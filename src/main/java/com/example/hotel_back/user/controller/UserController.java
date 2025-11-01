package com.example.hotel_back.user.controller;

import com.example.hotel_back.user.dto.UserRequestDTO;
import com.example.hotel_back.user.dto.UserResponseDTO;
import com.example.hotel_back.user.dto.UserVerifyEmailDTO;
import com.example.hotel_back.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/sendEmail")
    public Boolean sendEmail(@RequestBody String email) {
        return userService.sendVerificationCode(email);
    }

    @PostMapping("/verifyEmail")
    public Boolean verifyEmail(@Valid @RequestBody UserVerifyEmailDTO dto) {
        return userService.verifyEmail(dto);
    }

    @PostMapping("/signup")
    public UserResponseDTO registerUser(@Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO 가입자정보 = userService.join(dto);

        return 가입자정보;
    }

    @PostMapping("/login")
    public UserResponseDTO lognUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO dto = userService.login(userRequestDTO);

        return dto;
    }

}
