package com.eguma.barcodescanner;

import android.view.View;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import javax.annotation.Nullable;

public class BarcodeScannerManager extends ViewGroupManager<BarcodeScannerView> {
    private static final String REACT_CLASS = "RNBarcodeScannerView";

    private static final String DEFAULT_TORCH_MODE = "off";
    private static final String DEFAULT_CAMERA_TYPE = "back";

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
        BarcodeScannerView scannerView = new BarcodeScannerView(context);
        scannerView.setCameraType(DEFAULT_CAMERA_TYPE);
        scannerView.setFlash(DEFAULT_TORCH_MODE.equals("on"));
        context.addLifecycleEventListener(scannerView);
        return scannerView;
    }

    @Override
    public void addView(BarcodeScannerView parent, View child, int index) {
        parent.addView(child, index + 1);   // index 0 for camera preview reserved
    }

    @Override
    public void onDropViewInstance(BarcodeScannerView view) {
        ((ThemedReactContext) view.getContext()).removeLifecycleEventListener(view);
    }

}
