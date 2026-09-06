package com.itmentorcommunityplatform.intervalrepetitionservice.repository;

import com.itmentorcommunityplatform.intervalrepetitionservice.entity.UserCategorySelection;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserCategorySelectionRepository extends CrudRepository<UserCategorySelection, Long> {

    @Query("""
                SELECT category_id
                FROM user_category_selections
                WHERE user_id = :userId
            """)
    List<Long> findCategoryIdsByUserId(Long userId);

}
