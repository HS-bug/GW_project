package com.hsbug.backend.app.recipe.recipe_detail;

import com.hsbug.backend.app.recipe.recipe_detail.recipeStep.RecipeStepDTO;
import com.hsbug.backend.app.recipe.recipe_detail.recipe_attribute.RecipeIngredientsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeJsonDTO {

    private Long id;
    private String recipeName;
    private String recipeWriter;
    private String recipeEmail;
    private String recipeMainImage;
    private String typeCategory;
    private String situationCategory;
    private String ingredientCategory;
    private String methodCategory;
    private int recipeLikes; //OK
    private int recipeViews;
    private float recipeStar; //recipeRationgs
    private int recipeRatingCount;
    private String recipeTime;
    private String recipeLevel;
    private String recipeServes;
    private String recipeDescription;
    private List<RecipeIngredientsDTO> recipeIngredients = new ArrayList<>();  //ok
    private List<RecipeStepDTO> recipeStep = new ArrayList<>();    //ok

    public RecipeEntity toEntity() {
        return RecipeEntity.builder()
                .id(this.id)
                .recipeName(this.recipeName)
                .recipeWriter(this.recipeWriter)
                .recipeEmail(this.recipeEmail)
                .recipeMainImage(this.recipeMainImage)
                .typeCategory(this.typeCategory)
                .situationCategory(this.situationCategory)
                .ingredientCategory(this.ingredientCategory)
                .methodCategory(this.methodCategory)
                .recipeLikes(this.recipeLikes)
                .recipeViews(this.recipeViews)
                .recipeStar(this.recipeStar)
                .recipeRatingCount(this.recipeRatingCount)
                .recipeTime(this.recipeTime)
                .recipeLevel(this.recipeLevel)
                .recipeServes(this.recipeServes)
                .recipeDescription(this.recipeDescription)
                .build();
    }
}

/*  ????????? ?????? ??????
* {
      id: 1,
      recipeName: '??????????????? ??????',
      recipeNumber: '123',
      recipeWriter: 'ssh',
      recipeMainImage: '',
      recipeLikes: '100',
      recipeViews: '200',
      recipeRatings: '4.5',
      recipeRatingsCount: '50',
      recipeTime: '100',
      recipeLevel: '?????????',
      recipeServes: '3',
      recipeDescription: '?????? ?????? ?????????????????? ??????????????????.',
      recipeIngredients: [
        {ingredientName: '??????', ingredientAmount: '100g'},
        {ingredientName: '??????', ingredientAmount: '100g'},
        {ingredientName: '??????', ingredientAmount: '100g'},
        {ingredientName: '??????', ingredientAmount: '100g'},
        {ingredientName: '??????', ingredientAmount: '200g'},
        {ingredientName: '??????', ingredientAmount: '300g'},
      ],
      recipeStep: [
        {
          stepImage:
            'https://cdn.pixabay.com/photo/2022/02/23/18/11/drink-7031154_960_720.jpg',
          stepDescription:
            '????????? ??????????????????~????????? ??????????????????~????????? ??????????????????~????????? ??????????????????~',
        },
        {
          stepImage:
            'https://cdn.pixabay.com/photo/2022/02/23/18/11/drink-7031154_960_720.jpg',
          stepDescription: '????????? ??????????????????~',
        },
        {
          stepImage:
            'https://cdn.pixabay.com/photo/2022/02/23/18/11/drink-7031154_960_720.jpg',
          stepDescription: '????????? ??????????????????~',
        },
      ],
    },
  ]);
* */
