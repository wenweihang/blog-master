package com.zoe.blog.dao;

import com.zoe.blog.model.Reply;
import org.springframework.stereotype.Repository;

/**
 * Created by liuxuelian on 2020/07/28 11:12
 */
@Repository
public interface ReplyDao {

    int insert(Reply reply);

    int update(Reply reply);

    int delete(Integer id);

    int deleteByCommentId(Integer commentId);
}
