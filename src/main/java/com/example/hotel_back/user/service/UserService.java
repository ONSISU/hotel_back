package com.example.hotel_back.user.service;

import com.example.hotel_back.common.exception.user.*;
import com.example.hotel_back.common.util.EmailUtil;
import com.example.hotel_back.common.util.JwtUtil;
import com.example.hotel_back.user.dto.UserRequestDTO;
import com.example.hotel_back.user.dto.UserResponseDTO;
import com.example.hotel_back.user.dto.UserVerifyEmailDTO;
import com.example.hotel_back.user.entity.User;
import com.example.hotel_back.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final EmailUtil emailUtil;

	// 단순 이메일 전송
	public Boolean sendVerificationCode(String email) {
		try {
			emailUtil.sendVerificationEmail(email);
		} catch (Exception e) {
			log.info("::: {}", e.getMessage());
		}

		return true;

	}

	// 이메일 검증
	public Boolean verifyEmail(UserVerifyEmailDTO dto) {
		Boolean 인증여부 = emailUtil.verifyCode(dto.getEmail(), dto.getVerificationCode());
		if (!인증여부) throw new FailVerifiedEmailException("이메일 인증에 실패하였습니다.");

		return 인증여부;
	}

	public UserResponseDTO join(UserRequestDTO dto) {

		// 👉중복회원 확인
		Optional<User> user = userRepository.findByEmail(dto.getEmail());

		if (user.isPresent()) {
			throw new DuplicationUserException("이미 가입된 정보가 있습니다");
		}

		// 👉패스워드 base64처리
		String encodedPassword = passwordEncoder.encode(dto.getPassword());

		// 👉api로 들어오는 경우 차단을 위한 이메일 재검증
		Boolean 인증완료여부 = emailUtil.isDoneVerifiedEmail(dto.getEmail());
		if (!인증완료여부) throw new FailVerifiedEmailException("이메일 인증에 실패하였습니다.");

		// ✅ 모든 검증이 끝나고 회원가입 처리 시작
		User joinedUser = User.builder()
						.email(dto.getEmail())
						.fullName(dto.getFullName())
						.password(encodedPassword)
						.phone(dto.getPhone())
						.build();

		userRepository.save(joinedUser);

		String accessToken = jwtUtil.createToken(joinedUser.getEmail());
		String refreshToken = jwtUtil.createRefreshToken(joinedUser.getEmail());

		return UserResponseDTO.builder()
						.userId(joinedUser.getUserId())
						.oauthId(joinedUser.getOauthId())
						.email(joinedUser.getEmail())
						.fullName(joinedUser.getEmail())
						.createdAt(joinedUser.getCreatedAt())
						.updatedAt(joinedUser.getUpdatedAt())
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.build();
	}

	public UserResponseDTO login(UserRequestDTO dto) {
		// 👉회원 존재여부 확인
		Optional<User> res = userRepository.findByEmail(dto.getEmail());

		if (res.isEmpty()) {
			throw new NoSuchMemberException("회원이 존재하지 않습니다.");
		}

		User user = res.get();

		// 👉비밀번호 일치 확인
		String plainPassword = dto.getPassword();

		Boolean 비번일치여부 = passwordEncoder.matches(plainPassword, user.getPassword());

		if (!비번일치여부) {
			throw new NotMatchPasswordException("비밀번호가 일치하지 않습니다.");
		}

		// ✅로그인처리
		String accessToken = jwtUtil.createToken(user.getEmail());
		String refreshToken = jwtUtil.createRefreshToken(user.getEmail());

		return UserResponseDTO.builder()
						.userId(user.getUserId())
						.oauthId(user.getOauthId())
						.email(user.getEmail())
						.phone(user.getPhone())
						.fullName(user.getFullName())
						.createdAt(user.getCreatedAt())
						.updatedAt(user.getUpdatedAt())
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.build();

	}

	public UserResponseDTO testJoin(@Valid UserRequestDTO dto) {
		String encodedPassword = passwordEncoder.encode(dto.getPassword());

		// ✅ 모든 검증이 끝나고 회원가입 처리 시작
		User joinedUser = User.builder()
						.email(dto.getEmail())
						.fullName(dto.getFullName())
						.password(encodedPassword)
						.build();

		userRepository.save(joinedUser);

		String accessToken = jwtUtil.createToken(joinedUser.getEmail());
		String refreshToken = jwtUtil.createRefreshToken(joinedUser.getEmail());

		return UserResponseDTO.builder()
						.userId(joinedUser.getUserId())
						.oauthId(joinedUser.getOauthId())
						.email(joinedUser.getEmail())
						.fullName(joinedUser.getEmail())
						.createdAt(joinedUser.getCreatedAt())
						.updatedAt(joinedUser.getUpdatedAt())
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.build();
	}

	public UserResponseDTO getUserInfo(String accessToken, String refreshToken) throws Exception {

//		if (accessToken == null) {
//			throw new NoAccessTokenException("aceessToken이 없습니다.");
//		}

		Boolean isValid = jwtUtil.validateToken(refreshToken);
		if (!isValid) {
			throw new NoAccessTokenException("토큰이 만료되었습니다.");
		}

		String email = jwtUtil.getSubject(refreshToken);

		if (accessToken == null && isValid) {
			// accessToken 다시 발급
			accessToken = jwtUtil.createToken(email);
		}

		User user = userRepository.findByEmail(email).orElseThrow();
		return UserResponseDTO.builder()
						.userId(user.getUserId())
						.phone(user.getPhone())
						.fullName(user.getFullName())
						.email(user.getEmail())
						.updatedAt(user.getUpdatedAt())
						.oauthId(user.getOauthId())
						.accessToken(accessToken)
						.refreshToken(refreshToken)
						.build();
	}
}

