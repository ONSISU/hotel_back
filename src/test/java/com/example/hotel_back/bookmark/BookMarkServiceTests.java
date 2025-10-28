package com.example.hotel_back.bookmark;

import com.example.hotel_back.bookmark.entity.BookMark;
import com.example.hotel_back.bookmark.repository.BookMarkRepository;
import com.example.hotel_back.hotel.entity.Hotel;
import com.example.hotel_back.hotel.repository.HotelRepository;
import com.example.hotel_back.user.entity.User;
import com.example.hotel_back.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BookMarkServiceTests {

    @Autowired
    private BookMarkRepository bookMarkRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Commit
    public void insertTest() {
        User user = userRepository.findByEmail("tom@naver.com").orElseThrow();

        Hotel hotel = hotelRepository.findByName("별이 빛나").orElseThrow();

        BookMark bookMark = BookMark.builder()
                .user(user)
                .hotel(hotel)
                .build();

        BookMark savedBookMark = bookMarkRepository.save(bookMark);

        assertThat(savedBookMark.getUser().getPassword().equals("1234"));
    }

    @Test
    @Transactional
    public void readTest() {
        // 회원이 가진 북마크 리스트
        String userEmail = "tom@naver.com";
        User foundUser = userRepository.findByEmail(userEmail)
                .orElseThrow();

        List<BookMark> foundBookMarkList1 = bookMarkRepository.findByUser(foundUser)
                .orElseThrow();

        System.out.println("foundBookMarkList1 >>> " + foundBookMarkList1);

        // 호텔이 가진 북마크 리스트
        String hotelName = "별이 빛나 호텔";
        Hotel hotel = hotelRepository.findByName(hotelName)
                .orElseThrow();

        List<BookMark> foundBookMarkList2 = bookMarkRepository.findByHotel(hotel).orElseThrow();
        System.out.println("foundBookMarkList2 >>> " + foundBookMarkList2);
    }

    @Test
    public void deleteTest() {
        bookMarkRepository.deleteById(2L);
    }


}
