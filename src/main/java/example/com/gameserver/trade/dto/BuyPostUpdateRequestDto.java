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
public class BuyPostUpdateRequestDto {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @Min(value = 1, message = "가격은 1 이상이어야 합니다.")
    private int price;



    @Builder
    public BuyPostUpdateRequestDto(String title, String content, int price) {
        this.title = title;
        this.content = content;
        this.price = price;
    }
}