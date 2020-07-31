package com.zoe.blog.dao;

import com.zoe.blog.dto.WebArchiveDto;
import com.zoe.blog.dto.WebCategoryDto;
import com.zoe.blog.model.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/29 11:19
 */
@Repository
public interface WebArticleDao {

    List<WebArchiveDto> getArchive();

    List<Article> findArticlesByTitle(String title);

    List<Article> findArticleCountByArchive(String date);

    List<WebCategoryDto> findArticleCountByCategory();

    List<Article> findArticleByCategoryId(Integer categoryId);

    int getArticleCount();

}
