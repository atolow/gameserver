package example.com.gameserver.trade.dto;

import example.com.gameserver.trade.domain.BuyPost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BuyPostCreateResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final int price;
    private final String username;
    private final boolean isCompleted;

    @Builder
    public BuyPostCreateResponseDto(Long id, String title, String content, int price,
                                    String username, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.price = price;
        this.username = username;
        this.isCompleted = isCompleted;
    }

    public static BuyPostCreateResponseDto toDto(BuyPost buyPost) {
        return BuyPostCreateResponseDto.builder()
                .id(buyPost.getId())
                .title(buyPost.getTitle())
                .content(buyPost.getContent())
                .price(buyPost.getPrice())
                .username(buyPost.getUser().getUsername())
                .isCompleted(buyPost.isCompleted())
                .build();
    }
}