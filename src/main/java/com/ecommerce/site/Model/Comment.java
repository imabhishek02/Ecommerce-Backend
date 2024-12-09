package com.ecommerce.site.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Integer score;

    //one product can have many comments
    @ManyToOne
    @JoinColumn(name="product_id",nullable = false)
    private Product product;

    //one user can have multiple comments
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;



}
