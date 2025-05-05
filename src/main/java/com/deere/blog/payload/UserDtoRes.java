package com.deere.blog.payload;


import com.deere.blog.serializer.MaskingSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRes {

    private int id;
    private String name;
    private String email;
    @JsonSerialize(using = MaskingSerializer.class)
    private String password;
    private String about;
    private Set<RoleDto> roles = new HashSet<>();

}
