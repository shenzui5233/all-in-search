package work.jimmmy.allinsearch.dao;

import org.apache.ibatis.annotations.Param;
import work.jimmmy.allinsearch.model.ShopBo;
import work.jimmmy.allinsearch.model.ShopModel;

import java.math.BigDecimal;
import java.util.List;

public interface ShopMapper {
    List<ShopBo> selectAll();

    Integer countAllShop();

    List<ShopBo> recommend(@Param("longitude") BigDecimal longitude, @Param("latitude") BigDecimal latitude);
}
