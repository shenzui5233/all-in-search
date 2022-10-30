package work.jimmmy.allinsearch.service;

import work.jimmmy.allinsearch.api.exception.BusinessException;
import work.jimmmy.allinsearch.model.CategoryModel;

import java.util.List;

public interface CategoryService {
    /**
     * 创建品类
     *
     * @param categoryModel category
     * @return CategoryModel
     */
    CategoryModel create(CategoryModel categoryModel) throws BusinessException;

    CategoryModel get(Integer id);

    List<CategoryModel> selectAll();

    Integer countAllCategory();
}
