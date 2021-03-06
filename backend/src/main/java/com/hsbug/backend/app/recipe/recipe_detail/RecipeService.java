package com.hsbug.backend.app.recipe.recipe_detail;

import com.hsbug.backend.app.recipe.recently_viewed_recipes.RecentlyViewRecipeDto;
import com.hsbug.backend.app.recipe.recipe_detail.recipeStep.RecipeStepDTO;
import com.hsbug.backend.app.recipe.recipe_detail.recipeStep.RecipeStepEntity;
import com.hsbug.backend.app.recipe.recipe_detail.recipeStep.RecipeStepRepository;
import com.hsbug.backend.app.recipe.recipe_detail.recipe_attribute.RecipeIngredients;
import com.hsbug.backend.app.recipe.recipe_detail.recipe_attribute.RecipeIngredientsDTO;
import com.hsbug.backend.app.recipe.recipe_detail.recipe_attribute.RecipeIngredientsRepository;
import com.hsbug.backend.app.search_recipe._refrigerator.SearchRecipeRefrigDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientsRepository recipeIngredientsRepository;
    private final RecipeStepRepository recipeStepRepository;

    @Transactional
    public Long saveRecipe(RecipeJsonDTO dto) {
        RecipeEntity recipe = dto.toEntity();       //레시피 객체생성
        List<RecipeIngredients> recipeIngredients = new ArrayList<>();
        List<RecipeStepEntity> recipeStepEntityList = new ArrayList<>();
        List<RecipeIngredientsDTO> dtoRecipeIngredientsList = dto.getRecipeIngredients();
        List<RecipeStepDTO> dtoRecipeStepEntityList = dto.getRecipeStep();

        recipeRepository.save(recipe);          //레시피 저장
        for (RecipeIngredientsDTO ingredients : dtoRecipeIngredientsList) {     //재료 저장
            RecipeIngredients recipeIngredient = ingredients.toEntity();
            recipeIngredient.setRecipeEntityId(recipe);
            recipeIngredients.add(recipeIngredient);
        }
        System.out.println(recipeIngredients.get(0).getRecipeIngredientsId());
        recipeIngredientsRepository.deleteAllByRecipeEntityIdId(recipeIngredients.get(0).getRecipeEntityId().getId());
        recipeIngredientsRepository.saveAll(recipeIngredients);

        for (RecipeStepDTO recipeStepEntity : dtoRecipeStepEntityList) {    //recipeStep 저장
            RecipeStepEntity recipeStep = recipeStepEntity.toEntity();
            recipeStep.setRecipeEntity(recipe);
            recipeStepEntityList.add(recipeStep);
        }
        recipeStepRepository.deleteAllByRecipeEntityId(recipeStepEntityList.get(0).getRecipeEntity().getId());
        recipeStepRepository.saveAll(recipeStepEntityList);

        return recipe.getId();
    }

    public void saveAPIRecipe(Long id, RecipeJsonDTO dto) {
        Optional<RecipeEntity> optionalRecipe = recipeRepository.findById(id);
        if (!optionalRecipe.isPresent()) {
            saveRecipe(dto);
        } else {    //값이 존재함으로 업데이트
            RecipeEntity recipeEntity = optionalRecipe.get();
            dto.setId(recipeEntity.getId());
            saveRecipe(dto);
        }
    }

    @Transactional
    public void recipeCount(Long id) {
        RecipeEntity recipeEntity = recipeRepository.findById(id).get();
        recipeEntity.setRecipeViews(recipeEntity.getRecipeViews()+1);
    }


    public RecipeJsonDTO findDetail(Long id) {
        RecipeJsonDTO dto = recipeRepository.findById(id).get().toDto();
        List<RecipeIngredients> ingredientsList = recipeIngredientsRepository.findAllByRecipeEntityId_Id(id);
        List<RecipeStepEntity> stepEntityList = recipeStepRepository.findAllByRecipeEntityId(id);
        dto.setRecipeIngredients(this.toIngredientsDtoList(ingredientsList));
        dto.setRecipeStep(this.toStepDtoList(stepEntityList));
        return dto;
    }


    public List<RecipeIngredientsDTO> toIngredientsDtoList(List<RecipeIngredients>ingredientsList) {
        List<RecipeIngredientsDTO> recipeIngredientsDTOList = new ArrayList<>();
        for (RecipeIngredients ingredients: ingredientsList) {
            RecipeIngredientsDTO dto = ingredients.toDto();
            recipeIngredientsDTOList.add(dto);
        }
        return recipeIngredientsDTOList;
    }

    public List<RecipeStepDTO> toStepDtoList(List<RecipeStepEntity> recipeStepEntityList) {
        List<RecipeStepDTO> recipeStepDTOList = new ArrayList<>();
        for (RecipeStepEntity recipeStepEntity : recipeStepEntityList) {
            RecipeStepDTO dto = recipeStepEntity.toDto();
            recipeStepDTOList.add(dto);
        }
        return recipeStepDTOList;
    }

    // 좋아요 빼기
    public  void decreaseNum(Long id){
        Optional<RecipeEntity> recipeEntity = recipeRepository.findById(id);
        int book = recipeEntity.get().getRecipeLikes();
        if(book == 1){
            recipeEntity.get().setRecipeLikes(0);
        }else{
            book = book -1;
            recipeEntity.get().setRecipeLikes(book);
        }

        recipeRepository.save(recipeEntity.get());
    }
    //좋아요 더하기
    public void inceaseNum(Long id) {
        Optional<RecipeEntity> recipeEntity = recipeRepository.findById(id);
        int book = recipeEntity.get().getRecipeLikes();
        if (book == 0){
            recipeEntity.get().setRecipeLikes(1);
        }
        else{
            book = book + 1;
            recipeEntity.get().setRecipeLikes(book);
        }
        recipeRepository.save(recipeEntity.get());
    }

    public List<RecentlyViewRecipeDto> findCategoryRecipe(String category) {

        List<RecipeEntity> recipeCategoryList = new ArrayList<>();

        List<RecipeEntity> ingredientCategoryList = recipeRepository.findAllByIngredientCategory(category);
        if (!ingredientCategoryList.isEmpty()) {
            recipeCategoryList.addAll(ingredientCategoryList);
        } else {
            List<RecipeEntity> methodCategoryList = recipeRepository.findAllByMethodCategory(category);
            if (!methodCategoryList.isEmpty()) {
                recipeCategoryList.addAll(methodCategoryList);
            } else {
                List<RecipeEntity> typeCategoryList = recipeRepository.findAllByTypeCategory(category);
                if (!typeCategoryList.isEmpty()) {
                    recipeCategoryList.addAll(typeCategoryList);
                } else {
                    List<RecipeEntity> situationCategoryList = recipeRepository.findAllBySituationCategory(category);
                    recipeCategoryList.addAll(situationCategoryList);
                }
            }
        }
        return toRecentlyViewRefrigDto(recipeCategoryList);
    }

    public List<SearchRecipeRefrigDto> toSearchRecipeRefrigDto(List<RecipeEntity> recipeEntityList) {
        List<SearchRecipeRefrigDto> refrigDtos = new ArrayList<>();
        for (RecipeEntity recipe: recipeEntityList) {
            refrigDtos.add(SearchRecipeRefrigDto.builder()
                    .id(recipe.getId())
                    .recipeName(recipe.getRecipeName())
                    .recipeImg(recipe.getRecipeMainImage())
                    .views(recipe.getRecipeViews())
                    .stars(recipe.getRecipeStar())
                    .build()
            );
        }
        return refrigDtos;
    }

    public List<RecentlyViewRecipeDto> toRecentlyViewRefrigDto(List<RecipeEntity> recipeEntityList) {
        List<RecentlyViewRecipeDto> refrigDtos = new ArrayList<>();
        for (RecipeEntity recipe: recipeEntityList) {
            refrigDtos.add(RecentlyViewRecipeDto.builder()
                    .id(recipe.getId())
                    .recipeName(recipe.getRecipeName())
                    .recipeMainImage(recipe.getRecipeMainImage())
                    .recipeViews(recipe.getRecipeViews())
                    .recipeStars(recipe.getRecipeStar())
                    .recipeWriter((recipe.getRecipeWriter()))
                    .build()
            );
        }
        return refrigDtos;
    }

    public List<SearchRecipeRefrigDto> mostViewRecipe(){
        List<RecipeEntity> top10ByRecipeViews = recipeRepository.findTop10ByOrderByRecipeViewsDesc();
        return toSearchRecipeRefrigDto(top10ByRecipeViews);
    }

}
