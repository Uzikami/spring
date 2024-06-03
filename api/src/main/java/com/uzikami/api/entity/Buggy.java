package com.uzikami.api.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Buggy {
    @Size(min = 2, message="user name should have at least 2 characters")
    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;
}
