package com.example.backgroundhiddencamera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.util.Log;

public class Utils {
    public static boolean isFrontCameraPresent(Context context) {
        // Utils.context = context.getApplicationContext();

        boolean result = false;
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_ANY)) {

            int numOfCameras = Camera.getNumberOfCameras();
            for (int i = 0; i < numOfCameras; i++) {
                CameraInfo info = new CameraInfo();
                Camera.getCameraInfo(i, info);
                if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
    public static class LogUtil {
        public static void LogD(String tag, String msg) {
            if (Constants.DEBUG_FLAG)
                Log.d(tag, "===== " + msg + " =====");
        }

        public static void LogW(String tag, String msg) {
            if (Constants.DEBUG_FLAG)
                Log.w(tag, "~~~~~ " + msg + " ~~~~~");
        }

        public static void LogE(String tag, String msg, Throwable e) {
            if (Constants.DEBUG_FLAG)
                Log.e(tag, "^^^^^ " + msg + " ^^^^^", e);
        }
    }
}