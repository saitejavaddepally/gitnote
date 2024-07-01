package com.example.patterns.repository;

import com.example.patterns.model.TbWfwUserArticleModel;
import com.example.patterns.vo.TbWfwUserArticleVo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<TbWfwUserArticleModel, Integer> {


}