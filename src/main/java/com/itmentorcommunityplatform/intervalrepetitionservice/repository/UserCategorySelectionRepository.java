package com.itmentorcommunityplatform.intervalrepetitionservice.repository;

import com.itmentorcommunityplatform.intervalrepetitionservice.entity.UserCategorySelection;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface UserCategorySelectionRepository extends CrudRepository<UserCategorySelection, Long> {

    Set<Long> findCategoryIdsByUserId(Long userId);

}
