package com.zoe.blog.service;

import com.github.pagehelper.PageHelper;
import com.zoe.blog.dao.LogDao;
import com.zoe.blog.dto.LogQueryParam;
import com.zoe.blog.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuxuelian on 2020/08/01 11:08
 */
@Service
public class LogService {
    @Autowired
    LogDao logDao;


    public List<Log> list(LogQueryParam queryParam) {
        PageHelper.startPage(queryParam.getPageNum(), queryParam.getPageSize());
        return logDao.getLogs(queryParam);
    }

    public List<String> getLevelGroup() {
        return logDao.getLevelGroup();
    }

    public int delete(Integer id) {
        return logDao.delete(id);
    }

}
