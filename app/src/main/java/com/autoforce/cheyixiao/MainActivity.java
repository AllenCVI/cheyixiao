package com.autoforce.cheyixiao;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import com.autoforce.cheyixiao.base.BaseActivity;
import com.autoforce.cheyixiao.base.BaseConstant;
import com.autoforce.cheyixiao.base.ITranslucentAct;
import com.autoforce.cheyixiao.car.CarSourceFragment;
import com.autoforce.cheyixiao.car.source.SourceChildFragment;
import com.autoforce.cheyixiao.common.FilesDownload;
import com.autoforce.cheyixiao.common.UMengStatistics;
import com.autoforce.cheyixiao.common.data.local.LocalRepository;
import com.autoforce.cheyixiao.common.utils.*;
import com.autoforce.cheyixiao.customer.CustomerFragment;
import com.autoforce.cheyixiao.customer.mycommon.CustomerConstant;
import com.autoforce.cheyixiao.home.HomeFragment;
import com.autoforce.cheyixiao.home.dao.HomeCarFilePathDao;
import com.autoforce.cheyixiao.mine.MineFragment;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.io.File;


/**
 * Created by xialihao on 2018/11/15.
 */
public class MainActivity extends BaseActivity implements ITranslucentAct {

    private static final String TAG = "MainActivity";

    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    @BindView(R.id.fl_cut_progress)
    FrameLayout flCutProgress;

    @OnClick(R.id.iv_cha_hao)
    void click() {
        hideCutProgress();
    }

    private int currentTabIndex = -1;
    private long exitTime = 0;
    private int[] rbIds = {R.id.tab_home, R.id.tab_car, R.id.tab_customer, R.id.tab_mine};
    private static final String STATE_CURRENT_TAB_INDEX = "StateCurrentTabIndex";
    private static final String FRAGMENT_TAG_PREFIX = "MainActivityFragment_";
    private static final int DEFAULT_INDEX = 0;
    private PermissionHelper permissionHelper;
    private FilesDownload filesDownload;
    private boolean isNeedCheckMine = false;
    private int progress;
    private int totalFiles;
    private boolean isShowNotification = false;
    HomeCarFilePathDao dao;

    public static void start(Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

//    public static void start(Context context, boolean isGoMine) {
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra("isGoMine", isGoMine);
//        context.startActivity(intent);
//    }

    public static void start(Context context, String goFragment) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(BaseConstant.GOFRAGMENT, goFragment);
        context.startActivity(intent);

    }


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        //initPermission();
        resizeDrawable();

        int index;
        if (savedInstanceState != null) {
            index = savedInstanceState.getInt(MainActivity.STATE_CURRENT_TAB_INDEX);
        } else {
            index = DEFAULT_INDEX;
        }

        String goFragment = getIntent().getStringExtra(BaseConstant.GOFRAGMENT);
        if (goFragment != null && goFragment.equals(BaseConstant.HOMEFRAGMENT)) {
            index = DEFAULT_INDEX;
        }


        showTab(index);
        rgMain.check(rbIds[index]);

