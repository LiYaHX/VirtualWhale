package com.lody.virtual.server.pm;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Manage hook plugins. Copy from the git project VirtualHook.
 *
 * The source code link : https://github.com/PAGalaxyLab/VirtualHook
 *
 * @author Hai-Yang Li
 */
public class HookCacheManager {

    static final Set<String> HOOK_PLUGIN_NAME_CACHE = new HashSet<>();

    public static int size() {
        synchronized (HOOK_PLUGIN_NAME_CACHE) {
            return HOOK_PLUGIN_NAME_CACHE.size();
        }
    }

    public static void put(String packageName) {
        synchronized (HookCacheManager.class) {
            HOOK_PLUGIN_NAME_CACHE.add(packageName);
        }
    }

    public static boolean remove(String packageName) {
        synchronized (HookCacheManager.class) {
            return HOOK_PLUGIN_NAME_CACHE.remove(packageName);
        }
    }

    public static String[] getAll() {
        synchronized (HookCacheManager.class) {
            return HOOK_PLUGIN_NAME_CACHE.toArray(new String[HOOK_PLUGIN_NAME_CACHE.size()]);
        }
    }

}
