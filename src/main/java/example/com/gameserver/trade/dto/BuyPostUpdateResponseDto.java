package example.com.gameserver.trade.dto;

import example.com.gameserver.trade.domain.BuyPost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BuyPostUpdateResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final int price;
    private final String username;
    private final boolean isCompleted;

    @Builder
    public BuyPostUpdateResponseDto(Long id, String title, String content, int price,
                                    String username, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.price = price;
        this.username = username;
        this.isCompleted = isCompleted;
    }

    public static BuyPostUpdateResponseDto from(BuyPost post) {
        return BuyPostUpdateResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .price(post.getPrice())
                .username(post.getUser().getUsername())
                .isCompleted(post.isCompleted())
                .build();
    }
}