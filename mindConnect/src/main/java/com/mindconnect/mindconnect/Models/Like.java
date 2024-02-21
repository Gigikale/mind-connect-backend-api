package com.mindconnect.mindconnect.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "likes")
@Table(name = "likes")
@Getter
@Setter
public class Like extends BaseEntity {
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "like_user_fkey")
    )
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "like_post_fkey")
    )
    private Post post;

    @ManyToOne
    @JoinColumn(
            name = "comment_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "like_comment_fkey")
    )
    private Comment comment;
}
