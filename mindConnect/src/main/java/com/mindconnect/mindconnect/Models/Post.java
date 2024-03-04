package com.mindconnect.mindconnect.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "posts")
@Table(name = "posts")
@Getter
@Setter
public class Post extends BaseEntity {
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "hidden")
    private Boolean hidden;

    @Column(name = "report_count")
    private Integer reportCount;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "post_user_fkey")
    )
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "group_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "post_group_fkey")
    )
    private Group group;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes;

    public Integer getReportCount() {
        return reportCount != null ? reportCount : 0;
    }

}
