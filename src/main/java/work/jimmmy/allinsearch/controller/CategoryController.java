package work.jimmmy.allinsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import work.jimmmy.allinsearch.api.common.CommonRes;
import work.jimmmy.allinsearch.model.CategoryModel;
import work.jimmmy.allinsearch.service.CategoryService;

import java.util.List;

@Controller("/category")
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ResponseBody
    @RequestMapping("/list")
    public CommonRes list() {
        List<CategoryModel> categoryModelList = categoryService.selectAll();
        return CommonRes.create(categoryModelList);
    }
}
