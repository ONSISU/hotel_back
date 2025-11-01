package com.example.hotel_back.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequestDTO {

    private String oauthId;

    @NotBlank
    @Email(message = "이메일 패턴이 아닙니다")
    private String email;

    @NotBlank
    @Size(max = 20)
    private String fullName;

    @NotBlank
    private String password;

}

