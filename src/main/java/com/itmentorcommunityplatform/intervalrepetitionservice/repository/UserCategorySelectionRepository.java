package com.itmentorcommunityplatform.intervalrepetitionservice.repository;

import com.itmentorcommunityplatform.intervalrepetitionservice.entity.UserCategorySelection;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;



public interface UserCategorySelectionRepository extends CrudRepository<UserCategorySelection, Long> {

    @Modifying
    @Query("""
            DELETE FROM user_category_selections
            WHERE user_id = :userId
                  AND category_id = :categoryId 
            """)
    void deleteByUserIdAndCategoryId(Long userId, Long categoryId);

}
