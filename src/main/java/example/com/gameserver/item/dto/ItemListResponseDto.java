package example.com.gameserver.item.dto;

import example.com.gameserver.item.domain.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemListResponseDto {

    private final Long id;
    private final String name;
    private final int price;
    private final boolean isSold;

    @Builder
    public ItemListResponseDto(Long id, String name, int price, boolean isSold) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isSold = isSold;
    }

    public static ItemListResponseDto from(Item item) {
        return ItemListResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .isSold(item.isSold())
                .build();
    }
}