package example.com.gameserver.command.service;

import example.com.gameserver.command.domain.Command;
import example.com.gameserver.command.dto.CommandCreateRequestDto;
import example.com.gameserver.command.dto.CommandResponseDto;
import example.com.gameserver.command.dto.CommandUpdateRequestDto;
import example.com.gameserver.command.repository.CommandRepository;
import example.com.gameserver.user.domain.User;
import example.com.gameserver.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static example.com.gameserver.utils.EntityValidator.validateIsAdmin;

@Service
@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService {

    private final CommandRepository commandRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public CommandResponseDto create(CommandCreateRequestDto requestDto, User user) {
        validateIsAdmin(user);
        User FindUser = userRepository.findByIdOrElseThrow(user.getId());

        Command notice = Command.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .user(FindUser)
                .build();
        return CommandResponseDto.from(commandRepository.save(notice));
    }

    @Override
    public CommandResponseDto getById(Long id) {
        Command notice = commandRepository.findByIdOrElseThrow(id);
        return CommandResponseDto.from(notice);
    }

    @Override
    public CommandResponseDto getByIdEdit(Long id, User user) {
        validateIsAdmin(user);
        Command notice = commandRepository.findByIdOrElseThrow(id);
        if (!notice.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("수정 권한이 없습니다.");
        }
        return CommandResponseDto.from(notice);
    }

    @Override
    public Page<CommandResponseDto> getAll(Pageable pageable) {
        return commandRepository.findAllByOrderByIdDesc(pageable)
                .map(CommandResponseDto::from);
    }

    @Transactional
    @Override
    public CommandResponseDto update(Long id, CommandUpdateRequestDto requestDto, User user) {
        validateIsAdmin(user);
        Command notice = commandRepository.findByIdOrElseThrow(id);
        if (!notice.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("수정 권한이 없습니다.");
        }
        notice.update(requestDto.getTitle(), requestDto.getContent());
        return CommandResponseDto.from(notice);
    }

    @Transactional
    @Override
    public void delete(Long id, User user) {
        validateIsAdmin(user);
        Command notice = commandRepository.findByIdOrElseThrow(id);
        if (!notice.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("삭제 권한이 없습니다.");
        }
        commandRepository.delete(notice);
    }
    @Override
    public void checkAdmin(User user) {
        validateIsAdmin(user);
    }
}