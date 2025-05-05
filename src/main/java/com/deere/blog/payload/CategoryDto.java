package com.deere.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Integer categoryId;
    @NotEmpty
    @Size(min = 4,message = "Category title must be min of 4 characters !!")
    private String categoryTitle;
    @NotEmpty
    @Size(min = 10,message = "Category description must be min of 10 characters !!")
    private String categoryDesc;
}
