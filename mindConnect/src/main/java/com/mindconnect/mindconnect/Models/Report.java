package com.mindconnect.mindconnect.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "reports")
@Table(name = "reports")
@Getter
@Setter
public class Report extends BaseEntity {

    @Column(name = "reason", nullable = false)
    private String reason;

    @ManyToOne
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "report_post_fkey")
    )
    private Post post;

    @ManyToOne
    @JoinColumn(
            name = "reportedBy_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "reportedBy_user_fkey")
    )
    private User reportedBy;

}
