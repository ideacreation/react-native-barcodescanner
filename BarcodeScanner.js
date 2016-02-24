'use strict';

import React, {
  Component,
  PropTypes,
  requireNativeComponent,
  StyleSheet,
  View,
} from 'react-native';

import Viewfinder from './Viewfinder';

class BarcodeScannerView extends Component {
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
      <View style={styles.container}>
        <RNBarcodeScannerView {...this.props} onChange={this.onChange} />
        <Viewfinder
          backgroundColor={this.props.viewFinderBackgroundColor}
          color={this.props.viewFinderBorderColor}
          borderWidth={this.props.viewFinderBorderWidth}
          borderLength={this.props.viewFinderBorderLength}
          height={this.props.viewFinderHeight}
          isLoading={this.props.viewFinderShowLoadingIndicator}
          width={this.props.viewFinderWidth}
        />
      </View>
    );
  }
}

BarcodeScannerView.propTypes = {
  ...View.propTypes,
  cameraType: PropTypes.string,
  onBarCodeRead: PropTypes.func,
  showLoadingIndicator: PropTypes.bool,
  torchMode: PropTypes.string,
  viewFinderBackgroundColor: PropTypes.string,
  viewFinderBorderColor: PropTypes.string,
  viewFinderBorderWidth: PropTypes.number,
  viewFinderBorderLength: PropTypes.number,
  viewFinderShowLoadingIndicator: PropTypes.bool,
};

var styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

var RNBarcodeScannerView = requireNativeComponent('RNBarcodeScannerView', BarcodeScannerView, {
  nativeOnly: {onChange: true}
});

module.exports = BarcodeScannerView;
