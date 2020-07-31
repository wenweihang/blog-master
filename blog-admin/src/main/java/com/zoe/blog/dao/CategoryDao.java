package com.zoe.blog.dao;

import com.zoe.blog.model.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 19:42
 */
@Repository
public interface CategoryDao {

    List<Category> getList(String name);

    List<Category> search(String name);

    Category findById(Integer id);

    Category findByCategoryName(String categoryName);

    int insert(Category category);

    int update(Category category);

    int delete(Integer id);
}
