package io.virtualapp.home.models;

import android.graphics.drawable.Drawable;

/**
 * @author Lody
 */

public class AppInfo {
    public String packageName;
    public String path;
    public boolean fastOpen;
    public Drawable icon;
    public CharSequence name;
    public int cloneCount;
    public boolean isHook;  // @Hai-Yang Li 2019.10.11
}
