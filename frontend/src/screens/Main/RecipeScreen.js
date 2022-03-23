import {
  FlatList,
  Image,
  ImageBackground,
  Pressable,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import React, {useState} from 'react';
import {useNavigation} from '@react-navigation/native';
import Icon from 'react-native-vector-icons/MaterialIcons';
import CommunityIcon from 'react-native-vector-icons/MaterialCommunityIcons';
import {Rating} from 'react-native-ratings';
import IngredientList from 'components/Recipe/IngredientList';
import StepList from 'components/Recipe/StepList';

const RecipeScreen = () => {
  const navigation = useNavigation();

  // const DATA = [
  //   {
  //     id: '1',
  //     name: 'recipe',
  //     image: 'image',
  //   },
  // ];

  const [recipe, setRecipe] = useState([
    {
      id: 1,
      recipeName: '콤비네이션 피자',
      recipeNumber: '123',
      recipeWriter: 'ssh',
      recipeMainImage: '',
      recipeLikes: '100',
      recipeViews: '200',
      recipeRatings: '4.5',
      recipeRatingsCount: '50',
      recipeTime: '100',
      recipeLevel: '어려움',
      recipeServes: '3',
      recipeDescription: '요리 설명 피자먹고싶다 피자먹고싶다.',
      recipeIngredients: [
        {ingredientName: '양파', ingredientAmount: '100g'},
        {ingredientName: '양파', ingredientAmount: '100g'},
        {ingredientName: '양파', ingredientAmount: '100g'},
        {ingredientName: '양파', ingredientAmount: '100g'},
        {ingredientName: '대파', ingredientAmount: '200g'},
        {ingredientName: '쪽파', ingredientAmount: '300g'},
      ],
      recipeStep: [
        {
          stepImage:
            'https://cdn.pixabay.com/photo/2022/02/23/18/11/drink-7031154_960_720.jpg',
          stepDescription:
            '피자는 시켜먹어야지~피자는 시켜먹어야지~피자는 시켜먹어야지~피자는 시켜먹어야지~',
        },
        {
          stepImage:
            'https://cdn.pixabay.com/photo/2022/02/23/18/11/drink-7031154_960_720.jpg',
          stepDescription: '피자는 시켜먹어야지~',
        },
        {
          stepImage:
            'https://cdn.pixabay.com/photo/2022/02/23/18/11/drink-7031154_960_720.jpg',
          stepDescription: '피자는 시켜먹어야지~',
        },
      ],
    },
  ]);

  const [showRating, setShowRating] = useState('');

  const ratingStart = rating => {
    setShowRating(rating);
  };

  const ratingCompleted = rating => {
    setShowRating(rating);
  };

  return (
    <View style={styles.fullScreen}>
      <View style={styles.header}>
        <Pressable onPress={() => navigation.goBack()}>
          <Icon name="arrow-back" size={32} color={'#ff8527'} />
        </Pressable>
        <View style={styles.btnWrapper}>
          <Pressable onPress={() => console.log('hi')}>
            <CommunityIcon name="heart-outline" size={32} color={'#ff8527'} />
          </Pressable>
        </View>
      </View>
      <View style={styles.listWrapper}>
        <FlatList
          data={recipe}
          renderItem={({item}) => (
            <View>
              <View style={styles.titleWrapper}>
                <ImageBackground
                  source={require('../../assets/images/pizza.jpg')}
                  style={styles.image}
                  resizeMode="stretch">
                  <View style={styles.nameWrapper}>
                    <Text style={styles.recipeName}>{item.recipeName}</Text>
                  </View>
                </ImageBackground>
                <View style={styles.infoWrapper}>
                  <View style={styles.likeWrapper}>
                    <CommunityIcon
                      name="heart-outline"
                      size={24}
                      color={'#ff8527'}
                    />
                    {/* 좋아요 참여자 수 */}
                    <Text style={styles.likeText}>{item.recipeLikes}</Text>
                    {/* 조회 수 */}
                    <Text style={styles.viewsText}>
                      조회수 {item.recipeViews}
                    </Text>
                  </View>
                  <View style={styles.ratingShowWrapper}>
                    <Rating
                      type="custom"
                      ratingCount={5}
                      imageSize={20}
                      readonly
                      ratingColor="#ff8527"
                      ratingBackgroundColor="#fff"
                      startingValue={item.recipeRatings}
                      jumpValue={0.5}
                      onStartRating={ratingStart}
                      onFinishRating={ratingCompleted}
                    />
                    {/* 평점 참여자 수 */}
                    <Text style={styles.ratingText}>
                      ({item.recipeRatingsCount})
                    </Text>
                  </View>
                </View>
                <View style={styles.iconWrapper}>
                  <View style={styles.clockWrapper}>
                    <CommunityIcon
                      name="clock-outline"
                      size={32}
                      color={'#ff8527'}
                    />
                    <Text style={styles.clockText}>{item.recipeTime}분</Text>
                  </View>
                  <View style={styles.levelWrapper}>
                    <CommunityIcon
                      name="chef-hat"
                      size={32}
                      color={'#ff8527'}
                    />
                    <Text style={styles.levelText}>{item.recipeLevel}</Text>
                  </View>
                  <View style={styles.servesWrapper}>
                    <Icon name="local-dining" size={32} color={'#ff8527'} />
                    <Text style={styles.servesText}>
                      {item.recipeServes} 인분
                    </Text>
                  </View>
                </View>
              </View>
              <View style={styles.descriptionWrapper}>
                <Text style={styles.description}>{item.recipeDescription}</Text>
              </View>
              <View style={styles.ingredientWrapper}>
                <Text style={styles.ingredientTitle}>[재료]</Text>
                {/* 재료 리스트 컴포넌트 */}
                <IngredientList recipe={recipe} setRecipe={setRecipe} />
                {/* <IngredientList recipeIngredients={recipe.recipeIngredients} /> */}
              </View>
              <View style={styles.recipeWrapper}>
                {/* 레시피 리스트 컴포넌트 */}
                <StepList recipe={recipe} setRecipe={setRecipe} />
              </View>
              <View style={styles.ratingWrapper}>
                {/* 평점 컴포넌트 */}
                <Text style={styles.rating}>
                  Lorem ipsum dolor sit amet consectetur adipisicing elit. Alias
                  ad ipsa libero quos assumenda aliquid magni porro modi
                  consequatur velit!
                </Text>
              </View>
            </View>
          )}
        />
      </View>
    </View>
  );
};

export default RecipeScreen;

const styles = StyleSheet.create({
  fullScreen: {
    flex: 1,
    backgroundColor: '#f2f3f4',
  },
  header: {
    width: '90%',
    height: '4%',
    flexDirection: 'row',
    marginVertical: 15,
    marginHorizontal: 10,
    justifyContent: 'space-between',
  },
  listWrapper: {
    width: '100%',
    height: '93%',
  },
  titleWrapper: {
    alignItems: 'center',
    height: 550,
  },
  image: {
    width: '100%',
    height: '70%',
    justifyContent: 'flex-end',
    alignItems: 'center',
  },
  nameWrapper: {
    width: '90%',
    height: '40%',
    justifyContent: 'center',
    padding: 10,
    borderTopLeftRadius: 20,
    borderTopRightRadius: 20,
    backgroundColor: '#fff',
    elevation: 3,
  },
  recipeName: {
    fontFamily: 'NanumSquareRoundOTFB',
    fontSize: 26,
    color: '#000',
    marginTop: 15,
    marginHorizontal: 15,
  },
  infoWrapper: {
    width: '90%',
    borderBottomWidth: 0.7,
    borderBottomColor: '#b3b4ba',
    flexDirection: 'row',
    backgroundColor: '#fff',
    alignItems: 'center',
    elevation: 3,
    justifyContent: 'center',
  },
  likeWrapper: {
    flexDirection: 'row',
    backgroundColor: '#fff',
    paddingLeft: 10,
    alignItems: 'center',
    paddingBottom: 10,
  },
  likeText: {
    fontFamily: 'NanumSquareRoundOTFR',
    fontSize: 15,
    color: '#636773',
    marginLeft: 5,
    paddingRight: 10,
    borderRightWidth: 1,
    borderRightColor: '#b3b4ba',
  },
  viewsText: {
    fontFamily: 'NanumSquareRoundOTFR',
    fontSize: 15,
    color: '#636773',
    marginLeft: 10,
  },
  ratingShowWrapper: {
    backgroundColor: '#fff',
    flexDirection: 'row',
    alignItems: 'center',
    marginLeft: 15,
    justifyContent: 'center',
    marginBottom: 10,
  },
  ratingText: {
    fontFamily: 'NanumSquareRoundOTFR',
    fontSize: 15,
    color: '#636773',
    marginLeft: 5,
  },
  iconWrapper: {
    width: '90%',
    height: '23%',
    backgroundColor: '#fff',
    flexDirection: 'row',
    borderBottomLeftRadius: 20,
    borderBottomRightRadius: 20,
    justifyContent: 'center',
    paddingTop: 10,
    elevation: 3,
  },
  clockWrapper: {
    width: '30%',
    height: '80%',
    justifyContent: 'center',
    alignItems: 'center',
  },
  clockText: {
    fontFamily: 'NanumSquareRoundOTFB',
    fontSize: 16,
    color: '#000',
    marginTop: 10,
  },
  levelWrapper: {
    width: '30%',
    height: '80%',
    justifyContent: 'center',
    alignItems: 'center',
    borderLeftWidth: 0.5,
    borderLeftColor: '#636773',
    borderRightWidth: 0.5,
    borderRightColor: '#636773',
  },
  levelText: {
    fontFamily: 'NanumSquareRoundOTFB',
    fontSize: 16,
    color: '#000',
    marginTop: 10,
  },
  servesWrapper: {
    width: '30%',
    height: '80%',
    justifyContent: 'center',
    alignItems: 'center',
  },
  servesText: {
    fontFamily: 'NanumSquareRoundOTFB',
    fontSize: 16,
    color: '#000',
    marginTop: 10,
  },
  descriptionWrapper: {
    width: '90%',
    height: 150,
    backgroundColor: '#fff',
    borderRadius: 20,
    marginTop: 10,
    marginLeft: 20,
    elevation: 3,
    paddingHorizontal: 15,
    paddingVertical: 5,
  },
  description: {
    fontFamily: 'NanumSquareRoundOTFR',
    fontSize: 16,
    color: '#000',
    marginTop: 15,
  },
  ingredientWrapper: {
    width: '90%',
    // height: 300,
    backgroundColor: '#fff',
    borderRadius: 20,
    marginTop: 10,
    marginLeft: 20,
    elevation: 3,
    paddingHorizontal: 15,
    paddingVertical: 10,
  },
  ingredientTitle: {
    fontFamily: 'NanumSquareRoundOTFB',
    fontSize: 24,
    color: '#000',
  },
  recipeWrapper: {
    width: '90%',
    // height: 900,
    backgroundColor: '#fff',
    borderRadius: 20,
    marginTop: 10,
    marginLeft: 20,
    elevation: 3,
    paddingHorizontal: 15,
    paddingVertical: 10,
  },
  ratingWrapper: {
    width: '90%',
    // height: 900,
    backgroundColor: '#fff',
    borderRadius: 20,
    marginTop: 10,
    marginLeft: 20,
    marginBottom: 10,
    elevation: 3,
    paddingHorizontal: 15,
    paddingVertical: 10,
  },
});
