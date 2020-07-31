package com.zoe.blog.service;

import com.zoe.blog.dao.UvDao;
import com.zoe.blog.dto.UvAmountParam;
import com.zoe.blog.model.Uv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liuxuelian on 2020/07/31 12:45
 */
@Service
public class UvService {
    @Autowired
    UvDao uvDao;

    public Uv getCurrentDay() {
        return uvDao.getCurrentDay();
    }

    public UvAmountParam getUvAmount() {
        return uvDao.getUvAmount();
    }
}
