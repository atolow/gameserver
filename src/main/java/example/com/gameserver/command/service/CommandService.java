package example.com.gameserver.command.service;

import example.com.gameserver.command.dto.CommandCreateRequestDto;
import example.com.gameserver.command.dto.CommandResponseDto;
import example.com.gameserver.command.dto.CommandUpdateRequestDto;
import example.com.gameserver.item.dto.ItemDetailResponseDto;
import example.com.gameserver.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommandService {

    CommandResponseDto create(CommandCreateRequestDto requestDto, User user);

    CommandResponseDto getById(Long id);

    Page<CommandResponseDto> getAll(Pageable pageable);

    CommandResponseDto update(Long id, CommandUpdateRequestDto requestDto, User user);

    void delete(Long id, User user);

    void checkAdmin(User user);
    CommandResponseDto getByIdEdit(Long id, User user);
}