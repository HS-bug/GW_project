package com.hsbug.backend.app.manage_user_info.bookmark_recipe;

import com.hsbug.backend.app.recipe.recipe_detail.RecipeService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user/bookmark")
@RequiredArgsConstructor
public class BookmarkRecipeController {

    private final BookmarkRecipeService bookmarkRecipeService;
    private final RecipeService recipeService;

    @PostMapping("/addBookmark")
    public JSONObject addBookmark(@RequestParam Long id) {
        JSONObject obj = new JSONObject();
        try {
            String email = findEmail();
            bookmarkSaveValidation(id, email, obj);
            recipeService.inceaseNum(id);
            obj.put("status",200);
            return obj;
        }catch (Exception e){
            obj.put("status",201);
            return obj;
        }
    }

    @GetMapping("/readBookmark")
    public JSONObject readBookmark() {
        String email = findEmail();
        return bookmarkRecipeService.findRecipe(email);
    }

    @PostMapping("/deleteBookmark")
    public JSONObject deleteBookMark(@RequestParam Long id) {
        JSONObject obj = new JSONObject();
        try {
            String email = findEmail();
            bookmarkRecipeService.deleteBookmark(email, id);
            recipeService.decreaseNum(id);
            obj.put("message", "해당 북마크의 삭제가 완료되었습니다.");
            obj.put("status", 200);
            return obj;
        }catch (Exception e) {
            obj.put("status",201);
            return obj;
        }
    }

    private String findEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return email;
    }

    private void bookmarkSaveValidation(Long id, String email, JSONObject obj) {
        BookmarkRecipeDto bookmarkRecipeDto = bookmarkRecipeService.getUserBookmark(email);
        if (bookmarkRecipeDto == null) { // 북마크 0일 때
            List<Long> recipeList = new ArrayList<>();
            recipeList.add(id);
            BookmarkRecipeDto bookmark = new BookmarkRecipeDto(0L, email, recipeList);
            bookmarkRecipeService.saveRecipe(bookmark);
        } else { // 북마크 한 것이 있을 때
            List<Long> recipeList = bookmarkRecipeDto.getRecipe_id();
            System.out.println(recipeList);
            if (!recipeList.contains(id)) {
                recipeList.add(id);
                bookmarkRecipeDto.setRecipe_id(recipeList);
                System.out.println(recipeList);
                bookmarkRecipeService.saveRecipe(bookmarkRecipeDto);
            } else {
                obj.put("message", "이미 북마크에 저장 됨.");
                obj.put("status",200);
            }
        }
    }
}

