package com.ecommerce.site.Repository;

import com.ecommerce.site.Dto.ProductListDTO;
import com.ecommerce.site.Model.Cart;
import com.ecommerce.site.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product,Long> {

    @Query("SELECT new com.ecommerce.site.Dto.ProductListDTO(p.id, p.name, p.description, p.price, p.quantity, p.image) FROM Product p")
    List<ProductListDTO> findAllWithoutComments();
}
