'use strict';

var React = require('React');
var PropTypes = require('ReactPropTypes');
var requireNativeComponent = require('requireNativeComponent');

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
  viewFinderBackgroundColor: PropTypes.string,
  viewFinderBorderColor: PropTypes.string,
  viewFinderBorderWidth: PropTypes.number,
  viewFinderBorderLength: PropTypes.number,
  viewFinderDrawLaser: PropTypes.bool,
  viewFinderLaserColor: PropTypes.string,
  torchMode: PropTypes.string,
  cameraType: PropTypes.string,
  onBarCodeRead: PropTypes.func,
};

var RNBarcodeScannerView = requireNativeComponent('RNBarcodeScannerView', BarcodeScannerView, {
  nativeOnly: {onChange: true}
});

module.exports = BarcodeScannerView;
