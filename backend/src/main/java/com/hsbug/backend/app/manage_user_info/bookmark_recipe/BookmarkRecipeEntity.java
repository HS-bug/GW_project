package com.hsbug.backend.app.manage_user_info.bookmark_recipe;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class BookmarkRecipeEntity {
    @Id @Column(name="bookmark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    @ElementCollection
    private List<Long> recipe_id;

    @Builder
    public BookmarkRecipeEntity(Long id, String email, List<Long> recipe_id){
        this.id = id;
        this.email = email;
        this.recipe_id = recipe_id;
    }

}
