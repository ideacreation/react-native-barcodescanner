'use strict';

import React, {
  Component,
  PropTypes,
} from 'react';
import {
  requireNativeComponent,
  StyleSheet,
  View,
} from 'react-native';

import Viewfinder from './Viewfinder';

class BarcodeScannerView extends Component {
  constructor(props) {
    super(props);

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
    let viewFinder = this.props.showViewFinder ? (
      <Viewfinder
        backgroundColor={this.props.viewFinderBackgroundColor}
        color={this.props.viewFinderBorderColor}
        borderWidth={this.props.viewFinderBorderWidth}
        borderLength={this.props.viewFinderBorderLength}
        height={this.props.viewFinderHeight}
        isLoading={this.props.viewFinderShowLoadingIndicator}
        width={this.props.viewFinderWidth}
      />
    ) : null;
    return (
      <RNBarcodeScannerView {...this.props} onChange={this.onChange}>
        <View style={this.props.style} collapsable={false}>
          {viewFinder}
          {this.props.children}
        </View>
      </RNBarcodeScannerView>
    );
  }
}

BarcodeScannerView.propTypes = {
  ...View.propTypes,
  cameraType: PropTypes.string,
  onBarCodeRead: PropTypes.func,
  showLoadingIndicator: PropTypes.bool,
  showViewFinder: PropTypes.bool,
  torchMode: PropTypes.string,
  viewFinderBackgroundColor: PropTypes.string,
  viewFinderBorderColor: PropTypes.string,
  viewFinderBorderWidth: PropTypes.number,
  viewFinderBorderLength: PropTypes.number,
  viewFinderShowLoadingIndicator: PropTypes.bool,
};

BarcodeScannerView.defaultProps = {
  showViewFinder: true,
};

var RNBarcodeScannerView = requireNativeComponent('RNBarcodeScannerView', BarcodeScannerView, {
  nativeOnly: {onChange: true}
});

module.exports = BarcodeScannerView;
