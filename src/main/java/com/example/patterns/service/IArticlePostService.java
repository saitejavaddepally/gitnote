package com.example.patterns.service;

import com.example.patterns.vo.TbWfwUserArticleVo;
import com.example.patterns.vo.auth.CustomUserDetails;
import org.springframework.stereotype.Component;

@Component
public interface IArticlePostService {
    public void saveArticleForUser(TbWfwUserArticleVo tbWfwUserArticleVo, CustomUserDetails principal);
}
