import {
  ActivityIndicator,
  Alert,
  Image,
  Modal,
  Pressable,
  StyleSheet,
  Text,
  TextInput,
  View,
} from 'react-native';
import React, {useContext, useEffect, useState} from 'react';
import {useNavigation} from '@react-navigation/native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import Icon from 'react-native-vector-icons/MaterialIcons';
import {KeyboardAwareFlatList} from 'react-native-keyboard-aware-scroll-view';
import MainImageSelectModal from 'components/RecipeAdd/MainImageSelectModal';
import {Picker} from '@react-native-picker/picker';
import InputIngredientList from 'components/RecipeAdd/InputIngredientList';
import InputStepList from 'components/RecipeAdd/InputStepList';
import {RecipeIdContext} from 'contexts/RecipeIdContext';
import UpdateConfirmModal from 'components/UserRecipe/UpdateConfirmModal';

const RecipeUpdateScreen = () => {
  const navigation = useNavigation();
  const [input, setInput] = useState({
    recipeName: '',
    recipeMainImage: null,
    typeCategory: '',
    situationCategory: '',
    ingredientCategory: '',
    methodCategory: '',
    recipeTime: '',
    recipeLevel: '',
    recipeServes: '',
    recipeDescription: '',
    recipeIngredients: [
      // {ingredientName: '', ingredientAmount: ''},
      // {ingredientName: '', ingredientAmount: ''},
    ],
    recipeStep: [
      // {stepImage: null, stepDescription: ''},
      // {stepImage: null, stepDescription: ''},
    ],
  });

  const {recipeId, setRecipeId} = useContext(RecipeIdContext);

  const [recipeMainImage, setRecipeMainImage] = useState('');

  const [selectModalVisible, setSelectModalVisible] = useState(false);

  const [typeCategory, setTypeCategory] = useState(null);
  const [situationCategory, setSituationCategory] = useState(null);
  const [ingredientCategory, setIngredientCategory] = useState(null);
  const [methodCategory, setMethodCategory] = useState(null);

  const [recipeTime, setRecipeTime] = useState(null);
  const [recipeLevel, setRecipeLevel] = useState(null);
  const [recipeServes, setRecipeServes] = useState(null);

  const readItem = async () => {
    setLoading(true);
    try {
      const token = await AsyncStorage.getItem('user_token');
      await fetch(
        `http://localhost:8080/user/recipe/detail?id=${recipeId}&book_check=false`,
        {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            token: token,
          },
        },
      )
        .then(response => response.json())
        .then(responseJson => {
          // console.log('read\n\n\n', responseJson);
          if (responseJson.status === 200) {
            setLoading(false);
            setInput(responseJson.recipe_detail);
            setRecipeMainImage(responseJson.recipe_detail.recipeMainImage);
            setTypeCategory(responseJson.recipe_detail.typeCategory);
            setSituationCategory(responseJson.recipe_detail.situationCategory);
            setIngredientCategory(
              responseJson.recipe_detail.ingredientCategory,
            );
            setMethodCategory(responseJson.recipe_detail.methodCategory);
            setRecipeTime(responseJson.recipe_detail.recipeTime);
            setRecipeLevel(responseJson.recipe_detail.recipeLevel);
            setRecipeServes(responseJson.recipe_detail.recipeServes);
          } else {
            console.log('error');
          }
        })
        .catch(error => {
          console.error(error);
        });
    } catch (e) {
      console.log(e);
    }
  };
  useEffect(() => {
    readItem();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    setInput({
      ...input,
      // recipeMainImage: recipeMainImage?.assets[0]?.base64,
      recipeMainImage: recipeMainImage,
      typeCategory: typeCategory,
      situationCategory: situationCategory,
      ingredientCategory: ingredientCategory,
      methodCategory: methodCategory,
      recipeTime: recipeTime,
      recipeLevel: recipeLevel,
      recipeServes: recipeServes,
    });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [
    recipeMainImage,
    typeCategory,
    situationCategory,
    ingredientCategory,
    methodCategory,
    recipeTime,
    recipeLevel,
    recipeServes,
  ]);

  const createChangeTextHandler = name => value => {
    setInput({...input, [name]: value});
  };

  const handleIngredientNameChange = ({name, value, ingredientIndex}) => {
    setInput(prev => {
      return {
        ...input,
        recipeIngredients: prev.recipeIngredients.map((ingredient, index) => {
          if (index == ingredientIndex) {
            return {...ingredient, ingredientName: value};
          }
          return ingredient;
        }),
      };
    });
  };

  const handleIngredientAmountChange = ({name, value, ingredientIndex}) => {
    setInput(prev => {
      return {
        ...input,
        recipeIngredients: prev.recipeIngredients.map((ingredient, index) => {
          if (index == ingredientIndex) {
            return {...ingredient, ingredientAmount: value};
          }
          return ingredient;
        }),
      };
    });
  };

  const addIngredientInputs = () => {
    setInput(prev => {
      return {
        ...prev,
        recipeIngredients: [
          ...prev.recipeIngredients,
          {ingredientName: '', ingredientAmount: ''},
        ],
      };
    });
  };

  const removeIngredientInput = ingredientIndex => {
    setInput({
      ...input,
      recipeIngredients: input.recipeIngredients.filter(
        (recipeIngredients, removedIngredient) =>
          removedIngredient !== ingredientIndex,
      ),
    });
  };

  const handleStepDescriptionChange = ({name, value, stepIndex}) => {
    setInput(prev => {
      return {
        ...input,
        recipeStep: prev.recipeStep.map((step, index) => {
          if (index == stepIndex) {
            return {...step, stepDescription: value};
          }
          return step;
        }),
      };
    });
  };

  const addStepInputs = () => {
    setInput(prev => {
      return {
        ...prev,
        recipeStep: [...prev.recipeStep, {stepImage: '', stepDescription: ''}],
      };
    });
  };

  const removeStepInput = stepIndex => {
    setInput({
      ...input,
      recipeStep: input.recipeStep.filter(
        (recipeStep, removedStep) => removedStep !== stepIndex,
      ),
    });
  };

  const onPressSubmit = () => {
    setUpdateConfirm(!updateConfirm);
  };

  const [updateConfirm, setUpdateConfirm] = useState(false);

  let imageCheck = false;
  imageCheck = recipeMainImage.includes('http');

  const [loading, setLoading] = useState(false);

  return (
    <View style={styles.fullScreen}>
      {loading ? (
        <View style={styles.loadingScreen}>
          <ActivityIndicator size="large" color="#ff8527" />
        </View>
      ) : (
        <View style={styles.fullScreen}>
          <View style={styles.header}>
            <Pressable
              onPress={() => navigation.goBack()}
              android_ripple={{color: '#e1e2e3'}}>
              <Icon name="arrow-back" size={32} color={'#ff8527'} />
            </Pressable>
            <View style={styles.btnWrapper}>
              <Pressable
                onPress={onPressSubmit}
                android_ripple={{color: '#e1e2e3'}}>
                <Text style={styles.saveText}>??????</Text>
              </Pressable>
              <Modal
                avoidKeyboard={true}
                animationType="fade"
                transparent={true}
                visible={updateConfirm}
                onRequestClose={() => {
                  setUpdateConfirm(!updateConfirm);
                }}>
                <UpdateConfirmModal
                  updateConfirm={updateConfirm}
                  setUpdateConfirm={setUpdateConfirm}
                  input={input}
                />
              </Modal>
            </View>
          </View>
          <View style={styles.listWrapper}>
            <KeyboardAwareFlatList
              enableOnAndroid={true}
              enableAutomaticScroll={true}
              data={[input]}
              renderItem={({item}) => (
                <View style={styles.list}>
                  <View style={styles.nameWrapper}>
                    <Text style={styles.recipeName}>????????? ??????</Text>
                    <TextInput
                      style={styles.inputName}
                      autoCapitalize="none"
                      onChangeText={createChangeTextHandler('recipeName')}
                      placeholder={'????????? ??????'}
                      defaultValue={input.recipeName}
                    />
                  </View>
                  <Modal
                    avoidKeyboard={true}
                    animationType="fade"
                    transparent={true}
                    visible={selectModalVisible}
                    onRequestClose={() => {
                      setSelectModalVisible(!selectModalVisible);
                    }}>
                    <MainImageSelectModal
                      setSelectModalVisible={setSelectModalVisible}
                      setRecipeMainImage={setRecipeMainImage}
                    />
                  </Modal>
                  {imageCheck ? (
                    <Pressable
                      style={styles.imageWrapper}
                      onPress={() => setSelectModalVisible(true)}
                      android_ripple={{color: '#e1e2e3'}}>
                      <Image
                        style={styles.imageFull}
                        source={{uri: recipeMainImage}}
                        resizeMode="cover"
                      />
                    </Pressable>
                  ) : (
                    <Pressable
                      style={styles.imageWrapper}
                      onPress={() => setSelectModalVisible(true)}
                      android_ripple={{color: '#e1e2e3'}}>
                      <Image
                        style={styles.imageFull}
                        source={{
                          uri: `data:image/jpg;base64,${input.recipeMainImage}`,
                        }}
                        resizeMode="stretch"
                      />
                    </Pressable>
                  )}
                  <View style={styles.categoryWrapper}>
                    <Text style={styles.titleText}>????????????</Text>
                    <View style={styles.categoryInnerWrapper}>
                      <Picker
                        style={styles.categoryPicker}
                        mode={'dropdown'}
                        selectedValue={typeCategory}
                        dropdownIconColor={'#ff8527'}
                        onValueChange={(itemValue, itemIndex) =>
                          setTypeCategory(itemValue)
                        }>
                        <Picker.Item label="---?????????---" value="" />
                        <Picker.Item label="?????????" value="?????????" />
                        <Picker.Item label="????????????" value="????????????" />
                        <Picker.Item label="???/???/??????" value="???/???/??????" />
                        <Picker.Item label="???/??????" value="???/??????" />
                        <Picker.Item label="???/???/???" value="???/???/???" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item
                          label="??????/??????/???"
                          value="??????/??????/???"
                        />
                        <Picker.Item
                          label="??????/??????/???"
                          value="??????/??????/???"
                        />
                        <Picker.Item label="?????????" value="?????????" />
                        <Picker.Item label="???/??????/???" value="???/??????/???" />
                      </Picker>
                      <Picker
                        style={styles.categoryPicker}
                        mode={'dropdown'}
                        selectedValue={situationCategory}
                        dropdownIconColor={'#ff8527'}
                        onValueChange={(itemValue, itemIndex) =>
                          setSituationCategory(itemValue)
                        }>
                        <Picker.Item label="---?????????---" value="" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="????????????" value="????????????" />
                        <Picker.Item label="????????????" value="????????????" />
                        <Picker.Item label="?????????" value="?????????" />
                        <Picker.Item label="????????????" value="????????????" />
                        <Picker.Item label="?????????" value="?????????" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="?????????" value="?????????" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="?????????" value="?????????" />
                      </Picker>
                    </View>
                    <View style={styles.categoryInnerWrapper}>
                      <Picker
                        style={styles.categoryPicker}
                        mode={'dropdown'}
                        selectedValue={ingredientCategory}
                        dropdownIconColor={'#ff8527'}
                        onValueChange={(itemValue, itemIndex) =>
                          setIngredientCategory(itemValue)
                        }>
                        <Picker.Item label="---?????????---" value="" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="?????????" value="?????????" />
                        <Picker.Item label="????????????" value="????????????" />
                        <Picker.Item label="?????????" value="?????????" />
                        <Picker.Item label="?????????" value="?????????" />
                        <Picker.Item label="?????????" value="?????????" />
                        <Picker.Item label="??????/?????????" value="??????/?????????" />
                        <Picker.Item label="????????????" value="????????????" />
                        <Picker.Item label="???/??????" value="???/??????" />
                        <Picker.Item label="?????????" value="?????????" />
                        <Picker.Item label="????????????" value="????????????" />
                        <Picker.Item label="?????????" value="?????????" />
                        <Picker.Item label="?????????" value="?????????" />
                        <Picker.Item label="???/?????????" value="???/?????????" />
                      </Picker>
                      <Picker
                        style={styles.categoryPicker}
                        mode={'dropdown'}
                        selectedValue={methodCategory}
                        dropdownIconColor={'#ff8527'}
                        onValueChange={(itemValue, itemIndex) =>
                          setMethodCategory(itemValue)
                        }>
                        <Picker.Item label="---?????????---" value="" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="?????????" value="?????????" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="???" value="???" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="?????????" value="?????????" />
                        <Picker.Item label="???" value="???" />
                      </Picker>
                    </View>
                  </View>
                  <View style={styles.infoWrapper}>
                    <View style={styles.infoInnerWrapper}>
                      <Text style={styles.infoText}>?????? ??????</Text>
                      <Picker
                        style={styles.infoPicker}
                        mode={'dropdown'}
                        selectedValue={recipeTime}
                        onValueChange={(itemValue, itemIndex) =>
                          setRecipeTime(itemValue)
                        }>
                        <Picker.Item label="?????? ??????" value="" />
                        <Picker.Item label="10???" value="10" />
                        <Picker.Item label="20???" value="20" />
                        <Picker.Item label="30???" value="30" />
                        <Picker.Item label="40???" value="40" />
                        <Picker.Item label="50???" value="50" />
                        <Picker.Item label="1??????" value="60" />
                        <Picker.Item label="1?????? 10???" value="70" />
                        <Picker.Item label="1?????? 20???" value="80" />
                        <Picker.Item label="1?????? 30???" value="90" />
                        <Picker.Item label="1?????? 40???" value="100" />
                        <Picker.Item label="1?????? 50???" value="110" />
                        <Picker.Item label="2??????" value="120" />
                        <Picker.Item label="2?????? ??????" value="130" />
                      </Picker>
                    </View>
                    <View style={styles.infoInnerWrapper}>
                      <Text style={styles.infoText}>?????????</Text>
                      <Picker
                        style={styles.infoPicker}
                        mode={'dropdown'}
                        selectedValue={recipeLevel}
                        onValueChange={(itemValue, itemIndex) =>
                          setRecipeLevel(itemValue)
                        }>
                        <Picker.Item label="?????????" value="" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="??????" value="??????" />
                        <Picker.Item label="?????????" value="?????????" />
                      </Picker>
                    </View>
                    <View style={styles.infoInnerWrapper}>
                      <Text style={styles.infoText}>??????</Text>
                      <Picker
                        style={styles.infoPicker}
                        mode={'dropdown'}
                        selectedValue={recipeServes}
                        onValueChange={(itemValue, itemIndex) =>
                          setRecipeServes(itemValue)
                        }>
                        <Picker.Item label="??????" value="" />
                        <Picker.Item label="1??????" value="1??????" />
                        <Picker.Item label="2??????" value="2??????" />
                        <Picker.Item label="3??????" value="3??????" />
                        <Picker.Item label="4??????" value="4??????" />
                        <Picker.Item label="5?????? ??????" value="5?????? ??????" />
                      </Picker>
                    </View>
                  </View>
                  <View style={styles.descriptionWrapper}>
                    <Text style={styles.titleText}>?????? ??????</Text>
                    <TextInput
                      style={styles.inputDescription}
                      defaultValue={input.recipeDescription}
                      multiline={true}
                      autoCapitalize="none"
                      onChangeText={createChangeTextHandler(
                        'recipeDescription',
                      )}
                      underlineColorAndroid="transparent"
                      placeholder="?????? ????????? ??????????????????"
                    />
                  </View>
                  <View style={styles.ingredientWrapper}>
                    <Text style={styles.titleText}>??????</Text>
                    <InputIngredientList
                      input={input}
                      setInput={setInput}
                      handleIngredientNameChange={handleIngredientNameChange}
                      handleIngredientAmountChange={
                        handleIngredientAmountChange
                      }
                      removeIngredientInput={removeIngredientInput}
                    />
                    <View style={styles.addBtnWrapper}>
                      <Pressable
                        onPress={addIngredientInputs}
                        style={styles.addBtn}
                        android_ripple={{color: '#e1e2e3'}}>
                        <Icon name="add-circle" size={44} color={'#ffa856'} />
                        <Text style={styles.ingredientAddText}>?????? ??????</Text>
                      </Pressable>
                    </View>
                  </View>
                  <View style={styles.stepWrapper}>
                    <Text style={styles.titleText}>?????? ??????</Text>
                    <InputStepList
                      input={input}
                      setInput={setInput}
                      handleStepDescriptionChange={handleStepDescriptionChange}
                      removeStepInput={removeStepInput}
                    />
                    <View style={styles.addBtnWrapper}>
                      <Pressable
                        onPress={addStepInputs}
                        style={styles.addBtn}
                        android_ripple={{color: '#e1e2e3'}}>
                        <Icon name="add-circle" size={44} color={'#ffa856'} />
                        <Text style={styles.ingredientAddText}>
                          ???????????? ??????
                        </Text>
                      </Pressable>
                    </View>
                  </View>
                </View>
              )}
            />
          </View>
        </View>
      )}
    </View>
  );
};

