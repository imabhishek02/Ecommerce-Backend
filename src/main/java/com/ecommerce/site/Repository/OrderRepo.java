package com.ecommerce.site.Repository;

import com.ecommerce.site.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepo extends JpaRepository<Order,Long> {

    List<Order> findByUserId(Long userId);
}
