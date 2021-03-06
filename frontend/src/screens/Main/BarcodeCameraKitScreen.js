import {StyleSheet, Text, View} from 'react-native';
import React from 'react';
import {Pressable} from 'react-native';
import {CameraScreen} from 'react-native-camera-kit';

const BarcodeCameraKitScreen = ({onBarcodeScan, setOpenScanner}) => {
  return (
    <View style={styles.fullScreen}>
      <CameraScreen
        style={styles.cameraScreen}
        showFrame={false}
        // Show/hide scan frame
        scanBarcode={true}
        // Can restrict for the QR Code only
        laserColor={'blue'}
        // Color can be of your choice
        frameColor={'red'}
        // If frame is visible then frame color
        colorForScannerFrame={'black'}
        // Scanner Frame color
        onReadCode={event => onBarcodeScan(event.nativeEvent.codeStringValue)}
      />
      <View style={styles.cameraBtnWrapper}>
        <Pressable
          style={styles.cameraCancelBtn}
          onPress={() => setOpenScanner(false)}>
          <Text style={styles.cameraCancelText}>취소</Text>
        </Pressable>
      </View>
    </View>
  );
};

export default BarcodeCameraKitScreen;

const styles = StyleSheet.create({
  fullScreen: {
    flex: 1,
  },
  cameraScreen: {
    width: '100%',
    height: '92%',
  },
  cameraBtnWrapper: {
    width: '100%',
    height: '7%',
    flexDirection: 'row',
    marginBottom: 10,
  },
  cameraCancelBtn: {
    width: '94%',
    height: '100%',
    backgroundColor: '#fff',
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 15,
    marginHorizontal: 12,
  },
  // cameraSuccessBtn: {
  //   width: '47%',
  //   height: '100%',
  //   backgroundColor: '#ffb856',
  //   justifyContent: 'center',
  //   alignItems: 'center',
  //   borderRadius: 15,
  //   marginLeft: 15,
  // },
  cameraCancelText: {
    fontFamily: 'NanumSquareRoundOTFB',
    fontSize: 24,
    color: '#d50000',
  },
  cameraSuccessText: {
    fontFamily: 'NanumSquareRoundOTFB',
    fontSize: 24,
    color: '#fff',
  },
});
