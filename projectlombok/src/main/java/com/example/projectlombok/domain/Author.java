package com.example.projectlombok.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    private String givenName;

    private String FamilyName;

    private Integer age;
}
