package example.com.gameserver.trade.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BuyPostCreateRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Min(1)
    private int price;



    @Builder
    public BuyPostCreateRequestDto(String title, String content, int price) {
        this.title = title;
        this.content = content;
        this.price = price;
    }
}