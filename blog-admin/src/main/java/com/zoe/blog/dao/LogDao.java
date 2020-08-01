package com.zoe.blog.dao;

import com.zoe.blog.dto.LogQueryParam;
import com.zoe.blog.model.Log;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuxuelian on 2020/08/01 11:05
 */
@Repository
public interface LogDao {
    List<Log> getLogs(LogQueryParam queryParam);

    List<String> getLevelGroup();

    int delete(Integer id);
}
