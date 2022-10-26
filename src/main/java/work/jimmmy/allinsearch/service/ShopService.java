package work.jimmmy.allinsearch.service;

import work.jimmmy.allinsearch.model.ShopModel;

import java.util.List;

public interface ShopService {
    ShopModel create(ShopModel shopModel);

    ShopModel get(Integer id);

    List<ShopModel> selectAll();
}
