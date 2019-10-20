package com.lody.virtual.remote;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Parcel;
import android.os.Parcelable;

import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VPackageManager;
import com.lody.virtual.os.VEnvironment;

import java.io.File;

/**
 * @author Lody
 */
public final class InstalledAppInfo implements Parcelable {

    public String packageName;
    public String apkPath;
    public String libPath;
    public boolean dependSystem;
    public boolean isHook;  // @Hai-Yang Li 2019.10.11
    public int appId;

    // public InstalledAppInfo(String packageName, String apkPath, String libPath, boolean dependSystem, boolean skipDexOpt, int appId) {
    public InstalledAppInfo(String packageName, String apkPath, String libPath, boolean dependSystem, boolean isHook, int appId) {  // @Hai-Yang Li
        this.packageName = packageName;
        this.apkPath = apkPath;
        this.libPath = libPath;
        this.dependSystem = dependSystem;
        this.isHook = isHook;   // @Hai-Yang Li 2019.10.11
        this.appId = appId;
    }

    public File getOdexFile() {
        return VEnvironment.getOdexFile(packageName);
    }

    public ApplicationInfo getApplicationInfo(int userId) {
        return VPackageManager.get().getApplicationInfo(packageName, 0, userId);
    }

    public PackageInfo getPackageInfo(int userId) {
        return VPackageManager.get().getPackageInfo(packageName, 0, userId);
    }

    public int[] getInstalledUsers() {
        return VirtualCore.get().getPackageInstalledUsers(packageName);
    }

    public boolean isLaunched(int userId) {
        return VirtualCore.get().isPackageLaunched(userId, packageName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeString(this.apkPath);
        dest.writeString(this.libPath);
        dest.writeByte(this.dependSystem ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isHook ? (byte) 1 : (byte) 0);  // @Hai-Yang Li 2019.10.11
        dest.writeInt(this.appId);
    }

    protected InstalledAppInfo(Parcel in) {
        this.packageName = in.readString();
        this.apkPath = in.readString();
        this.libPath = in.readString();
        this.dependSystem = in.readByte() != 0;
        this.isHook = in.readByte() != 0;   // @Hai-Yang Li 2019.10.11
        this.appId = in.readInt();
    }

    public static final Creator<InstalledAppInfo> CREATOR = new Creator<InstalledAppInfo>() {
        @Override
        public InstalledAppInfo createFromParcel(Parcel source) {
            return new InstalledAppInfo(source);
        }

        @Override
        public InstalledAppInfo[] newArray(int size) {
            return new InstalledAppInfo[size];
        }
    };
}
