# react-native-barcodescanner

A barcode scanner component for react native android. For iOS you can use https://github.com/lwansbrough/react-native-camera. The library uses https://github.com/zxing/zxing to decode the barcodes.

### Rewrite with Version 2.0.0

With version 2.0.0 (react native >= 0.20.0) react-native-barcodescanner doesn't depend anymore on https://github.com/dm77/barcodescanner, but directly on https://github.com/zxing/zxing. The code is still heavily influenced by it but simplified for our use case. The viewfinder is rendered in javascript and not anymore directly in java.

### Breaking change

React native 0.19.0 changed the ReactProps class which led to problems with updating native view properties (see https://github.com/facebook/react-native/issues/5649). These errors are corrected in react-native-barcodescanner version 1.0.0. Use version 1.0.0 and larger for react native >=0.19.0 and for earlier react native versions use version 0.1.4.

### Installation

```bash
npm i --save react-native-barcodescanner
```

### Add it to your android project

* In `android/settings.gradle`

  ```gradle
  ...
  include ':react-native-barcodescanner', ':app'
  project(':react-native-barcodescanner').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-barcodescanner/android')
  ```

* In `android/app/build.gradle`

  ```gradle
  ...
  dependencies {
      ...
      compile project(':react-native-barcodescanner')
  }
  ```

* register module (in MainActivity.java)

  Add the following **import** statement:
  ```Java
  import com.eguma.barcodescanner.BarcodeScannerPackage;
  ```

  ...and then add `BarcodeScannerPackage` to exported package list *(MainActivity.java#getPackages)*:

  ```Java
  public class MainActivity extends ReactActivity {
      // (...)

      @Override
      protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
          new BarcodeScannerPackage()
        );
      }
  }
  ```

## Example
```javascript
import React, {
  AppRegistry,
  Component,
} from 'react-native';
import BarcodeScanner from 'react-native-barcodescanner';

class BarcodeScannerExampleApp extends Component {
  constructor(props) {
    super(props);

    this.state = {
      torchMode: 'off',
      cameraType: 'back',
    };
  }

  barcodeReceived(e) {
    console.log('Barcode: ' + e.data);
    console.log('Type: ' + e.type);
  }

  render() {
    return (
      <BarcodeScanner
        onBarCodeRead={this.barcodeReceived}
        style={{ flex: 1 }}
        torchMode={this.state.torchMode}
        cameraType={this.state.cameraType}
      />
    );
  }
}

AppRegistry.registerComponent('BarcodeScannerExampleApp', () => BarcodeScannerExampleApp);
```

## Properties

#### `onBarCodeRead`

Will call the specified method when a barcode is detected in the camera's view.
Event contains `data` (barcode value) and `type` (barcode type).
The following barcode types can be recognised:

```java
BarcodeFormat.UPC_A
BarcodeFormat.UPC_E
BarcodeFormat.EAN_13
BarcodeFormat.EAN_8
BarcodeFormat.RSS_14
BarcodeFormat.CODE_39
BarcodeFormat.CODE_93
BarcodeFormat.CODE_128
BarcodeFormat.ITF
BarcodeFormat.CODABAR
BarcodeFormat.QR_CODE
BarcodeFormat.DATA_MATRIX
BarcodeFormat.PDF_417
```

#### `torchMode`

Values:
`on`,
`off` (default)

Use the `torchMode` property to specify the camera torch mode.

#### `cameraType`

Values:
`back` (default),
`front`

Use the `cameraType` property to specify the camera to use. If you specify the front camera, but the device has no front camera the back camera is used.

### Viewfinder properties

The following properties can be used to style the viewfinder:

`viewFinderBackgroundColor`,
`viewFinderBorderColor`,
`viewFinderBorderWidth`,
`viewFinderBorderLength`,
`viewFinderShowLoadingIndicator`,

All color values are strings (e.g. '#eee' or 'rgba(0, 0, 0, 0.3)', default: 'white'). `viewFinderHeight` (default: 200), `viewFinderWidth` (default: 200), `viewFinderBorderWidth` (default: 2)and `viewFinderBorderLength` (default: 30) are numbers, `viewFinderShowLoadingIndicator` is either `true` or `false` (default) and shows a ActivityIndicatorIOS or a ProgressBarAndroid centered in the viewfinder depending on the platform.
