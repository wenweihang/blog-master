package com.zoe.blog;

import com.zoe.blog.dao.CommentDao;
import com.zoe.blog.model.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class BlogAdminApplicationTests {

    @Test
    public void test() {
        long time = new Date().getTime();
        System.out.println("time = " + time);
    }

}
