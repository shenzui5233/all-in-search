package work.jimmmy.allinsearch.service;

import work.jimmmy.allinsearch.api.exception.BusinessException;
import work.jimmmy.allinsearch.model.ShopBo;
import work.jimmmy.allinsearch.model.ShopModel;

import java.util.List;

public interface ShopService {
    Object create(ShopModel shopModel) throws BusinessException;

    ShopBo get(Integer id);

    List<ShopBo> selectAll();

    Integer countAllShop();
}
