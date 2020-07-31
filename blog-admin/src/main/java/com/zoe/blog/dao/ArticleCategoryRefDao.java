package com.zoe.blog.dao;

import com.zoe.blog.model.ArticleCategoryRef;
import com.zoe.blog.model.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 20:04
 */
@Repository
public interface ArticleCategoryRefDao {

    Category findCategoryByArticleId(Integer articleId);

    int insert(ArticleCategoryRef articleCategoryRef);

    void deleteByArticleId(Integer articleId);

    void deleteByCategoryId(Integer categoryId);
}
