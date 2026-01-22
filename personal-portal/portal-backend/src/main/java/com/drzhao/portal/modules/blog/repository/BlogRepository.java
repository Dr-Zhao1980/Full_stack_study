package com.drzhao.portal.modules.blog.repository;

import com.drzhao.portal.modules.blog.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<BlogPost, Long> {
    // 基础的 CRUD 由 JpaRepository 提供，无需手写
}