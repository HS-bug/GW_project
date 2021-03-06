import {FlatList, StyleSheet} from 'react-native';
import React from 'react';
import RefrigeratorItem from './RefrigeratorItem';

const RefrigeratorList = ({
  refrigeratorItem,
  onScrolledToBottom,
  itemModalVisible,
  setItemModalVisible,
  setId,
  detailItem,
  setDetailItem,
}) => {
  const onScroll = e => {
    if (!onScrolledToBottom) {
      return;
    }

    const {contentSize, layoutMeasurement, contentOffset} = e.nativeEvent;
    const distanceFromBottom =
      contentSize.height - layoutMeasurement.height - contentOffset.y;

    if (
      contentSize.height > layoutMeasurement.height &&
      distanceFromBottom < 10
    ) {
      onScrolledToBottom(true);
    } else {
      onScrolledToBottom(false);
    }
  };

  return (
    <FlatList
      style={styles.list}
      data={refrigeratorItem}
      renderItem={({item}) => (
        <RefrigeratorItem
          id={item.id}
          itemImage={item.itemImage}
          itemName={item.itemName}
          itemAmount={item.itemAmount}
          itemReg={item.itemReg}
          itemExp={item.itemExp}
          itemRemainingDate={item.itemRemainingDate}
          itemModalVisible={itemModalVisible}
          setItemModalVisible={setItemModalVisible}
          setId={setId}
          detailItem={detailItem}
          setDetailItem={setDetailItem}
        />
      )}
      // keyExtractor={item => item.id.toString()}
      onScroll={onScroll}
    />
  );
};

export default RefrigeratorList;

const styles = StyleSheet.create({
  list: {
    flex: 1,
    paddingBottom: 15,
  },
});
