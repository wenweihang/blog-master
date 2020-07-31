package com.zoe.blog.service;

import com.github.pagehelper.PageHelper;
import com.zoe.blog.dao.CommentDao;
import com.zoe.blog.dao.ReplyDao;
import com.zoe.blog.dto.CommentEnabledParam;
import com.zoe.blog.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by liuxuelian on 2020/07/28 14:04
 */
@Service
public class CommentService {
    @Autowired
    CommentDao commentDao;

    @Autowired
    ReplyDao replyDao;

    public List<Comment> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return commentDao.getList();
    }

    public int updateEnabled(CommentEnabledParam commentEnabledParam) {
        return commentDao.updateEnabled(commentEnabledParam);
    }

    @Transactional
    public int delete(Integer id) {
        // 删除评论先删除回复
        replyDao.deleteByCommentId(id);

        return commentDao.delete(id);
    }

    @Transactional
    public void deleteByArticleId(Integer articleId) {
        List<Comment> comments = commentDao.findCommentByArticleId(articleId);

        for (int i = 0; i < comments.size(); i++) {
            Integer commentId = comments.get(i).getId();
            replyDao.deleteByCommentId(commentId);
            commentDao.delete(commentId);
        }
    }
}
