package com.ecommerce.site.Service;

import com.ecommerce.site.Dto.CartDTO;
import com.ecommerce.site.Dto.ProductDTO;
import com.ecommerce.site.Model.Cart;
import com.ecommerce.site.Model.CartItem;
import com.ecommerce.site.Model.Product;
import com.ecommerce.site.Model.User;
import com.ecommerce.site.Repository.CartRepository;
import com.ecommerce.site.Repository.ProductRepo;
import com.ecommerce.site.Repository.UserRepo;
import com.ecommerce.site.exception.InsufficientStockException;
import com.ecommerce.site.exception.ResourceNotFoundException;
import com.ecommerce.site.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartMapper cartMapper;

    public CartDTO addToCart(Long userId,Long productId,Integer quantity){
        User user = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
        Product product = productRepo.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product not found"));

        if(product.getQuantity()<quantity){
            throw new InsufficientStockException("Not enough available");
        }
        Cart cart = cartRepository.findById(userId)
                .orElse(new Cart(null,user,new ArrayList<>()));
        Optional<CartItem> existingCartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        if(existingCartItem.isPresent()){
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }else{
            CartItem cartItem = new CartItem(null,cart,product,quantity);
            cart.getItems().add(cartItem);
        }
        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }
    public CartDTO getCart(Long userId){
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
        return cartMapper.toDTO(cart);
    }
    public void clearCart(Long userId){
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));

        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
