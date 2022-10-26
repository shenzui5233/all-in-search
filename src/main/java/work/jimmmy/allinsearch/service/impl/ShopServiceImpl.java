package work.jimmmy.allinsearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import work.jimmmy.allinsearch.dao.ShopModelMapper;
import work.jimmmy.allinsearch.model.SellerModel;
import work.jimmmy.allinsearch.model.ShopModel;
import work.jimmmy.allinsearch.service.CategoryService;
import work.jimmmy.allinsearch.service.SellerService;
import work.jimmmy.allinsearch.service.ShopService;

import java.util.Date;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopModelMapper shopModelMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SellerService sellerService;

    @Transactional
    @Override
    public ShopModel create(ShopModel shopModel) {
        shopModel.setCreatedAt(new Date());
        shopModel.setUpdatedAt(new Date());

        // 检测商家是否存在正确
        SellerModel sellerModel =
        return null;
    }

    @Override
    public ShopModel get(Integer id) {
        return null;
    }

    @Override
    public List<ShopModel> selectAll() {
        return null;
    }
}
