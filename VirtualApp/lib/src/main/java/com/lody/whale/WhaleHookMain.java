package com.lody.whale;

import android.util.Log;

import com.lody.whale.xposed.XC_MethodHook;
import com.lody.whale.xposed.XposedHelpers;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * whale hook framework hook main.
 *
 * @author Hai-Yang Li
 */
public class WhaleHookMain {

    private static final String TAG = "WhaleHookMain";

//    public static void hookThirtyProcess(ClassLoader classLoader) {
//
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                Log.i(TAG, "this is test thread breakpoint");
//            }
//        }.start();
//        Log.i(TAG, "hookThirtyProcess--pid : " + android.os.Process.myPid() + "; tid : " + android.os.Process.myPid());
//        XposedHelpers.findAndHookMethod("com.robin.designpatternapp.MainActivity",
////                ClassLoader.getSystemClassLoader(), // @Hai-Yang Li @ 2019.10.06 18:22 需要搞清楚 第三方app 以何种方式在 VirtualApp 中运行，
//                // 然后正确得到 类似 Application 的 classloader。意即 热插拔 技术。
//
//
//                classLoader,
//                "initView",
//                String.class,
//                new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);
//                        Log.i(TAG, "hookThirtyProcess--pid : param -- " + param.args[0]);
//                        param.args[0] = "hello world!";
//
//                        Log.i(TAG, "4. hook thirty method initView before--pid : " + android.os.Process.myPid() + "; tid : " + android.os.Process.myPid());
//
//                    }
//
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                        Log.i(TAG, "4. hook thirty method initView after--pid : " + android.os.Process.myPid() + "; tid : " + android.os.Process.myPid());
//                    }
//                });
//
//    }

    private static List<Class<?>> hookInfoClasses = new LinkedList<>();

    public static void doHookDefault(ClassLoader patchClassLoader, ClassLoader originClassLoader) {
        try {
            Class<?> hookInfoClass = Class.forName("com.robin.hookdesignpatternapp.whale.HookInfo", true, patchClassLoader);
            String[] hookItemNames = (String[]) hookInfoClass.getField("hookItemsName").get(null);
            for (String hookItemName : hookItemNames) {
                doHookItemDefault(patchClassLoader, hookItemName, originClassLoader);
            }
            hookInfoClasses.add(hookInfoClass);
            Log.i(TAG, "size of hookInfoClasses : " + hookInfoClasses.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doHookItemDefault(ClassLoader patchClassLoader, String hookItemName, ClassLoader originClassLoader) {
        try {
            Log.i(TAG, "Start hooking with item " + hookItemName);
            Class<?> hookItem = Class.forName(hookItemName, true, patchClassLoader);

            String className = (String) hookItem.getField("className").get(null);
            String methodName = (String) hookItem.getField("methodName").get(null);
            Object[] parameterTypes = (Object[]) hookItem.getField("parameterTypes").get(null);

            if (className == null || className.equals("")) {
                Log.w(TAG, "No target class. Skipping...");
                return;
            }

            XC_MethodHook xc_methodHook = new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
//                    Log.i(TAG, "beforeHookedMethod : no parameters");
                    Log.i(TAG, "beforeHookedMethod : param first --- " + param.args[0]);
                    param.args[0] = "hello, world!";
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
//                    Log.i(TAG, "afterHookedMethod : no parameters");
                    Log.i(TAG, "afterHookedMethod : param first --- " + param.args[0]);
                }
            };

            Object[] parameterTypesAndCallback;
            if (parameterTypes == null || parameterTypes.length == 0) {
                parameterTypesAndCallback = new Object[]{xc_methodHook};
            } else {
                int paramtersCount = parameterTypes.length;
                int length = paramtersCount + 1;

                parameterTypesAndCallback = new Object[length];
                for (int i = 0; i < paramtersCount; i++) {
                    parameterTypesAndCallback[i] = parameterTypes[i];
                }
                parameterTypesAndCallback[length - 1] = xc_methodHook;
            }

            Log.i(TAG, "className : " + className + "; methodName : " + methodName);
            XposedHelpers.findAndHookMethod(className,
                    originClassLoader,
                    methodName,
                    parameterTypesAndCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
