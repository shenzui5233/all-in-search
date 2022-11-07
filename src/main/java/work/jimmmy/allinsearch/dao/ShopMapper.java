package work.jimmmy.allinsearch.dao;

import org.apache.ibatis.annotations.Param;
import work.jimmmy.allinsearch.model.ShopBo;

import java.math.BigDecimal;
import java.util.List;

public interface ShopMapper {
    List<ShopBo> selectAll();

    Integer countAllShop();

    List<ShopBo> recommend(@Param("longitude") BigDecimal longitude, @Param("latitude") BigDecimal latitude);

    List<ShopBo> search(@Param("longitude") BigDecimal longitude, @Param("latitude") BigDecimal latitude, @Param("keyword") String keyword);
}
