package work.jimmmy.allinsearch.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
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

    @Autowired
    private RestHighLevelClient restHighLevelClient;

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

    @Override
    public Map<String, Object> searchEs(BigDecimal longitude, BigDecimal latitude, String keyword, Integer orderby, Integer categoryId, String tags) throws IOException {
        Map<String, Object> result = new HashMap<>();
//        SearchRequest searchRequest = new SearchRequest("shop");
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(QueryBuilders.matchQuery("name", keyword));
//        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        searchRequest.source(sourceBuilder);
//        List<Integer> shopIdList = new ArrayList<>();
//        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//        SearchHit[] hits =searchResponse.getHits().getHits();
//        for (SearchHit hit : hits) {
//            shopIdList.add(new Integer(hit.getSourceAsMap().get("id").toString()));
//        }
        Request request = new Request("GET", "/shop/_search");
        // 构建请求
        JSONObject jsonRequestObj = new JSONObject();
        // 构建source部分
        jsonRequestObj.put("_source", "*");
        // 构建自定义距离字段
        jsonRequestObj.put("script_fields", new JSONObject());
        jsonRequestObj.getJSONObject("script_fields").put("distance", new JSONObject());
        jsonRequestObj.getJSONObject("script_fields").getJSONObject("distance").put("script", new JSONObject());
        jsonRequestObj.getJSONObject("script_fields").getJSONObject("distance").getJSONObject("script")
                .put("source", "haversin(lat,lon,doc['location'].lat,doc['location'].lon)");
        jsonRequestObj.getJSONObject("script_fields").getJSONObject("distance").getJSONObject("script")
                .put("lang", "expression");
        jsonRequestObj.getJSONObject("script_fields").getJSONObject("distance").getJSONObject("script")
                .put("params", new JSONObject());
        jsonRequestObj.getJSONObject("script_fields").getJSONObject("distance").getJSONObject("script")
                .getJSONObject("params").put("lat", latitude);
        jsonRequestObj.getJSONObject("script_fields").getJSONObject("distance").getJSONObject("script")
                .getJSONObject("params").put("lon", longitude);

        // 构建query
        jsonRequestObj.put("query", new JSONObject());

        // 构建function score
        jsonRequestObj.getJSONObject("query").put("function_score", new JSONObject());

        // 构建function score内的query
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").put("query", new JSONObject());
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query")
                .put("bool", new JSONObject());
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query")
                .getJSONObject("bool").put("must", new JSONArray());

        // 构建第一个条件
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query")
                        .getJSONObject("bool").getJSONArray("must").add(new JSONObject());
        int queryIndex = 0;
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query")
                .getJSONObject("bool").getJSONArray("must").getJSONObject(queryIndex).put("match", new JSONObject());
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query")
                .getJSONObject("bool").getJSONArray("must").getJSONObject(queryIndex)
                .getJSONObject("match").put("name", new JSONObject());
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query")
                .getJSONObject("bool").getJSONArray("must").getJSONObject(queryIndex)
                .getJSONObject("match").getJSONObject("name").put("query", keyword);
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query")
                .getJSONObject("bool").getJSONArray("must").getJSONObject(queryIndex);

        // 构建第二个条件
        queryIndex++;
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query")
                .getJSONObject("bool").getJSONArray("must").add(new JSONObject());
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query")
                .getJSONObject("bool").getJSONArray("must").getJSONObject(queryIndex).put("term", new JSONObject());
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query")
                .getJSONObject("bool").getJSONArray("must").getJSONObject(queryIndex)
                .getJSONObject("term").put("seller_disabled_flag", 0);

        if (categoryId != null) {
            // 构建第三个条件
            queryIndex++;
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query")
                    .getJSONObject("bool").getJSONArray("must").add(new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query")
                    .getJSONObject("bool").getJSONArray("must").getJSONObject(queryIndex).put("term", new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONObject("query")
                    .getJSONObject("bool").getJSONArray("must").getJSONObject(queryIndex)
                    .getJSONObject("term").put("category_id", categoryId);
        }

        // 构建functions部分
        jsonRequestObj.getJSONObject("query").getJSONObject("function_score").put("functions", new JSONArray());
        int functionIndex = 0;
        if (orderby == null) { // 默认排序
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .add(new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .getJSONObject(functionIndex).put("gauss", new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .getJSONObject(functionIndex).getJSONObject("gauss").put("location", new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .getJSONObject(functionIndex).getJSONObject("gauss")
                    .getJSONObject("location").put("origin", latitude.toString() + "," + longitude.toString());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .getJSONObject(functionIndex).getJSONObject("gauss")
                    .getJSONObject("location").put("scale", "100km");
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .getJSONObject(functionIndex).getJSONObject("gauss")
                    .getJSONObject("location").put("offset", "0km");
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .getJSONObject(functionIndex).getJSONObject("gauss")
                    .getJSONObject("location").put("decay", "0.5");
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .getJSONObject(functionIndex).put("weight", 9);

            functionIndex++;
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .add(new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .getJSONObject(functionIndex).put("field_value_factor", new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .getJSONObject(functionIndex).getJSONObject("field_value_factor")
                    .put("field", "remark_score");
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .getJSONObject(functionIndex).put("weight", 0.2);

            functionIndex++;
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .add(new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .getJSONObject(functionIndex).put("field_value_factor", new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .getJSONObject(functionIndex).getJSONObject("field_value_factor")
                    .put("field", "seller_remark_score");
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .getJSONObject(functionIndex).put("weight", 0.1);

            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").put("score_mode","sum");
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").put("boost_mode","sum");
        } else {
            // 低价排序
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .add(new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .getJSONObject(0).put("field_value_factor", new JSONObject());
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .getJSONObject(0).getJSONObject("field_value_factor").put("field", "price_per_man");

            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").getJSONArray("functions")
                    .getJSONObject(0).put("weight", 1);
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").put("score_mode","sum");
            jsonRequestObj.getJSONObject("query").getJSONObject("function_score").put("boost_mode","replace");
        }

        // 排序字段
        jsonRequestObj.put("sort", new JSONArray());
        jsonRequestObj.getJSONArray("sort").add(new JSONObject());
        jsonRequestObj.getJSONArray("sort").getJSONObject(0).put("_score", new JSONObject());
        if (orderby == null) {
            jsonRequestObj.getJSONArray("sort").getJSONObject(0).getJSONObject("_score").put("order", "desc");
        } else {
           jsonRequestObj.getJSONArray("sort").getJSONObject(0).getJSONObject("_score").put("order", "asc");
        }

        String reqJson = jsonRequestObj.toJSONString();
        request.setJsonEntity(reqJson);
        Response response = restHighLevelClient.getLowLevelClient().performRequest(request);
        String responseStr = EntityUtils.toString(response.getEntity());
        System.out.println(responseStr);
        JSONObject jsonObject = JSONObject.parseObject(responseStr);
        JSONArray jsonArray = jsonObject.getJSONObject("hits").getJSONArray("hits");
        List<ShopBo> shopList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            Integer id = new Integer(jsonObj.get("_id").toString());
            BigDecimal distance = new BigDecimal(jsonObj.getJSONObject("fields").getJSONArray("distance").get(0).toString());
            ShopBo shopBo = get(id);
            shopBo.setDistance(distance.multiply(new BigDecimal(1000)
                    .setScale(0, RoundingMode.CEILING)).intValue());
            shopList.add(shopBo);
        }
        //List<ShopBo> shopList = shopIdList.stream().map(this::get).collect(Collectors.toList());
        result.put("shop", shopList);
        return result;
    }
}
