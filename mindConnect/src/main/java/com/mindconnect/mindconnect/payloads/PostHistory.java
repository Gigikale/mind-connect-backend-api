package com.mindconnect.mindconnect.payloads;

import com.mindconnect.mindconnect.Models.Comment;
import com.mindconnect.mindconnect.Models.Like;
import com.mindconnect.mindconnect.Models.User;
import lombok.Data;

import java.util.List;

@Data
public class PostHistory {
    private String content;

    private List<Comment> comment;

    private List<Like> likes;

}
