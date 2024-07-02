package com.example.patterns.service.imp;


import com.example.patterns.exceptions.UnauthorizedException;
import com.example.patterns.model.TbWfwUserArticleModel;
import com.example.patterns.repository.ArticleRepository;
import com.example.patterns.service.IArticlePostService;
import com.example.patterns.utils.ApplicationConstants;
import com.example.patterns.vo.TbWfwUserArticleVo;
import com.example.patterns.vo.auth.CustomUserDetails;
import io.micrometer.common.util.StringUtils;
import org.apache.coyote.BadRequestException;
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

        if (StringUtils.isEmpty(tbWfwUserArticle.getArticleContent()) || tbWfwUserArticle.getArticleContent().length() < 100){
            logger.error("Bad Request, Article content is empty and is less than 100 characters");
            throw new IllegalArgumentException("Bad request given , check Article content it is empty or is less than 100 characters" );
        }

        if(hasRole(ApplicationConstants.MAKER, principal)){
            String userId = String.valueOf(principal.getUser().getId());
            tbWfwUserArticleVo.setArticleContent(tbWfwUserArticle.getArticleContent());
            tbWfwUserArticleVo.setUserIdMapping(userId);
            tbWfwUserArticleVo.setIsArticleApproved(ApplicationConstants.NO);
            tbWfwUserArticleVo.setImageLinks(tbWfwUserArticle.getImageLinks());
            tbWfwUserArticleVo.setActionType(ApplicationConstants.ADD_ARTICLE);
            tbWfwUserArticleVo.setCreatedBy(principal.getUsername());
            tbWfwUserArticleVo.setApprovedBy("NONE");
            articleRepository.save(tbWfwUserArticleVo);
        }
        else{
            throw new UnauthorizedException("You are UnAuthorized to make a change .. ");
        }
    }

    public boolean hasRole(String requiredRole, CustomUserDetails customUserDetails) {
        return customUserDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(requiredRole));
    }

}