        rgMain.setOnCheckedChangeListener((radioGroup, id) -> {
            int pos;
            switch (id) {
                case R.id.tab_home:
                    pos = 0;
                    break;
                case R.id.tab_car:
                    pos = 1;
                    UMengStatistics.statisticEventNumber("carsource_center");
                    break;
                case R.id.tab_customer:
                    pos = 2;
                    break;
                case R.id.tab_mine:
                    pos = 3;
                    break;
                default:
                    pos = 0;
            }

            showTab(pos);
        });

    }

    @Override
    protected void initData() {
        dao = new HomeCarFilePathDao(getApplicationContext());
        filesDownload = new FilesDownload(getApplicationContext());
        filesDownload.setListener(new FilesDownload.OnProgressListener() {
            @Override
            public void onProgress(int downloadedNumber, int totalNumber) {
                if (downloadedNumber == totalNumber) {}
            }
        });
        initPermission();
    }

    private boolean goFragmentFlag = false;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent.getBooleanExtra("isGoMine", false)) {
            rgMain.check(R.id.tab_mine);
        }

        String goFragment = intent.getStringExtra(BaseConstant.GOFRAGMENT);

        if (goFragment != null && goFragment.equals(BaseConstant.HOMEFRAGMENT)) {
            rgMain.check(R.id.tab_home);
        } else if (goFragment != null && goFragment.equals(BaseConstant.CARFRAGMENT)) {
            goFragmentFlag = true;
            rgMain.check(R.id.tab_car);
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(genFragmentTag(1));
            if(fragment!=null&&fragment instanceof CarSourceFragment){
                goFragment(fragment,1);
            }
            return;
        }

        goFragmentFlag = false;

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedCheckMine) {
            rgMain.check(R.id.tab_mine);
            isNeedCheckMine = false;
        }
    }

    private void resizeDrawable() {

        for (int rbId : rbIds) {
            RadioButton rb = findViewById(rbId);
            DrawableUtils.resizeDrawable(rb, DrawableUtils.DRAWABLE_TOP, 2 / 3f);
        }
    }

    private void showTab(int index) {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        switch (index) {
            case 0:
            case 3:
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                break;
            case 1:
            case 2:
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                break;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        if (index != currentTabIndex) {
            changeFragment(index, currentTabIndex);
            currentTabIndex = index;
        }

    }

    private void changeFragment(int newTabIndex, int oldTabIndex) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Fragment currentFragment = null;
        if (oldTabIndex >= 0) {
            currentFragment = getSupportFragmentManager().findFragmentByTag(genFragmentTag(oldTabIndex));
        }

        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        Fragment targetFragment = getSupportFragmentManager().findFragmentByTag(genFragmentTag(newTabIndex));

        if (targetFragment == null) {
            targetFragment = createFragment(newTabIndex);
            transaction.add(R.id.fl_content, targetFragment, genFragmentTag(newTabIndex));
        } else {
            transaction.show(targetFragment);
        }
        transaction.commit();

        if (goFragmentFlag&&targetFragment instanceof CarSourceFragment) {
            goFragment(targetFragment,1);
        }else if(!goFragmentFlag&&targetFragment instanceof CarSourceFragment){
            goFragment(targetFragment,-1);
        }


    }

    private void goFragment(Fragment targetFragment,int index) {
        Bundle bundle = new Bundle();
        bundle.putInt(BaseConstant.GOFRAGMENT,index);
        targetFragment.setArguments(bundle);
        goFragmentFlag = false;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if (System.currentTimeMillis() - exitTime > 3000) {
                Toast.makeText(MainActivity.this, R.string.double_click_quit, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putInt(MainActivity.STATE_CURRENT_TAB_INDEX, currentTabIndex);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            currentTabIndex = savedInstanceState.getInt(MainActivity.STATE_CURRENT_TAB_INDEX);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SourceChildFragment.CODE_PRICE) {
                CarSourceFragment fragment = (CarSourceFragment) getSupportFragmentManager().findFragmentByTag(genFragmentTag(1));
                if (fragment != null) {
                    fragment.handleSourceChildFragment(requestCode, resultCode, data);
                }
            } else if (requestCode == SourceChildFragment.SOURCE_DETAIL) {
                isNeedCheckMine = true;
            }
        }

        if (requestCode == CustomerConstant.TOCOUSTOMERDETAILLREQUESTCODI) {
            CustomerFragment customerFragment = (CustomerFragment) getSupportFragmentManager().findFragmentByTag(genFragmentTag(2));
            if (customerFragment != null) {
                customerFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    private String genFragmentTag(int index) {
        return MainActivity.FRAGMENT_TAG_PREFIX + index;
    }

    private Fragment createFragment(int index) {

        Fragment fragment;
        switch (index) {
            case 0:
                fragment = HomeFragment.newInstance();
                break;
            case 1:
                fragment = CarSourceFragment.Companion.newInstance();
                break;
            case 2:
                fragment = CustomerFragment.newInstance();
                break;
            case 3:
                fragment = MineFragment.newInstance();
                break;
            default:
                fragment = HomeFragment.newInstance();
        }

        return fragment;
    }

    private void initPermission() {
        /*permissionHelper = new PermissionHelper(this, this);
        permissionHelper.requestPermission();*/
        AutoForcePermissionUtils.requestPermissions(this, new AutoForcePermissionUtils.PermissionCallback() {
            @Override
            public void onPermissionGranted() {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    moveOldFiles();
                }
            }

            @Override
            public void onPermissionDenied() {

            }
        },      Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE);
    }

    //TODO 将这些文件操作逻辑抽取出去  不依赖于MainActivity
    @SuppressLint("CheckResult")
    private void moveOldFiles() {
        if (LocalRepository.getInstance().getToken().trim().isEmpty()) {
            return;
        }
        progress = 0;
        totalFiles = 0;
        File carsFile = new File(getFilesDir(), "cars");
        if (carsFile.exists() && carsFile.listFiles().length > 0) {
            getTotalFiles(carsFile);
            Log.e(TAG, "文件总数为" + totalFiles);
            showCutProgress();
        }
        Observable.create((ObservableOnSubscribe<File>) emitter -> {
            if (carsFile.exists()) {
                emitter.onNext(carsFile);
            }
            emitter.onComplete();
        }).flatMap((Function<File, ObservableSource<File>>) file -> {
            File[] fileDirectories = file.listFiles();
            return Observable.fromArray(fileDirectories);
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) throws Exception {
                        if (file.exists()) {
                            cutFiles(file, file.getName());
                            FileUtils.deleteFilesDirectory(file);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isShowNotification) {
                                    showMoveFilesNotification(totalFiles, totalFiles);
                                }
                                hideCutProgress();
                                clearMoveFilesNotification();
                            }
                        });
                        downloadCommonFiles();
                    }
                });
    }

    private void downloadCommonFiles() {
        String downloadUrl = String.format(getResources().getString(R.string.offline_download_url), "common");
        if (filesDownload != null) {
            filesDownload.dealOfflineExperience(downloadUrl, "common");
        }
    }

    private void getTotalFiles(File parentFile) {
        for (File file : parentFile.listFiles()) {
            if (file.isFile()) {
                totalFiles += 1;
            } else {
                getTotalFiles(file);
            }
        }
    }

    /**
     * 把旧版本离线车源数据转移到新版本存储路径
     */
    private void cutFiles(File parentFile, String carId) {
        for (File file : parentFile.listFiles()) {
            if (file.isFile()) {
                progress += 1;
                if (isShowNotification) {
                    showMoveFilesNotification(progress, totalFiles);
                }
                String filePath = file.getAbsolutePath();
                String fileStr = filePath.substring(filePath.indexOf(carId + "/") + (carId + "/").length());
                String fileUrl = String.format(getResources().getString(R.string.offline_download_url_host), carId) + fileStr;
                String fileName = MD5Util.md5(fileUrl);
                File targetFile = new File(getExternalFilesDir(carId), fileName);
                if (FileUtils.cutFile(file, targetFile)) {
                    dao.insertHomeCarFilePathData(fileName, targetFile.getAbsolutePath(), carId);
                }
            } else {
                cutFiles(file, carId);
            }
        }
    }

    private void showCutProgress() {
        isShowNotification = false;
        if (flCutProgress != null)
            flCutProgress.setVisibility(View.VISIBLE);
    }

    private void hideCutProgress() {
        isShowNotification = true;
        if (flCutProgress != null) {
            flCutProgress.setVisibility(View.GONE);
        }
    }

    private void showMoveFilesNotification(int progress, int max) {
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.view_files_move_notification);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("车易销");
        builder.setWhen(System.currentTimeMillis());
        views.setTextViewText(R.id.tv_files_move, progress + "/" + max);
        views.setProgressBar(R.id.pb_files_move, max, progress, false);
        builder.setContent(views);
        manager.notify(100, builder.build());
    }

    private void clearMoveFilesNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(100);
    }
}
