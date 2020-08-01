package com.zoe.blog.dao;

import com.zoe.blog.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/30 11:56
 */
@Repository
public interface WebCommentDao {

    List<Comment> getCommentsByArticleId(Integer articleId);

    /**
     * 获取最新的前8条评论
     * @return
     */
    List<Comment> getCommentsLimitEight();

    int getCommentCount();

    int insert(Comment comment);

}
