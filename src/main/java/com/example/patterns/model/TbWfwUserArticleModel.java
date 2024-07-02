package com.example.patterns.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_wfw_user_articles") // Define your table name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TbWfwUserArticleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Assuming you have an ID field

    @Column(name = "article_content", columnDefinition = "TEXT")
    private String articleContent;

    @Column(name = "user_id_mapping", nullable = false)
    private String userIdMapping;

    @Column(name = "is_article_approved")
    private String isArticleApproved;

    @Column(name = "approved_by")
    private String approvedBy;

    @ElementCollection
    @CollectionTable(name = "article_image_links", joinColumns = @JoinColumn(name = "article_id"))
    @Column(name = "image_link")
    private List<String> imageLinks;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;
    // Getters, setters, and constructors are already generated by Lombok

}