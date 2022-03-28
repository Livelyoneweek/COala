package com.clone.finalProject.service;

import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.userDto.SignupRequestDto;
import com.clone.finalProject.dto.userDto.UserInfoRequestDto;
import com.clone.finalProject.dto.userDto.UserInfoResponseDto;
import com.clone.finalProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(SignupRequestDto requestDto) {

        //중복된 이메일(로그인 id)가 존재할 경우
        String username = requestDto.getUsername();
        String nickName = requestDto.getNickname();
        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }


        //중복된 닉네임이 존재할 경우
//        if (userRepository.existsByNickname(nickname)) {
//            throw new IllegalArgumentException("중복된 닉네임입니다.");
//        }

        //중복된 이메일이 존재할 경우
        if (userRepository.existsByNickname(nickName)) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        //UserValidator.validateUserRegister(username,password,passwordCheck);

        // 패스워드
        String enPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto, enPassword);
        userRepository.save(user); // DB 저장
        log.info("회원가입 완료");

    }

    // username 중복체크
    public boolean usernameCheck(String username) {
        return userRepository.existsByUsername(username);
    }

    // nickName 중복체크
    public boolean nickName(String nickname) {
        return userRepository.existsByNickname(nickname);
    }


    //회원 정보 수정
    @Transactional
    public void updateUserInfo(Long uid, UserInfoRequestDto userInfoRequestDto) {

        User user = userRepository.findByUid(uid).orElseThrow(
                () -> new NullPointerException("유저가 존재하지 않습니다.")
        );
        user.userInfoUpdate(userInfoRequestDto);
    }

    //회원 비밀번호 수정
    @Transactional
    public UserInfoResponseDto updatePassword(Long uid, UserInfoRequestDto userInfoRequestDto) {

        User user = userRepository.findByUid(uid).orElseThrow(
                () -> new NullPointerException("유저가 존재하지 않습니다.")
        );
        // 패스워드 암호화 함
        log.info(userInfoRequestDto.getPassword());

        UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto();
        //기존 패스워드랑 비교해서 맞으면 뉴패스워드로 업데이트
        if(passwordEncoder.matches(userInfoRequestDto.getPassword(), user.getPassword())){
            log.info("비밀번호 변경 중");
            String newPassword = passwordEncoder.encode(userInfoRequestDto.getNewPassword());
            user.userPasswordUpdate(newPassword);
            userInfoResponseDto = new UserInfoResponseDto(true);
        } else {
            log.info("변경실패");
            userInfoResponseDto = new UserInfoResponseDto(false);

        }
        return userInfoResponseDto;

    }

    //weekPoint 초기화 매주 수요일 새벽1시 실행
    // 초 분 시 일 월 요일 년도 (생략가능)
    // 1 월 7 토
    @Transactional
    @Scheduled(cron="0 00 11 * * 1")
    public void weekPointReset() {
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            user.weekPointReset();
        }
        log.info("weekPoint 초기화 완료 현재시간 {}",System.currentTimeMillis());
    }

    //monthPoint 초기화 매달 1일 새벽1시 실행
    @Transactional
    @Scheduled(cron = "0 0 01 1 * *")
    public void monthPointReset() {
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            user.monthPointReset();
        }
        log.info("monthPoint 초기화 완료 현재시간 {}",System.currentTimeMillis());
    }
}
