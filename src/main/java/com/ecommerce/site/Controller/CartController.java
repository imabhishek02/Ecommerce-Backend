package com.ecommerce.site.Controller;

import com.ecommerce.site.Dto.CartDTO;
import com.ecommerce.site.Model.User;
import com.ecommerce.site.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartDTO>addToCart(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestParam Long productId,
                                            @RequestParam Integer quantity){
        Long userId = ((User)userDetails).getId();
        return ResponseEntity.ok(cartService.addToCart(userId,productId,quantity));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartDTO>getCart(@AuthenticationPrincipal UserDetails userDetails){
        Long userId = ((User)userDetails).getId();
        return ResponseEntity.ok(cartService.getCart(userId));
    }
    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void>clearCart(@AuthenticationPrincipal UserDetails userDetails){
        Long userId = ((User)userDetails).getId();
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
