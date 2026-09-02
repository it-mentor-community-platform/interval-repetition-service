package com.itmentorcommunityplatform.intervalrepetitionservice.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user_category_selections")
public class UserCategorySelection {

    @Id
    private Long id;

    private Long categoryId;

    private Long userId;

    public UserCategorySelection(Long categoryId, Long userId) {
        this.categoryId = categoryId;
        this.userId = userId;
    }
}
