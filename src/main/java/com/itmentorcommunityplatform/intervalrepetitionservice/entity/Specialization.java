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
@Table("specializations")
public class Specialization {


        @Id
        private Long id;

        private String name;


        public Specialization(String name) {
                this.name = name;
        }
}