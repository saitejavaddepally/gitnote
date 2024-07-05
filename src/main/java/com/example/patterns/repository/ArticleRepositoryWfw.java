package com.example.patterns.repository;

import com.example.patterns.model.TbWfwUserArticleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ArticleRepositoryWfw extends JpaRepository<TbWfwUserArticleModel, Integer> {
    @Query("Update TbWfwUserArticleModel set is_article_approved = :isApproved where article_id = :articleId")
    void updateTbWfwUserArticle(@Param("isApproved") String isApproved, @Param("articleId") String articleId);

}