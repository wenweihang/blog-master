package com.zoe.blog.service;

import com.zoe.blog.dao.WebCategoryDao;
import com.zoe.blog.dto.WebCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/30 20:52
 */
@Service
public class WebCategoryService {
    @Autowired
    WebCategoryDao webCategoryDao;

    public List<WebCategoryDto> getCategoryCount() {
        return webCategoryDao.getCategoryCount();
    }
}
