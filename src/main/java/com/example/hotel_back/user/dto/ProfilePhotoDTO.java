package com.example.hotel_back.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfilePhotoDTO {

    private Long profilePhotoId;
    private String photoName;
    private String photoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
