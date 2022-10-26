package work.jimmmy.allinsearch.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class SellerCreateReq {
    @NotBlank(message = "商户名不能为空")
    private String name;
}
