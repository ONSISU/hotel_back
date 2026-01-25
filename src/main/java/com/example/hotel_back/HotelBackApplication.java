package com.example.hotel_back;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@MapperScan("com.example.hotel_back.*.mapper")
@SpringBootApplication
@EnableJpaAuditing
public class HotelBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelBackApplication.class, args);
	}

}
