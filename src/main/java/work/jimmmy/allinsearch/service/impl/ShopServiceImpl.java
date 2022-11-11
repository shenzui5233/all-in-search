package work.jimmmy.allinsearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.jimmmy.allinsearch.api.common.ErrorCodeEnum;
import work.jimmmy.allinsearch.api.exception.BusinessException;
import work.jimmmy.allinsearch.dao.ShopMapper;
import work.jimmmy.allinsearch.dao.ShopModelMapper;
import work.jimmmy.allinsearch.model.CategoryModel;
import work.jimmmy.allinsearch.model.SellerModel;
import work.jimmmy.allinsearch.model.ShopBo;
import work.jimmmy.allinsearch.model.ShopModel;
import work.jimmmy.allinsearch.service.CategoryService;
import work.jimmmy.allinsearch.service.SellerService;
import work.jimmmy.allinsearch.service.ShopService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopModelMapper shopModelMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ShopMapper shopMapper;

    @Transactional
    @Override
    public Object create(ShopModel shopModel) throws BusinessException {
        shopModel.setCreatedAt(new Date());
        shopModel.setUpdatedAt(new Date());

        // 检测商家是否存在正确
        SellerModel sellerModel = sellerService.get(shopModel.getSellerId());
        if (sellerModel == null) {
            throw new BusinessException(ErrorCodeEnum.PARAMETER_VALIDATION_ERROR, "商户不存在");
        }

        if (sellerModel.getDisabledFlag() == 1) {
            return new BusinessException(ErrorCodeEnum.PARAMETER_VALIDATION_ERROR, "商户已禁用");
        }

        // 检查类目
        CategoryModel categoryModel = categoryService.get(shopModel.getCategoryId());
        if (categoryModel == null) {
            throw new BusinessException(ErrorCodeEnum.PARAMETER_VALIDATION_ERROR, "类目不存在");
        }
        shopModelMapper.insertSelective(shopModel);
        return get(shopModel.getId());
    }

    @Override
    public ShopBo get(Integer id) {
        ShopModel shopModel = shopModelMapper.selectByPrimaryKey(id);
        if (shopModel == null) {
            return null;
        }
        SellerModel sellerModel = sellerService.get(shopModel.getSellerId());
        CategoryModel categoryModel = categoryService.get(shopModel.getCategoryId());
        return new ShopBo(shopModel, sellerModel, categoryModel);
    }

    @Override
    public List<ShopBo> selectAll() {
        List<ShopBo> shopList = shopMapper.selectAll();
        return shopList
                .stream()
                .peek(item -> {
                    item.setSellerModel(sellerService.get(item.getSellerId()));
                    item.setCategoryModel(categoryService.get(item.getCategoryId()));
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ShopBo> recommend(BigDecimal longitude, BigDecimal latitude) {
        List<ShopBo> shopList = shopMapper.recommend(longitude, latitude);
        shopList.forEach(shopBo -> {
            SellerModel sellerModel = sellerService.get(shopBo.getSellerId());
            CategoryModel categoryModel = categoryService.get(shopBo.getCategoryId());
            shopBo.setSellerModel(sellerModel);
            shopBo.setCategoryModel(categoryModel);
        });
        return shopList;
    }

    @Override
    public List<Map<String, Object>> searchGroupByTags(String keyword, Integer category, String tags) {
        return shopMapper.searchGroupByTags(keyword, category, tags);
    }

    @Override
    public Integer countAllShop() {
        return shopMapper.countAllShop();
    }

    @Override
    public List<ShopBo> search(BigDecimal longitude, BigDecimal latitude, String keyword, Integer orderby, Integer categoryId, String tags) {
        List<ShopBo> shopList = shopMapper.search(longitude, latitude, keyword, orderby, categoryId, tags);
        shopList.forEach(shopBo -> {
            SellerModel sellerModel = sellerService.get(shopBo.getSellerId());
            CategoryModel categoryModel = categoryService.get(shopBo.getCategoryId());
            shopBo.setSellerModel(sellerModel);
            shopBo.setCategoryModel(categoryModel);
        });
        return shopList;
    }
}
