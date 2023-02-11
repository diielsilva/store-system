package com.dev.storesystem.common.dtos.user;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserDto {
    @NotBlank(message = "O nome do usuário não pode ser nulo!")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
