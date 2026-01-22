package com.drzhao.portal.modules.blog.controller;

import com.drzhao.portal.common.Result;
import com.drzhao.portal.modules.blog.model.BlogPost;
import com.drzhao.portal.modules.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/list")
    public Result<List<BlogPost>> list() {
        return Result.success(blogService.getAllPosts());
    }

    @GetMapping("/{id}")
    public Result<BlogPost> detail(@PathVariable Long id) {
        return Result.success(blogService.getPostById(id));
    }

    @PostMapping("/publish")
    public Result<BlogPost> publish(@RequestBody BlogPost post) {
        // 这里简单将作者写死，实际应从 Token 获取
        post.setAuthor("Dr.Zhao");
        return Result.success(blogService.createPost(post));
    }
}