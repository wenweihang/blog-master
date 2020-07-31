package com.zoe.blog.service;

import com.github.pagehelper.PageHelper;
import com.zoe.blog.dao.ArticleCategoryRefDao;
import com.zoe.blog.dao.CategoryDao;
import com.zoe.blog.model.ArticleCategoryRef;
import com.zoe.blog.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 19:50
 */
@Service
public class CategoryService {
    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ArticleCategoryRefDao articleCategoryRefDao;

    public List<Category> list(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return categoryDao.getList(name);
    }

    public List<Category> search(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return categoryDao.search(name);
    }

    public Category findById(Integer id) {
        return categoryDao.findById(id);
    }

    public int insert(Category category) {
        return categoryDao.insert(category);
    }

    public int update(Category category) {
        return categoryDao.update(category);
    }

    @Transactional
    public int delete(Integer id) {
        // 删除分类前删除文章分类关联表里的文章分类
        articleCategoryRefDao.deleteByCategoryId(id);

        return categoryDao.delete(id);
    }
}
