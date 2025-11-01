package com.example.hotel_back.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserJoinRequestDTO {

    @NotBlank
    @Email(message = "이메일 패턴이 아닙니다")
    private String email;

    @NotBlank
    @Size(min = 4)
    private String password;

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(min = 4, max = 4)
    private String verificationCode;

}
