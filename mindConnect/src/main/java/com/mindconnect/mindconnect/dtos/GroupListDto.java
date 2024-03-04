package com.mindconnect.mindconnect.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupListDto {
       private String name;
       private String about;
       private Long usersCount;
       private Long postsCount;
       private String adminName;
       private String createdAt;

}