export default RecipeUpdateScreen;

const styles = StyleSheet.create({
  fullScreen: {
    flex: 1,
    backgroundColor: '#f2f3f4',
  },
  loadingScreen: {
    flex: 1,
    backgroundColor: '#f2f3f4',
    alignItems: 'center',
    justifyContent: 'center',
  },
  header: {
    width: '95%',
    height: '5%',
    flexDirection: 'row',
    marginVertical: 15,
    marginHorizontal: 10,
    justifyContent: 'space-between',
  },
  btnWrapper: {},
  saveText: {
    fontFamily: 'NanumSquareRoundOTFB',
    fontSize: 22,
    color: '#ff8527',
    marginTop: 5,
    marginHorizontal: 10,
  },
  listWrapper: {
    width: '100%',
    height: '93%',
  },
  list: {
    width: '95%',
  },
  nameWrapper: {
    width: '100%',
    marginLeft: 10,
    padding: 10,
  },
  recipeName: {
    fontFamily: 'NanumSquareRoundOTFB',
    fontSize: 18,
    color: '#636773',
  },
  inputName: {
    width: '100%',
    fontFamily: 'NanumSquareRoundOTFR',
    fontSize: 16,
    color: '#000000',
    borderBottomColor: '#636773',
    borderBottomWidth: 0.7,
  },
  imageWrapper: {
    width: '100%',
    height: 500,
    marginTop: 15,
    marginLeft: 10,
    borderRadius: 10,
    backgroundColor: '#f2f3f4',
    alignItems: 'center',
    elevation: 5,
    marginBottom: 10,
  },
  imageFull: {
    width: '100%',
    height: '100%',
    borderRadius: 10,
  },
  image: {
    width: '100%',
    height: '85%',
    borderRadius: 10,
  },
  imageText: {
    fontFamily: 'NanumSquareRoundOTFB',
    fontSize: 22,
    color: '#000000',
    marginBottom: 5,
  },
  categoryWrapper: {
    width: '100%',
    marginLeft: 10,
    marginVertical: 10,
    backgroundColor: '#f2f3f4',
    padding: 10,
    elevation: 5,
    borderRadius: 10,
  },
  titleText: {
    fontFamily: 'NanumSquareRoundOTFB',
    fontSize: 20,
    color: '#636773',
  },
  categoryInnerWrapper: {
    width: '95%',
    flexDirection: 'row',
  },
  categoryPicker: {
    width: '50%',
    marginHorizontal: 5,
  },
  infoWrapper: {
    width: '100%',
    marginLeft: 10,
    marginVertical: 10,
    backgroundColor: '#f2f3f4',
    padding: 10,
    elevation: 5,
    borderRadius: 10,
    alignItems: 'center',
  },
  infoInnerWrapper: {
    width: '100%',
    marginLeft: 10,
    paddingHorizontal: 10,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  infoText: {
    fontFamily: 'NanumSquareRoundOTFB',
    fontSize: 18,
    color: '#636773',
  },
  infoPicker: {
    width: '50%',
    marginHorizontal: 5,
  },
  descriptionWrapper: {
    width: '100%',
    marginLeft: 10,
    marginVertical: 10,
    backgroundColor: '#f2f3f4',
    padding: 10,
    elevation: 5,
    borderRadius: 10,
  },
  inputDescription: {
    width: '90%',
    fontFamily: 'NanumSquareRoundOTFR',
    fontSize: 15,
    color: '#000',
  },
  ingredientWrapper: {
    width: '100%',
    marginLeft: 10,
    marginVertical: 10,
    backgroundColor: '#f2f3f4',
    padding: 10,
    elevation: 5,
    borderRadius: 10,
  },
  addBtnWrapper: {
    justifyContent: 'center',
    alignItems: 'center',
  },
  addBtn: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
  },
  ingredientAddText: {
    fontFamily: 'NanumSquareRoundOTFB',
    fontSize: 22,
    color: '#000',
    marginLeft: 10,
  },
  stepWrapper: {
    width: '100%',
    marginBottom: 50,
    marginLeft: 10,
    marginVertical: 10,
    backgroundColor: '#f2f3f4',
    padding: 10,
    elevation: 5,
    borderRadius: 10,
  },
});
