
package com.example.backgroundhiddencamera;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.ErrorCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;
import static android.content.Context.WINDOW_SERVICE;

public class CameraView implements SurfaceHolder.Callback, PictureCallback,
        ErrorCallback {

    private Context context = null;

    private WindowManager winMan;
    // a surface holder
    private SurfaceHolder sHolder;

    private static Camera camera;


    private Parameters parameters;

    private AudioManager audioMgr = null;

    private WindowManager.LayoutParams params = null;

    private IFrontCaptureCallback callback;

    private SurfaceView surfaceView = null;

    public CameraView(Context ctx) {
        context = ctx;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
            audioMgr = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public void onError(int error, Camera camera) {
        Log.d(TAG, "Camera Error : " + error, null);

        WindowManager winMan = (WindowManager) context
                .getSystemService(WINDOW_SERVICE);
        winMan.removeView(surfaceView);
        callback.onCaptureError(-1);
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Toast.makeText(context,"Image Captured Successfully",Toast.LENGTH_LONG).show();

        if (data != null) {
            // Intent mIntent = new Intent();
            // mIntent.putExtra("image",imageData);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
                audioMgr.setStreamMute(AudioManager.STREAM_SYSTEM, false);

            WindowManager winMan = (WindowManager) context
                    .getSystemService(WINDOW_SERVICE);
            winMan.removeView(surfaceView);

            try {
                BitmapFactory.Options opts = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,
                        data.length, opts);
                bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, false);
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int newWidth = 300;
                int newHeight = 300;

                // calculate the scale - in this case = 0.4f
                float scaleWidth = ((float) newWidth) / width;
                float scaleHeight = ((float) newHeight) / height;

                // createa matrix for the manipulation
                Matrix matrix = new Matrix();
                // resize the bit map
                matrix.postScale(scaleWidth, scaleHeight);
                // rotate the Bitmap
                matrix.postRotate(-90);
                Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                        width, height, matrix, true);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 40,
                        bytes);

                // you can create a new file name "test.jpg" in sdcard
                // folder.
                File f = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "test.jpg");

                System.out.println("File F : " + f);

                f.createNewFile();
                // write the bytes in file
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                // remember close de FileOutput
                fo.close();
                context.stopService(new Intent(context,GetBackCoreService.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // StoreByteImage(mContext, imageData, 50,"ImageName");
            // setResult(FOTO_MODE, mIntent);
        }

        GetBackCoreService getBackCoreService=new GetBackCoreService();
        getBackCoreService.stopSelf();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // get camera parameters
        parameters = camera.getParameters();

        // set camera parameters
        camera.setParameters(parameters);
        camera.startPreview();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
            audioMgr.setStreamMute(AudioManager.STREAM_SYSTEM, true);
        Log.d(TAG, "Taking picture");

        try {
            Thread.sleep(4000);
            camera.takePicture(null, null, this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            Log.d(TAG, "Camera Opened");
            camera.setPreviewDisplay(sHolder);
        } catch (IOException exception) {
            camera.release();
            camera = null;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        // stop the preview
        camera.stopPreview();

        // release the camera
        camera.release();
        Log.d(TAG, "Camera released");

        // unbind the camera from this object
        camera = null;

    }
    public void capturePhoto(IFrontCaptureCallback frontCaptureCb) {

        callback = frontCaptureCb;

        if (!Utils.isFrontCameraPresent(context))
            callback.onCaptureError(-1);

        surfaceView = new SurfaceView(context);
        winMan = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        params = new WindowManager.LayoutParams(
                1,
                1,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        winMan.addView(surfaceView, params);

        surfaceView.setZOrderOnTop(true);

        SurfaceHolder holder = surfaceView.getHolder();

        holder.setFormat(PixelFormat.TRANSPARENT);

        sHolder = holder;
        sHolder.addCallback(this);
        sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        Utils.LogUtil.LogD(Constants.LOG_TAG, "Opening Camera");

        // The Surface has been created, acquire the camera
        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
    }
}