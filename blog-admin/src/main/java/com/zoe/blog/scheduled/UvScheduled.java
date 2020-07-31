package com.zoe.blog.scheduled;

import com.zoe.blog.dao.UvDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by liuxuelian on 2020/07/30 15:24
 */
@Component
public class UvScheduled {

    @Autowired
    UvDao uvDao;

    @Scheduled(cron = "${time.cron}")
    public void createUvCurrentDateRecord() {
        uvDao.insert();
    }
}
