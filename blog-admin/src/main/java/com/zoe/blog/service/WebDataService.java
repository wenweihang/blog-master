package com.zoe.blog.service;

import com.zoe.blog.dao.UvDao;
import com.zoe.blog.model.Uv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据统计
 * Created by liuxuelian on 2020/07/30 17:37
 */
@Service
public class WebDataService {
    @Autowired
    UvDao uvDao;

    public List<Uv> getRecentlySevenDays() {
        return uvDao.getRecentlySevenDays();
    }
}
