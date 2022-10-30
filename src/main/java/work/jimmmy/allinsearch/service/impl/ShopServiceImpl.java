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

import java.util.Date;
import java.util.List;
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
        List<ShopModel> shopModelList = shopMapper.selectAll();
        return shopModelList
                .stream()
                .map(item -> new ShopBo(item, sellerService.get(item.getSellerId()), categoryService.get(item.getCategoryId())))
                .collect(Collectors.toList());
    }

    @Override
    public Integer countAllShop() {
        return shopMapper.countAllShop();
    }
}
