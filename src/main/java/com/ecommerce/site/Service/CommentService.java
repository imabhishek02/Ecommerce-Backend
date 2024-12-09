package com.ecommerce.site.Service;

import com.ecommerce.site.Dto.CommentDTO;
import com.ecommerce.site.Model.Comment;
import com.ecommerce.site.Model.Product;
import com.ecommerce.site.Model.User;
import com.ecommerce.site.Repository.CommentRepos;
import com.ecommerce.site.Repository.ProductRepo;
import com.ecommerce.site.Repository.UserRepo;
import com.ecommerce.site.exception.ResourceNotFoundException;
import com.ecommerce.site.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepos commentRepos;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CommentMapper commentMapper;

    public CommentDTO addComment(Long productId,Long userId,CommentDTO commentDTO){
        Product product = productRepo.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        Comment comment = commentMapper.toEntity(commentDTO);
        comment.setProduct(product);
        comment.setUser(user);
        Comment saveComment = commentRepos.save(comment);
        return commentMapper.toDTO(comment);

    }

    public List<CommentDTO> getCommentsByProduct(Long productId){
        List<Comment> comments = commentRepos.findByProductId(productId);
        return comments.stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }

}
