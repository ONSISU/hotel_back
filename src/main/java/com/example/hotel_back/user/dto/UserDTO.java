package com.example.hotel_back.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long userId;

    private int oauthId;

    @Email(message = "이메일 패턴이 아닙니다")
    @NotBlank
    private String email;

    @Size(min = 4, message = "최소 4글자 이상")
    @NotBlank
    private String fullName;

    @Pattern(regexp = "(^$|[0-9]{10})")
    private String phone;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private ProfilePhotoDTO profilePhoto;

}
