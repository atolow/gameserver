package example.com.gameserver.item.service;

import example.com.gameserver.item.domain.Item;
import example.com.gameserver.item.dto.*;
import example.com.gameserver.item.repository.ItemRepository;
import example.com.gameserver.user.domain.User;
import example.com.gameserver.user.domain.UserRole;
import example.com.gameserver.user.repository.UserRepository;
import example.com.gameserver.utils.EntityValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static example.com.gameserver.utils.EntityValidator.validateIsAdmin;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    @Transactional
    @Override
    public ItemCreateResponseDto create(ItemCreateRequestDto requestDto, User user) {
        validateIsAdmin(user);
        Item item = Item.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .price(requestDto.getPrice())
                .user(user)
                .build();

        return ItemCreateResponseDto.from(itemRepository.save(item));
    }
    @Transactional
    @Override
    public ItemUpdateResponseDto update(Long id, ItemUpdateRequestDto requestDto,User user) {
        validateIsAdmin(user);

        Item item = itemRepository.findByIdOrElseThrow(id);

        item.update(requestDto.getName(), requestDto.getDescription(), requestDto.getPrice());

        return ItemUpdateResponseDto.from(item);
    }

    @Override
    public ItemDetailResponseDto getById(Long id,User user) {
        validateIsAdmin(user);
        Item item = itemRepository.findByIdOrElseThrow(id);
        return ItemDetailResponseDto.from(item);
    }

    @Override
    public List<ItemListResponseDto> getAll(User user) {
        validateIsAdmin(user);
        return itemRepository.findAll().stream()
                .map(ItemListResponseDto::from)
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public void delete(Long id,User user) {
        validateIsAdmin(user);
        Item item = itemRepository.findByIdOrElseThrow(id);
        itemRepository.delete(item);
    }
    @Override
    public void checkAdmin(User user) {
        EntityValidator.validateIsAdmin(user);
    }
}