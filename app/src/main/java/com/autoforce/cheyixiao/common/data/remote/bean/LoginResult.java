package com.autoforce.cheyixiao.common.data.remote.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xialihao on 2018/11/19.
 */
public class LoginResult extends SimpleResult implements Parcelable {


    /**
     * Cyx_saler : 44324
     * bind_phone : 16601169211
     * expire : false
     * Cyx_token : jejcz1X1fiA3
     * role : 1
     * name : 哦哦哦
     * is_bind : 1
     * avatar :
     * msg : 登录成功
     * code : 200
     * cert_code : 2
     * username : 16601169211
     */

    @SerializedName("Cyx_saler")
    private String saler;
    @SerializedName("bind_phone")
    private long bindPhone;
    private boolean expire;
    @SerializedName("Cyx_token")
    private String token;
    private String role;
    private String name;
    @SerializedName("is_bind")
    private int isBind;
    private String avatar;
    @SerializedName("cert_code")
    private String certCode;
    private String username;

    public String getSaler() {
        return saler;
    }

    public void setSaler(String saler) {
        this.saler = saler;
    }

    public long getBindPhone() {
        return bindPhone;
    }

    public void setBindPhone(long bindPhone) {
        this.bindPhone = bindPhone;
    }

    public boolean isExpire() {
        return expire;
    }

    public void setExpire(boolean expire) {
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsBind() {
        return isBind;
    }

    public void setIsBind(int isBind) {
        this.isBind = isBind;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCertCode() {
        return certCode;
    }

    public boolean isVerified() {
        return "2".equals(certCode);
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.saler);
        dest.writeLong(this.bindPhone);
        dest.writeByte(this.expire ? (byte) 1 : (byte) 0);
        dest.writeString(this.token);
        dest.writeString(this.role);
        dest.writeString(this.name);
        dest.writeInt(this.isBind);
        dest.writeString(this.avatar);
        dest.writeString(this.certCode);
        dest.writeString(this.username);
    }

    public LoginResult() {
    }

    protected LoginResult(Parcel in) {
        this.saler = in.readString();
        this.bindPhone = in.readLong();
        this.expire = in.readByte() != 0;
        this.token = in.readString();
        this.role = in.readString();
        this.name = in.readString();
        this.isBind = in.readInt();
        this.avatar = in.readString();
        this.certCode = in.readString();
        this.username = in.readString();
    }

    public static final Parcelable.Creator<LoginResult> CREATOR = new Parcelable.Creator<LoginResult>() {
        @Override
        public LoginResult createFromParcel(Parcel source) {
            return new LoginResult(source);
        }

        @Override
        public LoginResult[] newArray(int size) {
            return new LoginResult[size];
        }
    };
}
