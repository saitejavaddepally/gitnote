package com.example.patterns.repository;

import com.example.patterns.model.TbWfwUserArticleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepositoryWfw extends JpaRepository<TbWfwUserArticleModel, Integer> {

}