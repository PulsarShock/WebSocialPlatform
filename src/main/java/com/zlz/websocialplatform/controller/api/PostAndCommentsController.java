package com.zlz.websocialplatform.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/posts_and_comments")
public class PostAndCommentsController {

    List<String> list= Arrays.asList(
            "/get_posts_list",
            "/get_single_post",
            "/get_single_comment",
            "/change_up_down_state",
            "/shutdown_comments",
            "/delete",
            "/reply",
            "/new_post"
    );

}
