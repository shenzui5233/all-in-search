package work.jimmmy.allinsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import work.jimmmy.allinsearch.api.common.CommonRes;
import work.jimmmy.allinsearch.api.common.ErrorCodeEnum;
import work.jimmmy.allinsearch.api.exception.BusinessException;
import work.jimmmy.allinsearch.model.ShopBo;
import work.jimmmy.allinsearch.service.CategoryService;
import work.jimmmy.allinsearch.service.SellerService;
import work.jimmmy.allinsearch.service.ShopService;

import java.math.BigDecimal;
import java.util.List;

@Controller("/shop")
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/recommend")
    @ResponseBody
    public CommonRes recommend(@RequestParam(name = "longitude") BigDecimal longitude, @RequestParam(name = "latitude") BigDecimal latitude) throws BusinessException {
        if (longitude == null || latitude == null) {
            throw new BusinessException(ErrorCodeEnum.PARAMETER_VALIDATION_ERROR);
        }
        List<ShopBo> shopBoList = shopService.recommend(longitude, latitude);
        return CommonRes.create(shopBoList);
    }
}
