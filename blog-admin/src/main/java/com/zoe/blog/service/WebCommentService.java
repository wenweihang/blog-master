package com.zoe.blog.service;

import com.zoe.blog.dao.UvDao;
import com.zoe.blog.dao.WebCommentDao;
import com.zoe.blog.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/30 11:56
 */
@Service
public class WebCommentService {
    @Autowired
    WebCommentDao webCommentDao;

    @Autowired
    UvDao uvDao;

    public List<Comment> list(Integer articleId) {
        return webCommentDao.getCommentsByArticleId(articleId);
    }

    public List<Comment> getCommentLimitEight() {
        return webCommentDao.getCommentsLimitEight();
    }

    public int insert(Comment comment) {
        // 记录日评论数
        uvDao.addOneComments();

        return webCommentDao.insert(comment);
    }

}
