package com.ecommerce.site.Service;

import com.ecommerce.site.Dto.ProductDTO;
import com.ecommerce.site.Dto.ProductListDTO;
import com.ecommerce.site.Model.Product;
import com.ecommerce.site.Repository.ProductRepo;
import com.ecommerce.site.exception.ResourceNotFoundException;
import com.ecommerce.site.mapper.ProductMapper;
import jakarta.transaction.Transactional;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Service
public class ProductService {
    @Autowired
    private  ProductRepo productRepo;

    @Autowired
    private  ProductMapper productMapper;

    private static final String UPLOAD_DIR = "src/main/resources/static/images";

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile image) throws IOException {
        Product product = productMapper.toEntity(productDTO);
        if(image!=null && !image.isEmpty()){
            String fileName = saveImage(image);
            product.setImage("/images"+fileName);
        }
        Product savedProduct = productRepo.save(product);
        return productMapper.toDTO(product);
    }
    @Transactional
    public ProductDTO updateProduct(Long id,ProductDTO productDTO,MultipartFile image)throws IOException{
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found with given id"));
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription((productDTO.getDescription()));
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setQuantity(productDTO.getQuantity());
        if(image!=null && !image.isEmpty()){
            String fileName = saveImage(image);
            existingProduct.setImage("/images"+fileName);
        }
        Product updatedProduct = productRepo.save(existingProduct);
        return productMapper.toDTO(updatedProduct);

    }

    public void deleteProduct(Long id){
        if(!productRepo.existsById(id)){
           throw new ResourceNotFoundException("Product not found");
        }
        productRepo.deleteById(id);
    }

    public ProductDTO getProduct(Long id){
        Product product = productRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("NOT Found"));
        return productMapper.toDTO(product);

    }
    public List<ProductListDTO> getAllProduct(){
        return productRepo.findAllWithoutComments();
    }

    public String saveImage(MultipartFile image) throws IOException {
        String filename = UUID.randomUUID().toString()+"_"+image.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR+filename);
        Files.createDirectories(path.getParent());
        Files.write(path,image.getBytes());
        return filename;
    }


}
