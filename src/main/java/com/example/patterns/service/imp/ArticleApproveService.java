package com.example.patterns.service.imp;


import com.example.patterns.model.TbCsmUserArticleModel;
import com.example.patterns.model.TbWfwUserArticleModel;
import com.example.patterns.repository.ArticleRepositoryCsm;
import com.example.patterns.repository.ArticleRepositoryWfw;
import com.example.patterns.service.IArticleApproveService;
import com.example.patterns.vo.ApproveRejectReqVo;
import com.example.patterns.vo.TbWfwUserArticleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ArticleApproveService implements IArticleApproveService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleApproveService.class);

    @Autowired
    ArticleRepositoryWfw articleRepositoryWfw;

    @Autowired
    ArticleRepositoryCsm articleRepositoryCsm;



    @Override
    public void approveArticleForUser(ApproveRejectReqVo approveRejectReqVo) throws Exception {

        logger.info("Article id is {}", approveRejectReqVo.getArticleId());

        TbCsmUserArticleModel tbCsmUserArticleModel = new TbCsmUserArticleModel();

        Optional<TbWfwUserArticleModel> articleVoOptional = articleRepositoryWfw.findById(Long.valueOf(approveRejectReqVo.getArticleId()));

        if(articleVoOptional.isEmpty()){
            logger.info("Unable to find the article that needs to be thrown");
            throw new Exception("Server Error, pls wait ");
        }


        TbWfwUserArticleModel tbWfwUserArticleModel = articleVoOptional.get();

        // move to csm and set status of isApproved to "YES"
        BeanUtils.copyProperties(tbWfwUserArticleModel, tbCsmUserArticleModel);

        // save the details in db the details approved . .
        logger.info("Model after copy is {}", tbCsmUserArticleModel);

        tbCsmUserArticleModel.setIsArticleApproved("Y");

        articleRepositoryCsm.save(tbCsmUserArticleModel);

        articleRepositoryWfw.updateTbWfwUserArticle(articleVoOptional.get().getArticleId());


    }
}
