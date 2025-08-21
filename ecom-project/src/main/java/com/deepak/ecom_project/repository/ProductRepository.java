package com.deepak.ecom_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deepak.ecom_project.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

}
