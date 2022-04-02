package com.hsbug.backend.admin_page.recipe.recipe_attribute;

import com.hsbug.backend.admin_page.recipe.recipe.RecipeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RecipeIngredients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeIngredientsId;

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID")
    private RecipeEntity recipeEntityId;

    private String ingredientName;
    private String ingredientAmount;

    public RecipeIngredients(String ingredientName, String ingredientAmount) {
        this.ingredientName = ingredientName;
        this.ingredientAmount = ingredientAmount;
    }

    public RecipeIngredientsDTO toDto() {
        RecipeIngredientsDTO dto = new RecipeIngredientsDTO();
        dto.setIngredientName(this.ingredientName);
        dto.setIngredientAmount(this.ingredientAmount);
        return dto;
    }

}
