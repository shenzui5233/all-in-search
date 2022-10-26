package work.jimmmy.allinsearch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import work.jimmmy.allinsearch.api.common.ErrorCodeEnum;
import work.jimmmy.allinsearch.api.exception.BusinessException;
import work.jimmmy.allinsearch.dao.CategoryMapper;
import work.jimmmy.allinsearch.dao.CategoryModelMapper;
import work.jimmmy.allinsearch.model.CategoryModel;
import work.jimmmy.allinsearch.service.CategoryService;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryModelMapper categoryModelMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional
    @Override
    public CategoryModel create(CategoryModel categoryModel) throws BusinessException {
        categoryModel.setCreatedAt(new Date());
        categoryModel.setUpdatedAt(new Date());
        try {
            categoryModelMapper.insertSelective(categoryModel);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(ErrorCodeEnum.NO_HANDLER_FOUND);
        }
        return categoryModel;
    }

    @Override
    public CategoryModel get(Integer id) {
        return categoryModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CategoryModel> selectAll() {
        return categoryMapper.selectAll();
    }
}
