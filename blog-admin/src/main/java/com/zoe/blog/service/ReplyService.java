package com.zoe.blog.service;

import com.zoe.blog.dao.ReplyDao;
import com.zoe.blog.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liuxuelian on 2020/07/28 15:42
 */
@Service
public class ReplyService {

    @Autowired
    ReplyDao replyDao;

    public int insert(Reply reply) {
        return replyDao.insert(reply);
    }

    public int update(Reply reply) {
        return replyDao.update(reply);
    }

    public int delete(Integer id) {
        return replyDao.delete(id);
    }
}
