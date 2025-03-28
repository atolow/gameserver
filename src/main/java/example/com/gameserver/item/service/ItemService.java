package example.com.gameserver.item.service;

import example.com.gameserver.item.dto.*;
import example.com.gameserver.notice.dto.NoticeResponseDto;
import example.com.gameserver.user.domain.User;

import java.util.List;

public interface ItemService {
    ItemCreateResponseDto create(ItemCreateRequestDto requestDto, User user);
    ItemUpdateResponseDto update(Long id, ItemUpdateRequestDto requestDtoUser,User user);
    ItemDetailResponseDto getById(Long id,User user);
    List<ItemListResponseDto> getAll(User user);
    void delete(Long id,User user);
    void checkAdmin(User user);


}