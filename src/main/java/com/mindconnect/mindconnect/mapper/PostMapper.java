package com.mindconnect.mindconnect.mapper;

import com.mindconnect.mindconnect.Models.Post;
import com.mindconnect.mindconnect.payloads.PostHistory;

public class PostMapper {
    public static PostHistory mapToPostHistory(Post post, PostHistory postHistory){
        postHistory.setContent(post.getContent());
        postHistory.setComment(post.getComment());
        postHistory.setLikes(post.getLikes());
        return postHistory;
    }
}
