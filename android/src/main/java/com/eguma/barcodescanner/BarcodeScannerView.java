package com.eguma.barcodescanner;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.widget.FrameLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

public class BarcodeScannerView extends FrameLayout implements Camera.PreviewCallback, LifecycleEventListener {
    private CameraPreview mPreview;
    private MultiFormatReader mMultiFormatReader;

    private static final String TAG = "BarcodeScannerView";

    public BarcodeScannerView(Context context) {
        super(context);

        mPreview = new CameraPreview(context, this);
        mMultiFormatReader = new MultiFormatReader();
        this.addView(mPreview);
    }

    public void setCameraType(String cameraType) {
        mPreview.setCameraType(cameraType);
    }

    public void setFlash(boolean flag) {
        mPreview.setFlash(flag);
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        try {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();
            int width = size.width;
            int height = size.height;

            Result rawResult = null;
            PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(data, width, height, 0, 0, width, height, false);

            if (source != null) {
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                try {
                    rawResult = mMultiFormatReader.decodeWithState(bitmap);
                } catch (ReaderException re) {
                    // continue
                } catch (NullPointerException npe) {
                    // This is terrible
                } catch (ArrayIndexOutOfBoundsException aoe) {

                } finally {
                    mMultiFormatReader.reset();
                }
            }

            final Result finalRawResult = rawResult;

            if (finalRawResult != null) {
                Log.i(TAG, finalRawResult.getText());
                WritableMap event = Arguments.createMap();
                event.putString("data", finalRawResult.getText());
                event.putString("type", finalRawResult.getBarcodeFormat().toString());
                ReactContext reactContext = (ReactContext)getContext();
                reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                        getId(),
                        "topChange",
                        event);
            }
        } catch(Exception e) {
            // TODO: Terrible hack. It is possible that this method is invoked after camera is released.
            Log.e(TAG, e.toString(), e);
        }
    }

    @Override
    public void onHostResume() {
        mPreview.startCamera(); // workaround for reload js
        // mPreview.onResume();
    }

    @Override
    public void onHostPause() {
        mPreview.stopCamera();  // workaround for reload js
        // mPreview.onPause();
    }

    @Override
    public void onHostDestroy() {
        mPreview.stopCamera();
    }

}
