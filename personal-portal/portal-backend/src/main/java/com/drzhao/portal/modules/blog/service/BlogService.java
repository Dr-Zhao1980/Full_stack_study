package com.drzhao.portal.modules.blog.service;

import com.drzhao.portal.modules.blog.model.BlogPost;
import com.drzhao.portal.modules.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public List<BlogPost> getAllPosts() {
        // 按创建时间倒序排列 (最新的在前面)
        return blogRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public BlogPost getPostById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("文章不存在"));
    }

    public BlogPost createPost(BlogPost post) {
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        // 如果摘要为空，自动截取正文前50字
        if (post.getSummary() == null && post.getContent().length() > 50) {
            post.setSummary(post.getContent().substring(0, 50) + "...");
        } else if (post.getSummary() == null) {
            post.setSummary(post.getContent());
        }
        return blogRepository.save(post);
    }
}