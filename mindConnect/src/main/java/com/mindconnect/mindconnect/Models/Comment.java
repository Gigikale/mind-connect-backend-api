package com.mindconnect.mindconnect.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "comments")
@Table(name = "comments")
@Getter
@Setter
public class Comment extends BaseEntity{
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "comment_user_fkey")
    )
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "comment_post_fkey")
    )
    private Post post;

//    @ManyToOne
//    @JoinColumn(
//            name = "comment_id",
//            referencedColumnName = "id",
//            foreignKey = @ForeignKey(name = "comment_comment_fkey")
//    )
//    private Comment comment;
//
//    @OneToMany(mappedBy = "comment")
//    private List<Comment> comments;

    @OneToMany(mappedBy = "comment")
    private List<Like> likes;


}
