package com.deepak.ecom_project.productservice;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.deepak.ecom_project.entity.Product;
import com.deepak.ecom_project.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    public Product getProductById(int productId) {
        return productRepository.findById((long) productId).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());

        return productRepository.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageDate(imageFile.getBytes());
            
            return productRepository.save(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById((long) id);
    }   

    
       

}
