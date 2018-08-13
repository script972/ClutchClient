package com.script972.clutchclient.helpers;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.BatteryManager;
import android.os.Build;
import android.os.StatFs;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceHelper {

    public static boolean isJellyBean(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }
    public static boolean isLollipop() { return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP; }

    private static final long SIZE_KB = 1024L;


    public static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    /**
     * @return full RAM size of a device in kilobytes
     */
    public static long getTotalRAM(Context context) {
        if (isJellyBean()) {
            ActivityManager actManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
            actManager.getMemoryInfo(memInfo);
            return memInfo.totalMem / SIZE_KB;
        } else {
            return getTotalRAM();
        }
    }

    /**
     * @return used RAM size of a device in kilobytes
     */
    public static long getUsedRAM(Context context) {
        if (getTotalRAM(context) != -1) {
            return getTotalRAM(context) - getAvailableRAM(context);
        }
        return -1;
    }

    /**
     * @return available RAM size of a DEVICE in kilobytes. This is NOT what is available to your app
     */
    public static long getAvailableRAM(Context context) {
        ActivityManager actManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        return memInfo.availMem / SIZE_KB;
    }

    /**
     * @return percentage of battery level
     */
    public static int getBatteryLevel(Context context) {
        if (isLollipop()) {
            BatteryManager bm = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
            return (bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) > 0) ? bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) : getBatteryPercentage(context);
        } else {
            return getBatteryPercentage(context);
        }
    }

    /**
     * @return android OS version
     */
    public static String getAndroidVersion() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
        return sdkVersion + " (" + release +")";
    }

    /**
     * @return device model name
     */
    public static String getDeviceModel() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    /**
     * @return number of kilobytes on External storage (total size)
     */
  /*  public static long getExternalMemorySize() {
        StatFs stat = new StatFs(FileHelper.getCacheDir(TheoryTestProApplication.getContext()).getPath());
        long bytesAvailable = (long)stat.getBlockSize() *(long)stat.getBlockCount();
        return  bytesAvailable / SIZE_KB;
    }*/

    /**
     * @return number of kilobytes available on External storage
     */
  /*  public static long getAvailableSpaceInKB() {
        long availableSpace = -1L;
        StatFs stat = new StatFs(FileHelper.getCacheDir(TheoryTestProApplication.getContext()).getPath());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableSpace = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        } else {
            availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
        }
        return availableSpace / SIZE_KB;
    }*/

    private static long getTotalRAM() {
        RandomAccessFile reader = null;
        String load = null;
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
            }
            reader.close();
            return Long.parseLong(value);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    private static int getBatteryPercentage(Context context) {
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);
        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;
        float batteryPct = level / (float) scale;
        return (int) (batteryPct * 100);
    }

}