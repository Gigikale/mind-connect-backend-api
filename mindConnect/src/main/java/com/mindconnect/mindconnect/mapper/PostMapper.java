package com.mindconnect.mindconnect.mapper;

import com.mindconnect.mindconnect.Models.Post;
import com.mindconnect.mindconnect.payloads.PostHistory;

public class PostMapper {
    public static PostHistory mapToPostHistory(Post post, PostHistory postHistory, Long likeCount){
        postHistory.setContent(post.getContent());
        postHistory.setLikeCount(likeCount);
        return postHistory;
    }
}
