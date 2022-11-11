package work.jimmmy.allinsearch.dao;

import org.apache.ibatis.annotations.Param;
import work.jimmmy.allinsearch.model.ShopBo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ShopMapper {
    List<ShopBo> selectAll();

    Integer countAllShop();

    List<ShopBo> recommend(@Param("longitude") BigDecimal longitude, @Param("latitude") BigDecimal latitude);

    List<ShopBo> search(@Param("longitude") BigDecimal longitude,
                        @Param("latitude") BigDecimal latitude,
                        @Param("keyword") String keyword,
                        @Param("orderby") Integer orderby,
                        @Param("categoryId") Integer categoryId,
                        @Param("tags") String tags);

    List<Map<String, Object>> searchGroupByTags(@Param("keyword") String keyword,
                                                @Param("categoryId") Integer categoryId,
                                                @Param("tags") String tags);
}
