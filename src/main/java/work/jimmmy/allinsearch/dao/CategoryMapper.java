package work.jimmmy.allinsearch.dao;

import work.jimmmy.allinsearch.model.CategoryModel;

import java.util.List;

public interface CategoryMapper {
    List<CategoryModel> selectAll();

    Integer countAllCategory();
}
