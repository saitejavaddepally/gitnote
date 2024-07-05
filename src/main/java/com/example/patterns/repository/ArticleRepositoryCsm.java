package com.example.patterns.repository;

import com.example.patterns.model.TbCsmUserArticleModel;
import com.example.patterns.model.TbWfwUserArticleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepositoryCsm extends JpaRepository<TbCsmUserArticleModel, Integer> {
}