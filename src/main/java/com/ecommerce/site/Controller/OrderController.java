package com.ecommerce.site.Controller;

import com.ecommerce.site.Dto.OrderDTO;
import com.ecommerce.site.Model.Order;
import com.ecommerce.site.Model.User;
import com.ecommerce.site.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    @PreAuthorize("isAuthenticated")
    public ResponseEntity<OrderDTO> createOrder(@AuthenticationPrincipal UserDetails userDetails
                                                 ,@RequestParam String address,
                                                @RequestParam String phoneNumber){
        Long userId = ((User)userDetails).getId();
        OrderDTO orderDTO = orderService.createOrder(userId,address,phoneNumber);
        return ResponseEntity.ok(orderDTO);

    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/user")
    @PreAuthorize("isAuthenticated")
    public ResponseEntity<List<OrderDTO>> getUserOrder(@AuthenticationPrincipal UserDetails userDetails){
        Long userId = ((User)userDetails).getId();
        List<OrderDTO> orders = orderService.getUserOrder(userId);
        return ResponseEntity.ok(orders);
    }
    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long orderId,
                                                      @RequestParam Order.OrderStatus status){
        OrderDTO updateOrder = orderService.updateOrderStatus(orderId,status);
        return ResponseEntity.ok(updateOrder);
    }


}
