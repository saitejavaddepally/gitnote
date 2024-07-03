package com.example.patterns.service.imp;


import com.example.patterns.exceptions.UnauthorizedException;
import com.example.patterns.model.TbCsmUserArticleModel;
import com.example.patterns.model.TbWfwUserArticleModel;
import com.example.patterns.repository.ArticleRepositoryCsm;
import com.example.patterns.repository.ArticleRepositoryWfw;
import com.example.patterns.service.IArticlePostService;
import com.example.patterns.utils.ApplicationConstants;
import com.example.patterns.vo.TbCsmUserArticleVo;
import com.example.patterns.vo.TbWfwUserArticleVo;
import com.example.patterns.vo.auth.CustomUserDetails;
import io.micrometer.common.util.StringUtils;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ArticlePostService implements IArticlePostService {

    private static final Logger logger = LoggerFactory.getLogger(ArticlePostService.class);

    @Autowired
    ArticleRepositoryWfw articleRepositoryWfw;

    @Autowired
    ArticleRepositoryCsm articleRepositoryCsm;

    @Override
    public void saveArticleForUser(TbWfwUserArticleVo tbWfwUserArticle, CustomUserDetails principal) throws Exception {

        TbWfwUserArticleModel tbWfwUserArticleModel = new TbWfwUserArticleModel();

        if (StringUtils.isEmpty(tbWfwUserArticle.getArticleContent()) || tbWfwUserArticle.getArticleContent().length() < 100){
            logger.error("Bad Request, Article content is empty and is less than 100 characters");
            throw new IllegalArgumentException("Bad request given , check Article content it is empty or is less than 100 characters" );
        }

        if(hasRole(ApplicationConstants.MAKER, principal)){

            if(tbWfwUserArticle.getActionType().equalsIgnoreCase(ApplicationConstants.MODIFY_ARTICLE)){

                // get csm user + Article details
                Optional<TbCsmUserArticleModel> tbCsmUserArticleVo = articleRepositoryCsm.findById(Integer.parseInt(tbWfwUserArticle.getPreviousUserArticleId()));

                if(tbCsmUserArticleVo.isEmpty()){
                    throw new BadRequestException("Some issue with DB, checking from our end. ");
                }
                // copy properties of csm to wfw
                BeanUtils.copyProperties(tbCsmUserArticleVo.get(), tbWfwUserArticleModel);

                // save wfw details to db again but with new article id
                // later if approved copy make csm isActive to 0 and copy properties of wfw to csm again
            }

            adaptAndSaveArticle(tbWfwUserArticleModel, tbWfwUserArticle, principal);
        }
        else{
            throw new UnauthorizedException("You are UnAuthorized to make a change .. ");
        }
    }


    public void adaptAndSaveArticle(TbWfwUserArticleModel tbWfwUserArticleVo, TbWfwUserArticleVo tbWfwUserArticle, CustomUserDetails principal){
        tbWfwUserArticleVo.setArticleContent(tbWfwUserArticle.getArticleContent());
        tbWfwUserArticleVo.setUserIdMapping(String.valueOf(principal.getUser().getId()));
        tbWfwUserArticleVo.setIsArticleApproved(ApplicationConstants.NO);
        tbWfwUserArticleVo.setImageLinks(tbWfwUserArticle.getImageLinks());
        tbWfwUserArticleVo.setActionType(ApplicationConstants.ADD_ARTICLE);
        tbWfwUserArticleVo.setCreatedBy(principal.getUsername());
        tbWfwUserArticleVo.setApprovedBy("NONE");
        articleRepositoryWfw.save(tbWfwUserArticleVo);
    }



    public boolean hasRole(String requiredRole, CustomUserDetails customUserDetails) {
        return customUserDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(requiredRole));
    }

}
