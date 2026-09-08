package com.itmentorcommunityplatform.intervalrepetitionservice.repository;

import com.itmentorcommunityplatform.intervalrepetitionservice.entity.UserCategorySelection;
import org.springframework.data.repository.CrudRepository;



public interface UserCategorySelectionRepository extends CrudRepository<UserCategorySelection, Long> {

    void deleteByUserIdAndCategoryId(Long userId, Long categoryId);

}
