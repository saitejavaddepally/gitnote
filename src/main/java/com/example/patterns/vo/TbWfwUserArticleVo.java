package com.example.patterns.vo;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TbWfwUserArticleVo {

    String articleContent;
    String userIdMapping;
    String isArticleApproved;
    String approvedBy;
    List<String> imageLinks;
    String actionType;


}
