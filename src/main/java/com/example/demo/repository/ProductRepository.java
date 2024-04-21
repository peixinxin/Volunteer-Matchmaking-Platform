package com.example.demo.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

    List<Product> findByName(String name);
}
