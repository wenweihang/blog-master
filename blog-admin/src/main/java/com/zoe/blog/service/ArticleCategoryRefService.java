package com.zoe.blog.service;

import com.zoe.blog.dao.ArticleCategoryRefDao;
import com.zoe.blog.dao.CategoryDao;
import com.zoe.blog.model.ArticleCategoryRef;
import com.zoe.blog.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 20:34
 */
@Service
public class ArticleCategoryRefService {
    @Autowired
    ArticleCategoryRefDao articleCategoryRefDao;

    public Category getCategoryByArticleId(Integer articleId) {
        return articleCategoryRefDao.findCategoryByArticleId(articleId);
    }


    /**
     * 插入文章分类前 删除之前文章的分类
     * @param articleCategoryRef
     */
    @Transactional
    public int insert(ArticleCategoryRef articleCategoryRef) {

        // 添加数据前删除文章之前的所有分类
        articleCategoryRefDao.deleteByArticleId(articleCategoryRef.getArticleId());


        return articleCategoryRefDao.insert(articleCategoryRef);
    }
}
