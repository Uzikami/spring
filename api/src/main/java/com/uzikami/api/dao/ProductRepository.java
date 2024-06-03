package com.uzikami.api.dao;

import com.uzikami.api.entity.Product;
import com.uzikami.api.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
