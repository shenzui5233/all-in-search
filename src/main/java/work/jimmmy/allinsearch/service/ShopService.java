package work.jimmmy.allinsearch.service;

import work.jimmmy.allinsearch.api.exception.BusinessException;
import work.jimmmy.allinsearch.model.ShopBo;
import work.jimmmy.allinsearch.model.ShopModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ShopService {
    Object create(ShopModel shopModel) throws BusinessException;

    ShopBo get(Integer id);

    List<ShopBo> selectAll();

    List<ShopBo> recommend(BigDecimal longitude, BigDecimal latitude);

    List<Map<String, Object>> searchGroupByTags(String keyword, Integer category, String tags);

    Integer countAllShop();

    List<ShopBo> search(BigDecimal longitude, BigDecimal latitude, String keyword,
                        Integer orderby, Integer categoryId, String tags);

    Map<String, Object> searchEs(BigDecimal longitude, BigDecimal latitude, String keyword,
                        Integer orderby, Integer categoryId, String tags) throws IOException;
}
