package example.com.gameserver.user.service;

import example.com.gameserver.user.dto.*;

public interface UserService {

    /**
     * 사용자 등록 (회원가입)
     * @param requestDto 사용자 생성 요청 DTO
     * @return 생성된 사용자 응답 DTO
     */
    UserCreateResponseDto createUser(UserCreateRequestDto requestDto);

    /**
     * 비밀번호 변경
     * @param userId 비밀번호를 변경할 사용자 ID
     * @param requestDto 비밀번호 변경 요청 DTO
     * @return 변경 결과 응답 DTO
     */
    UserPasswordChangeResponseDto updatePassword(Long userId, UserPasswordChangeRequestDto requestDto);

    /**
     * 사용자 조회
     * @param userId 조회할 사용자 ID
     * @return 사용자 응답 DTO
     */
    UserResponseDto getUserById(Long userId);

    void deactivateUser(Long userId);

    Double getBalanceForUser(String username);




}