package work.jimmmy.allinsearch.service;

import work.jimmmy.allinsearch.api.exception.BusinessException;
import work.jimmmy.allinsearch.model.SellerModel;

import java.util.List;

public interface SellerService {
    /**
     * 创建商家
     *
     * @param sellerModel sellerModel
     * @return SellerModel
     */
    SellerModel create(SellerModel sellerModel);

    /**
     * 根据id查询商家
     *
     * @param id id
     * @return SellerModel
     */
    SellerModel get(Integer id);

    List<SellerModel> selectAll();

    SellerModel changeStatus(Integer id, Integer disableFlag) throws BusinessException;

    Integer countAllSeller();
}
