package com.ecommerce.site.Repository;

import com.ecommerce.site.Model.Cart;
import com.ecommerce.site.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepos extends JpaRepository<Comment,Long> {

    List<Comment> findByProductId(Long productId);
}
