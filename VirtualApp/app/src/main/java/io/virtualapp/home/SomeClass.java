package io.virtualapp.home;

import android.util.Log;

public class SomeClass {

    private static final String TAG = "SomeClass";

    public void doSomething(String s, int i, MyClass m) {

        Log.i(TAG, "hookThirtyProcess--pid : param 1--s : " + s + "; param 2--i : " + i);

    }

    private final static void haiyang(){

    }

    public void haiyangli(){
        haiyang();
    }

}
