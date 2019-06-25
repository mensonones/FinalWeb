package com.br.product.repository;

import com.br.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product getByName(String name);

    Product findById(Integer id);
}
