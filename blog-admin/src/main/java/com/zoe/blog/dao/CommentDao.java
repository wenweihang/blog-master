package com.zoe.blog.dao;

import com.zoe.blog.dto.CommentEnabledParam;
import com.zoe.blog.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/28 11:14
 */
@Repository
public interface CommentDao {

    List<Comment> getList();

    List<Comment> findCommentByArticleId(Integer articleId);

    int updateEnabled(CommentEnabledParam commentEnabledParam);

    int delete(Integer id);

}
