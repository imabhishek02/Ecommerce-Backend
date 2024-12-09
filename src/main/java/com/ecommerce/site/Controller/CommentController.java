package com.ecommerce.site.Controller;

import com.ecommerce.site.Dto.CommentDTO;
import com.ecommerce.site.Model.User;
import com.ecommerce.site.Service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/product/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentDTO>addComment(@PathVariable Long productId,
                                                @AuthenticationPrincipal UserDetails userDetails,
                                                @Valid @RequestBody CommentDTO commentDTO){
        Long userId = ((User)userDetails).getId();
        return ResponseEntity.ok(commentService.addComment(productId,userId,commentDTO));

    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByProducts(@PathVariable Long productId){
        return ResponseEntity.ok(commentService.getCommentsByProduct(productId));
    }
}
