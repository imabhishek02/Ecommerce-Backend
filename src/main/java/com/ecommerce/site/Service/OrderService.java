package com.ecommerce.site.Service;

import com.ecommerce.site.Dto.CartDTO;
import com.ecommerce.site.Dto.OrderDTO;
import com.ecommerce.site.Model.*;
import com.ecommerce.site.Repository.OrderRepo;
import com.ecommerce.site.Repository.ProductRepo;
import com.ecommerce.site.Repository.UserRepo;
import com.ecommerce.site.exception.InsufficientStockException;
import com.ecommerce.site.exception.ResourceNotFoundException;
import com.ecommerce.site.mapper.CartMapper;
import com.ecommerce.site.mapper.OrderMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CartMapper cartMapper;

    @Transactional
    public OrderDTO createOrder(Long userId,String address,String phoneNumber){
        User user = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
        CartDTO cartDTO = cartService.getCart(userId);
        Cart cart = cartMapper.toEntity(cartDTO);

        if(cart.getItems().isEmpty()){
            throw  new IllegalStateException("Cannot create an order with an empty cart");
        }
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setPhoneNumber(phoneNumber);
        order.setStatus(Order.OrderStatus.PREPARING);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItem= createOrderItems(cart,order);
        order.setItems(orderItem);
        Order savedOrder = orderRepo.save(order);
        cartService.clearCart(userId);

        try{
            emailService.sendOrderConfirmation(savedOrder);
        } catch (MailException e) {
            logger.error("Failed to send Order Confirmation Email for Order Id :"+savedOrder.getId());

        }
        return orderMapper.toDTO(savedOrder);

    }
    public List<OrderItem> createOrderItems(Cart cart,Order order){
        return cart.getItems().stream().map(cartItem->{
            Product product = productRepo.findById(cartItem.getProduct().getId())
                    .orElseThrow(()->new EntityNotFoundException("Product not found with id: "+cartItem.getProduct().getId()));
            if(product.getQuantity()==null){
                throw new IllegalStateException("Product quantity is not set for the product " + product.getName());

            }
            if(product.getQuantity() < cartItem.getQuantity()){
                throw new InsufficientStockException("Not enough stock for the product"+product.getName());
            }
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productRepo.save(product);

            return new OrderItem(null,order,product,cartItem.getQuantity(),product.getPrice());
        }).collect(Collectors.toList());
    }
    public List<OrderDTO> getAllOrders(){
        return orderMapper.toDTOs(orderRepo.findAll());
    }
    public List<OrderDTO> getUserOrder(Long userId){
        return orderMapper.toDTOs(orderRepo.findByUserId(userId));
    }
    public OrderDTO updateOrderStatus(Long orderId,Order.OrderStatus status){
        Order order = orderRepo.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("Order not found"));
        order.setStatus(status);
        Order updatedOrder = orderRepo.save(order);
        return orderMapper.toDTO(updatedOrder);


    }

}
