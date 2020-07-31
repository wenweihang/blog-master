package com.zoe.blog.dao;

import com.zoe.blog.dto.WebCategoryDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/30 20:50
 */
@Repository
public interface WebCategoryDao {
    List<WebCategoryDto> getCategoryCount();

}
