package com.example.patterns.service.imp;


import com.example.patterns.model.TbWfwUserArticleModel;
import com.example.patterns.repository.ArticleRepository;
import com.example.patterns.service.IArticlePostService;
import com.example.patterns.vo.TbWfwUserArticleVo;
import com.example.patterns.vo.auth.CustomUserDetails;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticlePostService implements IArticlePostService {

    private static final Logger logger = LoggerFactory.getLogger(ArticlePostService.class);

    @Autowired
    ArticleRepository articleRepository;


    @Override
    public void saveArticleForUser(TbWfwUserArticleVo tbWfwUserArticle, CustomUserDetails principal) {

        TbWfwUserArticleModel tbWfwUserArticleVo = new TbWfwUserArticleModel();

        if (StringUtils.isEmpty(tbWfwUserArticle.getArticleContent())){
            logger.error("Bad Request, Article content is empty");
            throw new IllegalArgumentException("Bad request given , check Article content it is empty .." );
        }

        String userId = String.valueOf(principal.getUser().getId());
        tbWfwUserArticleVo.setArticleContent(tbWfwUserArticle.getArticleContent());
        tbWfwUserArticleVo.setUserIdMapping(userId);
        tbWfwUserArticleVo.setIsArticleApproved("N");
        tbWfwUserArticleVo.setImageLinks(tbWfwUserArticle.getImageLinks());
        tbWfwUserArticleVo.setActionType("ADD_NEW_ARTICLE");
        tbWfwUserArticleVo.setApprovedBy("NONE");

        articleRepository.save(tbWfwUserArticleVo);

    }

}
