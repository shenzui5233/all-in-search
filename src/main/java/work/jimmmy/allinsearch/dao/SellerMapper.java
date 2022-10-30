package work.jimmmy.allinsearch.dao;

import work.jimmmy.allinsearch.model.SellerModel;

import java.util.List;

public interface SellerMapper {
    List<SellerModel> selectAll();

    Integer countAllSeller();
}
