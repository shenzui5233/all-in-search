package work.jimmmy.allinsearch.controller;

import org.apache.commons.lang3.StringUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/shop")
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private ShopService shopService;

    /**
     * 推荐服务v1.0
     *
     * @param longitude 经度
     * @param latitude 纬度
     * @return CommonRes
     * @throws BusinessException businessException
     */
    @RequestMapping("/recommend")
    @ResponseBody
    public CommonRes recommend(@RequestParam(name = "longitude") BigDecimal longitude, @RequestParam(name = "latitude") BigDecimal latitude) throws BusinessException {
        if (longitude == null || latitude == null) {
            throw new BusinessException(ErrorCodeEnum.PARAMETER_VALIDATION_ERROR);
        }
        List<ShopBo> shopBoList = shopService.recommend(longitude, latitude);
        return CommonRes.create(shopBoList);
    }

    @RequestMapping("/search")
    @ResponseBody
    public CommonRes search(@RequestParam(name = "longitude") BigDecimal longitude, @RequestParam(name = "latitude") BigDecimal latitude, @RequestParam(name = "keyword") String keyword) throws BusinessException {
        if (StringUtils.isEmpty(keyword) || longitude == null || latitude == null) {
            throw new BusinessException(ErrorCodeEnum.PARAMETER_VALIDATION_ERROR);
        }
        List<ShopBo> shopBoList = shopService.search(longitude, latitude, keyword);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("shop", shopBoList);
        return CommonRes.create(resMap);
    }
}
