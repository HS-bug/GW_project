package com.hsbug.backend.app.recipe.recipe_ratings;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/user/rating")
@RequiredArgsConstructor
public class RecipeRatingController {
    private final RecipeRatingService recipeRatingService;

    @PostMapping("/add")
    public JSONObject addRating(@RequestBody RecipeRatingDto dto) {
        JSONObject obj = new JSONObject();
        dto.setUser(this.findEmail());
        boolean check = recipeRatingService.isAlreadyHasRecipe(dto);
        if (check == false) {
            recipeRatingService.saveRating(dto.toEntity());
            recipeRatingService.ratingAverage(dto.getRecipeId());
            obj.put("result", "레시피 평가가 완료되었습니다. 감사합니다.");
        } else {
            recipeRatingService.editRating(dto);
            recipeRatingService.ratingAverage(dto.getRecipeId());
            obj.put("result", "기존의 평점이 변경되었습니다.");
        }
        obj.put("status",200);
        return obj;
    }

    private String findEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return email;
    }
}
