package com.deepak.ecom_project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deepak.ecom_project.entity.Product;
import com.deepak.ecom_project.productservice.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class Controller {

    @Autowired
    private ProductService productService;

    @RequestMapping("/")
    public String home() {
        return "Hello world";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProduct() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId) {
        Product product = productService.getProductById(productId);

        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/add_product", consumes = { "multipart/form-data" })
    public ResponseEntity<?> addProduct(
            @RequestPart("product") Product product,
            @RequestPart("imageFile") MultipartFile imageFile) {

        System.out.println(product);

        try {
            Product product1 = productService.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int id) {
        Product product = productService.getProductById(id);
        byte[] imageFile = product.getImageDate();

        // return
        // ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);

        if (product == null || product.getImageDate() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg") // or "image/png" depending on your data
                .body(product.getImageDate());
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id,
            @RequestPart Product product,
            @RequestPart MultipartFile imageFile) {

        Product product2 = null;
        try {
            product2 = productService.updateProduct(id, product, imageFile);
        } catch (Exception e) {
            return new ResponseEntity<>("Somthing went wrong", HttpStatus.BAD_REQUEST);
        }

        if (product2 != null) {
            return new ResponseEntity<>("Updated.....", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product is not update...", HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {

        Product product2 = productService.getProductById(id);
        
        if (product2 != null) {
                productService.deleteProduct(id);
                return new ResponseEntity<>("Product Deleted...",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("product Is not deleted...",HttpStatus.NOT_FOUND);
        }

        
    }
}
