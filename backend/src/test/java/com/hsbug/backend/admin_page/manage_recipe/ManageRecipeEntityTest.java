package com.hsbug.backend.admin_page.manage_recipe;

import com.hsbug.backend.admin_page.recipe_attribute.RecipeIngredients;
import com.hsbug.backend.admin_page.recipe_attribute.RecipeIngredientsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ManageRecipeEntityTest {

    @Autowired
    private ManageRecipeRepository recipeRepository;
    @Autowired
    private RecipeIngredientsRepository recipeIngredientsRepository;

    @Test
    public void test() {
        ManageRecipeEntity recipeEntity = new ManageRecipeEntity();
        recipeEntity.setINFO_NA("가ㅣ가ㅏ마ㅏ가가");
        recipeRepository.save(recipeEntity);

        RecipeIngredients recipeIngredients = new RecipeIngredients();
        recipeIngredients.setIngredientName("나나나나ㅏ나ㅏ난");

        recipeIngredients.setRecipeEntityId(recipeEntity);
        recipeIngredientsRepository.save(recipeIngredients);
        System.out.println("재료아이디: " + recipeIngredientsRepository.findByINGREDIENTSNAME("나나나나ㅏ나ㅏ난").getRecipeIngredientsId());
        System.out.println("레시피 아이디: "+recipeIngredientsRepository.findByINGREDIENTSNAME("나나나나ㅏ나ㅏ난").getRecipeEntityId().getId());
        System.out.println("레시피 이름"+recipeIngredientsRepository.findByINGREDIENTSNAME("나나나나ㅏ나ㅏ난").getIngredientName());

    }

}