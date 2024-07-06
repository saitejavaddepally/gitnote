package com.example.patterns.repository;

import com.example.patterns.model.TbWfwUserArticleModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ArticleRepositoryWfw extends JpaRepository<TbWfwUserArticleModel, Long> {

    @Modifying
    @Query("UPDATE TbWfwUserArticleModel a SET a.isArticleApproved = 'Y' WHERE a.articleId = :articleId")
    @Transactional
    void updateTbWfwUserArticle(@Param("articleId") Long articleId);

}