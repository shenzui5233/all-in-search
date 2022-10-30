package work.jimmmy.allinsearch.dao;

import work.jimmmy.allinsearch.model.ShopModel;

import java.util.List;

public interface ShopMapper {
    List<ShopModel> selectAll();

    Integer countAllShop();
}
