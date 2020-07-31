package com.zoe.blog.dao;

import com.zoe.blog.dto.UvAmountParam;
import com.zoe.blog.model.Uv;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 统计每日访问量
 * Created by liuxuelian on 2020/07/30 15:37
 */
@Repository
public interface UvDao {

    Uv getCurrentDay();

    UvAmountParam getUvAmount();


    List<Uv> getRecentlySevenDays();

    /**
     * 初始化新增当日的记录
     */
    int insert();

    int addOneUv();

    int addOneComments();

}
