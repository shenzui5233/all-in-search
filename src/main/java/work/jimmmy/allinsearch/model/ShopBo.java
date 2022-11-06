package work.jimmmy.allinsearch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ShopBo {
    private Integer id;

    private Date createdAt;

    private Date updatedAt;

    private String name;

    private BigDecimal remarkScore;

    private Integer pricePerMan;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Integer categoryId;

    private String tags;

    private String startTime;

    private String endTime;

    private String address;

    private Integer sellerId;

    private String iconUrl;

    private Integer distance;

    private CategoryModel categoryModel;

    private SellerModel sellerModel;

    public ShopBo(ShopModel shopModel, SellerModel sellerModel, CategoryModel categoryModel) {
        BeanUtils.copyProperties(shopModel, this);
        this.sellerModel = sellerModel;
        this.categoryModel = categoryModel;
    }
}
