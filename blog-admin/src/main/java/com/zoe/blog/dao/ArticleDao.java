package com.zoe.blog.dao;

import com.zoe.blog.dto.ArticleQueryParam;
import com.zoe.blog.model.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 18:08
 */
@Repository
public interface ArticleDao {

    List<Article> getList(ArticleQueryParam articleQueryParam);

    Article findByArticleId(Integer id);

    int insert(Article article);

    int update(Article article);

    int addOneViews(Integer articleId);

    int delete(Integer id);
}
