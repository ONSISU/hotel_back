package com.example.hotel_back.user.controller;

import com.example.hotel_back.user.dto.UserRequestDTO;
import com.example.hotel_back.user.dto.UserResponseDTO;
import com.example.hotel_back.user.dto.UserVerifyEmailDTO;
import com.example.hotel_back.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserController {

	private final UserService userService;

	@PostMapping("/me")
	public UserResponseDTO userMe(
					@CookieValue(name = "accessToken", required = false) String accessToken,
					@CookieValue(name = "refreshToken", required = false) String refreshToken
	) throws Exception {

		return userService.getUserInfo(accessToken, refreshToken);
	}

	@PostMapping("/sendEmail")
	public Boolean sendEmail(@RequestBody Map<String, String> emailMap) {

		return userService.sendVerificationCode(emailMap.get("email"));
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
	public UserResponseDTO loginUser(@RequestBody UserRequestDTO userRequestDTO, HttpServletResponse response) {
		UserResponseDTO dto = userService.login(userRequestDTO);

		// Cookie에 저장하기..
		ResponseCookie accessToken = ResponseCookie.from("accessToken", dto.getAccessToken())
						.path("/")
						.maxAge(15 * 60)          // 15분
						.sameSite("Lax")
						.secure(false) // 테스트용
						.build();

		ResponseCookie refreshToken = ResponseCookie.from("refreshToken", dto.getRefreshToken())
						.path("/")
						.httpOnly(true)           // 🔐 핵심
						.secure(false) // 테스트용
						.sameSite("Lax")
						.maxAge(7 * 24 * 60 * 60) // 7일
						.build();

		response.addHeader(HttpHeaders.SET_COOKIE, accessToken.toString());
		response.addHeader(HttpHeaders.SET_COOKIE, refreshToken.toString());

		return dto;
	}

	@PostMapping("/testJoin")
	public UserResponseDTO testRegisterUser(@Valid @RequestBody UserRequestDTO dto) {
		UserResponseDTO 가입자정보 = userService.testJoin(dto);

		return 가입자정보;
	}

}
