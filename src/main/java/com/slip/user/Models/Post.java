package com.slip.user.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue private Long id;
    private String postRef;
    @NotEmpty @NotBlank
    private String writeUp;
    private String imageUrl;
    private Long likes;
    private List<String> comments;
    private Long shares;
    private List<String> postImgUrls;
}
