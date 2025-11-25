package com.example.hotel_back.user;

import com.example.hotel_back.user.entity.ProfilePhoto;
import com.example.hotel_back.user.entity.User;
import com.example.hotel_back.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	int 테스트식별자 = LocalTime.now().getSecond();

	@Test
	public void insertUserTest() {

		ProfilePhoto photo = ProfilePhoto.builder()
						.photoName("Hello.png")
						.photoUrl("/uploads/")
						.build();

		User user = User.builder()
						.email("kevin%s@naver.com".formatted(테스트식별자))
						.phone("010907922%s".formatted(테스트식별자))
						.fullName("LEEDONGHOON")
						.password("1234")
						.profilePhoto(photo)
						.build();

		User result = userRepository.save(user);

		// ✅ AssertJ style
		assertThat(result.getUserId()).isNotNull();  // DB inserted ID
		//assertThat(result.getEmail()).isEqualTo("tom@naver.com");
		assertThat(result.getProfilePhoto().getPhotoName()).isEqualTo("Hello.png");
	}

	@Test
	@Transactional
	public void readPhotoNameTest() {
		User user = userRepository.findById(1L).orElseThrow();

		String photoName = "Hello.png";
		ProfilePhoto p = user.getProfilePhoto();

		assertThat(p.getPhotoName().equals(photoName));
	}

	@Test
	public void joinUserTest() {
		Map<String, Object> map = new HashMap<>();
//        map.put("email", "동훈%s@naver.com".formatted(테스트식별자));
		map.put("email", "bh@naver.com");
		map.put("password", passwordEncoder.encode("1234"));
		map.put("fullName", "남병현");
//		map.put("fullName", "남병현%s".formatted(테스트식별자));
		map.put("phone", "010555555%s".formatted(테스트식별자));

		ObjectMapper mapper = new ObjectMapper();
		User user = mapper.convertValue(map, User.class);

		User savedUser = userRepository.save(user);

		assertThat(map.get("password").equals(savedUser.getPassword()));
	}

	@Test
	public void loginTest() {
		Map<String, Object> map = new HashMap<>();
		map.put("email", "tom@naver.com");
		map.put("password", "1234");

		ObjectMapper mapper = new ObjectMapper();

		User foundUser = userRepository
						.findByEmail((String) map.get("email"))
						.orElseThrow();


		assertThat(foundUser.getPassword().equals((String) map.get("password")));
	}

	@Test
	public void deleteTest() {
		String userEmail = "tom@naver.com";
		userRepository.deleteById(3L);
	}
}

