package com.example.patterns.repository;

import com.example.patterns.model.TbCsmUserArticleModel;
import com.example.patterns.model.TbWfwUserArticleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepositoryCsm extends JpaRepository<TbCsmUserArticleModel, Integer> {

}