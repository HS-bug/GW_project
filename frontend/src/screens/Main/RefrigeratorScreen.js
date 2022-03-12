import {
  FlatList,
  Image,
  Pressable,
  StyleSheet,
  Text,
  View,
  Platform,
  PermissionsAndroid,
  Alert,
} from 'react-native';
import React, {useContext, useEffect, useState} from 'react';
import Icon from 'react-native-vector-icons/MaterialIcons';
import AsyncStorage from '@react-native-async-storage/async-storage';
import {UserNameContext} from 'contexts/UserNameContext';
import RefrigeratorEmpty from 'components/RefrigeratorEmpty';
import RefrigeratorList from 'components/RefrigeratorList';
import AddButton from 'components/AddButton';

const RefrigeratorScreen = ({navigation}) => {
  const {username, setUsername} = useContext(UserNameContext);
  const [hidden, setHidden] = useState(false);

  const [refrigeratorItem, setRefrigeratorItem] = useState([
    {
      id: 1,
      itemName: '비비고 군만두',
      itemNumber: '1122334455667',
      itemRegistration: '22.03.02',
      itemExp: '22.03.05',
      itemRemainingDate: '1',
    },
    {
      id: 2,
      itemName: '2',
      itemNumber: '1122334455667',
      itemRegistration: '22.03.02',
      itemExp: '22.03.05',
      itemRemainingDate: '1',
    },
    {
      id: 3,
      itemName: '3',
      itemNumber: '1122334455667',
      itemRegistration: '22.03.02',
      itemExp: '22.03.05',
      itemRemainingDate: '1',
    },
    {
      id: 4,
      itemName: '4',
      itemNumber: '1122334455667',
      itemRegistration: '22.03.02',
      itemExp: '22.03.05',
      itemRemainingDate: '1',
    },
    {
      id: 5,
      itemName: '5',
      itemNumber: '1122334455667',
      itemRegistration: '22.03.02',
      itemExp: '22.03.05',
      itemRemainingDate: '1',
    },
  ]);

  const onScrolledToBottom = isBottom => {
    if (hidden !== isBottom) {
      setHidden(isBottom);
    }
  };

  return (
    <View style={styles.fullscreen}>
      <View>
        <View style={styles.header}>
          <Pressable onPress={() => navigation.navigate('HomeScreen')}>
            <Image
              source={require('../../assets/images/logo.png')}
              style={styles.logo}
              resizeMode="contain"
            />
          </Pressable>
          <View style={styles.headerTextWrapper}>
            <Text style={styles.headerText}>
              <Text style={styles.innerText}>{username} </Text>
              님의 냉장고
            </Text>
          </View>
          <Pressable style={styles.notification}>
            <Icon name="notifications-none" size={32} color={'#ff8527'} />
          </Pressable>
        </View>

        <View style={styles.listWrapper}>
          {refrigeratorItem.length === 0 ? (
            <RefrigeratorEmpty />
          ) : (
            <RefrigeratorList
              refrigeratorItem={refrigeratorItem}
              onScrolledToBottom={onScrolledToBottom}
            />
          )}
          <AddButton hidden={hidden} />
        </View>
      </View>
    </View>
  );
};

export default RefrigeratorScreen;

const styles = StyleSheet.create({
  fullscreen: {
    flex: 1,
  },
  header: {
    width: '95%',
    height: '11%',
    flexDirection: 'row',
    marginVertical: 5,
    marginHorizontal: 10,
    alignItems: 'center',
  },
  logo: {
    width: 56,
    height: 56,
  },
  headerTextWrapper: {
    width: '65%',
    height: 50,
    marginLeft: 20,
    alignItems: 'flex-end',
    justifyContent: 'center',
  },
  innerText: {
    fontFamily: 'NanumSquareRoundOTFB',
    fontSize: 23,
  },
  headerText: {
    fontFamily: 'NanumSquareRoundOTFR',
    fontSize: 20,
    color: '#000000',
  },
  notification: {
    marginLeft: 20,
  },
  listWrapper: {
    height: '87%',
  },
});
