package com.clone.finalProject.service;

import com.clone.finalProject.dto.userDto.SignupRequestDto;
import com.clone.finalProject.validator.UserValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Nested
    @DisplayName("회원가입 테스트")
    class SignupTest {

        @Test
        @DisplayName("정상")
        void signup() {
            //given
            SignupRequestDto requestDto = new SignupRequestDto(
                    "user01",
                    "닉네임1",
                    "1년차",
                    "a123456",
                    "a123456"
            );

            //when
            boolean result = UserValidator.validateUserRegister(requestDto);

            //then
            assertTrue(result);
        }

        @Test
        @DisplayName("실패_username_길이")
        void signupFail1() {
            //given
            SignupRequestDto requestDto = new SignupRequestDto(
                    "user0101011",
                    "닉네임1",
                    "1년차",
                    "a123456",
                    "a123456"
            );

            //when
            Exception e = assertThrows(IllegalArgumentException.class, () -> {
                UserValidator.validateUserRegister(requestDto);
            });

            //then
            assertEquals("아이디는 영소문자,숫자 포함하여 4~10자 이내로 입력해주세요.", e.getMessage());
        }

        @Test
        @DisplayName("실패_username_한글")
        void signupFail2() {
            //given
            SignupRequestDto requestDto = new SignupRequestDto(
                    "한글아이디",
                    "닉네임1",
                    "1년차",
                    "a123456",
                    "a123456"
            );

            //when
            Exception e = assertThrows(IllegalArgumentException.class, () -> {
                UserValidator.validateUserRegister(requestDto);
            });

            //then
            assertEquals("아이디는 영소문자,숫자 포함하여 4~10자 이내로 입력해주세요.", e.getMessage());
        }

        @Test
        @DisplayName("실패_nickname_길이")
        void signupFail3() {
            //given
            SignupRequestDto requestDto = new SignupRequestDto(
                    "user01",
                    "닉네임111111111",
                    "1년차",
                    "a123456",
                    "a123456"
            );

            //when
            Exception e = assertThrows(IllegalArgumentException.class, () -> {
                UserValidator.validateUserRegister(requestDto);
            });

            //then
            assertEquals("닉네임은 문자,숫자 포함 4-10자 이내로 입력해주세요.", e.getMessage());
        }

        @Test
        @DisplayName("실패_nickname_특수문자 포함")
        void signupFail4() {
            //given
            SignupRequestDto requestDto = new SignupRequestDto(
                    "user01",
                    "닉네임@@@",
                    "1년차",
                    "a123456",
                    "a123456"
            );

            //when
            Exception e = assertThrows(IllegalArgumentException.class, () -> {
                UserValidator.validateUserRegister(requestDto);
            });

            //then
            assertEquals("닉네임은 문자,숫자 포함 4-10자 이내로 입력해주세요.", e.getMessage());
        }

        @Test
        @DisplayName("실패_password_길이")
        void signupFail5() {
            //given
            SignupRequestDto requestDto = new SignupRequestDto(
                    "user01",
                    "닉네임01",
                    "1년차",
                    "a1234567777",
                    "a1234567777"
            );

            //when
            Exception e = assertThrows(IllegalArgumentException.class, () -> {
                UserValidator.validateUserRegister(requestDto);
            });

            //then
            assertEquals("비밀번호는 숫자,문자 포함하여 4~10자 이내로 입력해주세요.", e.getMessage());
        }
        @Test
        @DisplayName("실패_password_불일치")
        void signupFail6() {
            //given
            SignupRequestDto requestDto = new SignupRequestDto(
                    "user01",
                    "닉네임01",
                    "1년차",
                    "a12345677",
                    "a123456"
            );

            //when
            Exception e = assertThrows(IllegalArgumentException.class, () -> {
                UserValidator.validateUserRegister(requestDto);
            });

            //then
            assertEquals("비밀번호가 일치하지 않습니다.", e.getMessage());
        }
    }
}