package com.ecommerce.site.Controller;

import com.ecommerce.site.Dto.ProductDTO;
import com.ecommerce.site.Dto.ProductListDTO;
import com.ecommerce.site.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(" ")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value="/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> createProduct(@RequestPart("product") @Valid ProductDTO productDTO,
                                                    @RequestPart(value = "image",required = false)MultipartFile image) throws IOException {
        return ResponseEntity.ok(productService.createProduct(productDTO,image));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,@RequestPart("product") @Valid ProductDTO productDTO,
                                                    @RequestPart(value = "image",required = false)MultipartFile image) throws IOException {
        return ResponseEntity.ok(productService.updateProduct(id,productDTO,image));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id){
        productService.getProduct(id);
        return ResponseEntity.ok(productService.getProduct(id));
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ProductListDTO>> getAllProduct(){

        return ResponseEntity.ok(productService.getAllProduct());
    }

}
