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
@Table("categories")
public class Category {

    @Id
    private Long id;

    private Long specializationId;

    private String name;


    public Category(Long specializationId, String name) {
        this.specializationId = specializationId;
        this.name = name;
    }
}