package com.eguma.barcodescanner;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import javax.annotation.Nullable;

public class ReactBarcodeScannerManager extends ViewGroupManager<ReactBarcodeScannerView> implements LifecycleEventListener {
    private static final String REACT_CLASS = "RNBarcodeScannerView";

    private static final String DEFAULT_VIEWFINDER_BACKGROUND_COLOR = "#60000000";
    private static final String DEFAULT_VIEWFINDER_BORDER_COLOR = "#ffffffff";
    private static final int DEFAULT_VIEWFINDER_BORDER_WIDTH = 4;
    private static final int DEFAULT_VIEWFINDER_BORDER_LENGTH = 60;
    private static final boolean DEFAULT_VIEWFINDER_DRAW_LASER = false;
    private static final String DEFAULT_VIEWFINDER_LASER_COLOR = "#ffcc0000";
    private static final String DEFAULT_TORCH_MODE = "off";
    private static final String DEFAULT_CAMERA_TYPE = "back";

    private ReactBarcodeScannerView mScannerView;
    private boolean mScannerViewVisible;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactProp(name = "viewFinderBackgroundColor")
    public void setViewFinderBackgroundColor(ReactBarcodeScannerView view, @Nullable String viewFinderBackgroundColor) {
      if (viewFinderBackgroundColor != null) {
          view.setMaskColor(viewFinderBackgroundColor);
      }
    }

    @ReactProp(name = "viewFinderBorderColor")
    public void setViewFinderBorderColor(ReactBarcodeScannerView view, @Nullable String viewFinderBorderColor) {
      if (viewFinderBorderColor != null) {
          view.setBorderColor(viewFinderBorderColor);
      }
    }

    @ReactProp(name = "viewFinderBorderWidth")
    public void setViewFinderBorderWidth(ReactBarcodeScannerView view, @Nullable Integer viewFinderBorderWidth) {
      if (viewFinderBorderWidth != null) {
          view.setBorderStrokeWidth(viewFinderBorderWidth);
      }
    }

    @ReactProp(name = "viewFinderBorderLength")
    public void setViewFinderBorderLength(ReactBarcodeScannerView view, @Nullable Integer viewFinderBorderLength) {
      if (viewFinderBorderLength != null) {
          view.setBorderLineLength(viewFinderBorderLength);
      }
    }

    @ReactProp(name = "viewFinderDrawLaser")
    public void setViewFinderDrawLaser(ReactBarcodeScannerView view, @Nullable Boolean viewFinderDrawLaser) {
      if (viewFinderDrawLaser != null) {
          view.setDrawLaser(viewFinderDrawLaser);
      }
    }

    @ReactProp(name = "viewFinderLaserColor")
    public void setViewFinderLaserColor(ReactBarcodeScannerView view, @Nullable String viewFinderLaserColor) {
      if (viewFinderLaserColor != null) {
          view.setLaserColor(viewFinderLaserColor);
      }
    }

    @ReactProp(name = "cameraType")
    public void setCameraType(ReactBarcodeScannerView view, @Nullable String cameraType) {
      if (cameraType != null) {
          view.setCameraType(cameraType);
      }
    }

    @ReactProp(name = "torchMode")
    public void setTorchMode(ReactBarcodeScannerView view, @Nullable String torchMode) {
      if (torchMode != null) {
          view.setTorchMode(torchMode);
      }
    }

    @Override
    public ReactBarcodeScannerView createViewInstance(ThemedReactContext context) {
        context.addLifecycleEventListener(this);
        mScannerView = new ReactBarcodeScannerView(context);
        mScannerView.setMaskColor(DEFAULT_VIEWFINDER_BACKGROUND_COLOR);
        mScannerView.setBorderColor(DEFAULT_VIEWFINDER_BORDER_COLOR);
        mScannerView.setBorderStrokeWidth(DEFAULT_VIEWFINDER_BORDER_WIDTH);
        mScannerView.setBorderLineLength(DEFAULT_VIEWFINDER_BORDER_LENGTH);
        mScannerView.setDrawLaser(DEFAULT_VIEWFINDER_DRAW_LASER);
        mScannerView.setLaserColor(DEFAULT_VIEWFINDER_LASER_COLOR);
        mScannerView.setCameraType(DEFAULT_CAMERA_TYPE);
        mScannerView.setTorchMode(DEFAULT_TORCH_MODE);
        mScannerViewVisible = true;
        return mScannerView;
    }

    @Override
    public void onDropViewInstance(ReactBarcodeScannerView view) {
        mScannerViewVisible = false;
        view.stopCamera();
    }

    @Override
    public void onHostResume() {
        if (mScannerViewVisible) {
            mScannerView.startCamera(mScannerView.getCameraId());
            mScannerView.setFlash(mScannerView.torchModeIsEnabled());
        }
    }

    @Override
    public void onHostPause() {
        mScannerView.stopCamera();
    }

    @Override
    public void onHostDestroy() {
        mScannerView.stopCamera();
    }
}
