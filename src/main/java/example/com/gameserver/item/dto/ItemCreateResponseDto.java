package example.com.gameserver.item.dto;

import example.com.gameserver.item.domain.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemCreateResponseDto {

    private final Long id;
    private final String name;
    private final String description;
    private final int price;
    private final boolean isSold;
    private final String ownerUsername;

    @Builder
    public ItemCreateResponseDto(Long id, String name, String description, int price, boolean isSold, String ownerUsername) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isSold = isSold;
        this.ownerUsername = ownerUsername;
    }

    public static ItemCreateResponseDto from(Item item) {
        return ItemCreateResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice())
                .isSold(item.isSold())
                .ownerUsername(item.getUser().getUsername())
                .build();
    }
}