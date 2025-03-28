package example.com.gameserver.user.service;

import example.com.gameserver.user.domain.User;
import example.com.gameserver.user.domain.UserRole;
import example.com.gameserver.user.dto.*;
import example.com.gameserver.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static example.com.gameserver.utils.EntityValidator.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    @Override
    public UserCreateResponseDto createUser(UserCreateRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        validateEmail(requestDto.getEmail());
        validatePassword(requestDto.getPassword());
        validateUsername(requestDto.getUsername());

        User user = User.builder()
                .username(requestDto.getUsername())
                .realName(requestDto.getRealName())
                .email(requestDto.getEmail())
                .phoneNumber(requestDto.getPhoneNumber())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .cash(0)                    // 회원가입 시 잔액은 0원
                .role(UserRole.NEWBIE)        // 기본 역할은 NEWBIE
                .isActive(true)
                .build();

        return UserCreateResponseDto.from(userRepository.save(user));
    }
    @Transactional
    @Override
    public UserPasswordChangeResponseDto updatePassword(Long userId, UserPasswordChangeRequestDto requestDto) {
        User user = userRepository.findByIdOrElseThrow(userId);

        // 기존 비밀번호 일치 확인
        if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        // 새 비밀번호 일치 확인
        if (!requestDto.getNewPassword().equals(requestDto.getConfirmPassword())) {
            throw new IllegalArgumentException("새 비밀번호 확인이 일치하지 않습니다.");
        }

        // 변경
        String encodedPassword = passwordEncoder.encode(requestDto.getNewPassword());
        user.changePassword(encodedPassword);

        return UserPasswordChangeResponseDto.from(user);
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId);
        return UserResponseDto.from(user);
    }
    @Transactional
    @Override
    public void deactivateUser(Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId);
        if (!user.isActive()) {
            throw new IllegalStateException("이미 탈퇴한 유저입니다.");
        }

        user.deactivate(); // isActive = false
    }


    // 사용자 이름으로 balance 값을 가져오는 메서드
    public Double getBalanceForUser(String username) {
        Double balance = userRepository.findBalanceByUsername(username);
        return balance != null ? balance : 0.0;  // balance가 null이면 0.0을 반환
    }


    public List<Object[]> getBalanceSortedByBalance() {
        return userRepository.findBalanceFromCMIUsers();
    }

}