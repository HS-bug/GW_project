package com.hsbug.backend.app.manage_user_info.bookmark_recipe;

import com.hsbug.backend.app.refrigerator.manage_product.ManageProductDto;
import lombok.Getter;
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

    @PostMapping("/addBookmark")
    public JSONObject addBookmark(@RequestParam Long id){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        BookmarkRecipeDto bookmarkRecipeDto = bookmarkRecipeService.getUserBookmark(email);
        if (bookmarkRecipeDto == null){ // 북마크 0일 때
            List<Long> recipeList = new ArrayList<>();
            recipeList.add(id);
            BookmarkRecipeDto bookmark = new BookmarkRecipeDto(0L,email, recipeList);
            bookmarkRecipeService.saveRecipe(0L,bookmark);
        }else{ // 북마크 한 것이 있을 때
            List<Long> recipeList = bookmarkRecipeDto.getRecipe_id();
            System.out.println(recipeList);
            recipeList.add(id);
            bookmarkRecipeDto.setRecipe_id(recipeList);
            System.out.println(recipeList);
            bookmarkRecipeService.saveRecipe(bookmarkRecipeDto.getId(),bookmarkRecipeDto);
        }
        JSONObject obj = new JSONObject();
        return obj;
    }

    @GetMapping("/readBookmark")
    public JSONObject readBookmark(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return bookmarkRecipeService.findRecipe(email);
    }
}

