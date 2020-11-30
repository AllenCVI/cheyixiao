package com.autoforce.cheyixiao.common.data.local;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.autoforce.cheyixiao.common.data.local.bean.AccountInfo;
import com.autoforce.cheyixiao.common.data.local.utils.SpUtils;
import com.autoforce.cheyixiao.common.data.remote.bean.LoginResult;
import com.autoforce.cheyixiao.common.utils.GsonProvider;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by xialihao on 2018/11/19.
 */
public class LocalRepository {


    private static volatile LocalRepository sInstance = null;
    private static final int ACCOUNT_SIZE = 3;

    private LocalRepository() {

    }

    public static LocalRepository getInstance() {
        if (sInstance == null) {
            synchronized (LocalRepository.class) {
                if (sInstance == null) {
                    sInstance = new LocalRepository();
                }
            }
        }
        return sInstance;
    }

    /**
     * 保存已登录的账号信息到本地
     * @param account
     */
    public void saveAccount(String account) {

        if (TextUtils.isEmpty(account)) {
            return;
        }

        List<AccountInfo> accounts = getSavedAccountInfos();

        // 去重
        for (Iterator<AccountInfo> iter = accounts.iterator(); iter.hasNext(); ) {
            AccountInfo info = iter.next();
            if (account.equals(info.getPhone())) {
                iter.remove();
                break;
            }
        }

        accounts.add(0, new AccountInfo(account));

        //控制size
        if (accounts.size() > ACCOUNT_SIZE) {
            accounts.remove(accounts.size() - 1);
        }

        SpUtils.getInstance().put(SpKeyConfig.PHONES, GsonProvider.gson().toJson(accounts));

    }

    /**
     * 返回本地保存的所有已登录账号记录
     * @return
     */
    public List<String> getSavedAccounts() {

        List<AccountInfo> accountInfos = getSavedAccountInfos();
        List<String> list = new ArrayList<>();
        for (AccountInfo info : accountInfos) {
            list.add(info.getPhone());
        }

        return list;
    }

    @NonNull
    private List<AccountInfo> getSavedAccountInfos() {

        String phoneJson = SpUtils.getInstance().getString(SpKeyConfig.PHONES, "");
        List<AccountInfo> retList = new ArrayList<>();

        if (!TextUtils.isEmpty(phoneJson)) {
            List<AccountInfo> savedPhones = GsonProvider.gson().fromJson(phoneJson, new TypeToken<List<AccountInfo>>() {
            }.getType());

            if (savedPhones != null) {
                retList.addAll(savedPhones);
            }
        }

        return retList;
    }


    /**
     * 保存用户登录后信息
     */
    public void saveUserInfo(LoginResult loginResult) {
        SpUtils.getInstance().put(SpKeyConfig.TOKEN_KEY, loginResult.getToken());
        SpUtils.getInstance().put(SpKeyConfig.SALER_ID_KEY, loginResult.getSaler());
        SpUtils.getInstance().put(SpKeyConfig.CERT_CODE_KEY, loginResult.getCertCode());
        SpUtils.getInstance().put(SpKeyConfig.ROLE_KEY, loginResult.getRole());
    }

    /**
     * 清空本地保存用户信息，在退出登录/或者账号异地登录时调用
     */
    public void clearUserInfo() {
        SpUtils.getInstance().put(SpKeyConfig.TOKEN_KEY, "");
        SpUtils.getInstance().put(SpKeyConfig.SALER_ID_KEY, "");
        SpUtils.getInstance().put(SpKeyConfig.ROLE_KEY, "");
        SpUtils.getInstance().put(SpKeyConfig.CERT_CODE_KEY, "");
    }

    /**
     * 返回token
     *
     * @return
     */
    public String getToken() {
        return SpUtils.getInstance().getString(SpKeyConfig.TOKEN_KEY, "");
    }

    /**
     * 返回salerId
     *
     * @return
     */
    public String getCertCode() {
        return SpUtils.getInstance().getString(SpKeyConfig.CERT_CODE_KEY, "");
    }


    /**
     * 返回salerId
     *
     * @return
     */
    public String getSalerId() {
        return SpUtils.getInstance().getString(SpKeyConfig.SALER_ID_KEY, "");
    }


    /**
     * 返回salerId
     *
     * @return
     */
    public String getRole() {
        return SpUtils.getInstance().getString(SpKeyConfig.ROLE_KEY, "");
    }


    public boolean isCertPass() {
        return getCertCode().equals(SpKeyConfig.CODE_PASS);
    }


    /**
     * 将本地保存的审核状态码变成通过
     */
    public void setCertPass() {
        SpUtils.getInstance().put(SpKeyConfig.CERT_CODE_KEY, SpKeyConfig.CODE_PASS);
    }

    public void setCertReject() {
        SpUtils.getInstance().put(SpKeyConfig.CERT_CODE_KEY, SpKeyConfig.CODE_REJECT);
    }

    interface SpKeyConfig {
        String TOKEN_KEY = "token";
        String SALER_ID_KEY = "salerId";
        String CERT_CODE_KEY = "certCode";
        String ROLE_KEY = "role";

        String CODE_PASS = "2";
        String CODE_REJECT = "3";
        String PHONES = "phones";
    }
}
