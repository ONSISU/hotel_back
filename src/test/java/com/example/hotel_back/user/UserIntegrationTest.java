package com.example.hotel_back.user;


import com.example.hotel_back.common.util.EmailUtil;
import com.example.hotel_back.common.util.RandomNumberUtil;
import com.example.hotel_back.user.dto.UserRequestDTO;
import com.example.hotel_back.user.dto.UserResponseDTO;
import com.example.hotel_back.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
public class UserIntegrationTest {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailUtil emailUtil;

	@Test
	@DisplayName("이메일 전송테스트")
	public void sendCodeToEmailTest() {

		// given) 보낼 이메일
		String email = "gnsdl9079@gmail.com";

		// when) 이메일에 인증코드 보냄 & 레디스 저장
		int 인증번호 = RandomNumberUtil.get랜덤인증번호();
		emailUtil.sendVerificationEmail(email);

		// then) 이메일 전송완료
		// 이메일 확인

	}

	@Test
	@DisplayName("인증번호 인증 테스트")
	public void verifyEmailTest() {
		// given) 이메일로 받은 인증번호
		String email = "gnsdl9079@gmail.com";
		String 인증번호 = "5644";

		// when) 인증번호가 레디스에 일치하는지 확인
		Boolean 인증여부 = emailUtil.verifyCode(email, 인증번호);

		// then) 인증확인
		assertThat(인증여부.equals(true));

	}

	@Test
	@DisplayName("회원가입")
	public void joinTest() {
		// given) 회원파라미터
		UserRequestDTO requestDTO = UserRequestDTO.builder()
						.email("tom3@naver.com")
						.fullName("tomhoon")
						.password("1234")
						.build();

		// when) 가입시
		UserResponseDTO joinedDTO = userService.join(requestDTO);

		// then) 가입완료
		log.info("joinedDTO ::: {}", joinedDTO);
	}

	@Test
	@DisplayName("로그인")
	public void loginTest() {
		//given)회원정보
		UserRequestDTO dto = UserRequestDTO.builder()
						.email("tom2@naver.com")
						.fullName("tomhoon")
						.password("1234")
						.build();


		//when)로그인처리
		UserResponseDTO response = userService.login(dto);

		//then)로그인완료 후 토큰 발급
		log.info("response ::: {}", response.toString());
	}


}
