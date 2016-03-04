package com.eguma.barcodescanner;

import android.view.View;

import javax.annotation.Nullable;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

public class BarcodeScannerManager extends ViewGroupManager<BarcodeScannerView> implements LifecycleEventListener {
    private static final String REACT_CLASS = "RNBarcodeScannerView";

    private static final String DEFAULT_TORCH_MODE = "off";
    private static final String DEFAULT_CAMERA_TYPE = "back";

    private BarcodeScannerView mScannerView;
    private boolean mScannerViewVisible;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactProp(name = "cameraType")
    public void setCameraType(BarcodeScannerView view, @Nullable String cameraType) {
      if (cameraType != null) {
          view.setCameraType(cameraType);
      }
    }

    @ReactProp(name = "torchMode")
    public void setTorchMode(BarcodeScannerView view, @Nullable String torchMode) {
        if (torchMode != null) {
            view.setFlash(torchMode.equals("on"));
        }
    }

    @Override
    public BarcodeScannerView createViewInstance(ThemedReactContext context) {
        context.addLifecycleEventListener(this);
        mScannerView = new BarcodeScannerView(context);
        mScannerView.setCameraType(DEFAULT_CAMERA_TYPE);
        mScannerView.setFlash(DEFAULT_TORCH_MODE.equals("on"));
        mScannerViewVisible = true;
        return mScannerView;
    }

    @Override
    public void onHostResume() {
        mScannerView.onResume();
    }

    @Override
    public void onHostPause() {
        mScannerView.onPause();
    }

    @Override
    public void onHostDestroy() {
        mScannerView.stopCamera();
    }

    @Override
    public void addView(BarcodeScannerView parent, View child, int index) {
        parent.addView(child, index + 1);   // index 0 for camera preview reserved
    }
}
