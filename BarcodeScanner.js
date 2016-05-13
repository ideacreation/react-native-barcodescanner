'use strict';

var React = require('react');
var ReactNative = require('react-native');
var { PropTypes} = React;
var {
  requireNativeComponent,
  View,
} = ReactNative;

class BarcodeScannerView extends React.Component {
  constructor() {
    super();
    this.onChange = this.onChange.bind(this);
  }

  onChange(event) {
    if (!this.props.onBarCodeRead) {
      return;
    }

    this.props.onBarCodeRead({
      type: event.nativeEvent.type,
      data: event.nativeEvent.data,
    });
  }

  render() {
    return (
      <RNBarcodeScannerView {...this.props} onChange={this.onChange} />
    );
  }
}

BarcodeScannerView.propTypes = {
  ...View.propTypes,
  viewFinderBackgroundColor: PropTypes.string,
  viewFinderBorderColor: PropTypes.string,
  viewFinderBorderWidth: PropTypes.number,
  viewFinderBorderLength: PropTypes.number,
  viewFinderDrawLaser: PropTypes.bool,
  viewFinderLaserColor: PropTypes.string,
  torchMode: PropTypes.string,
  cameraType: PropTypes.string,
  onBarCodeRead: PropTypes.func,
  rotation: PropTypes.number,
  scaleX: PropTypes.number,
  scaleY: PropTypes.number,
  translateX: PropTypes.number,
  translateY: PropTypes.number,
};

var RNBarcodeScannerView = requireNativeComponent('RNBarcodeScannerView', BarcodeScannerView, {
  nativeOnly: {onChange: true}
});

module.exports = BarcodeScannerView;
