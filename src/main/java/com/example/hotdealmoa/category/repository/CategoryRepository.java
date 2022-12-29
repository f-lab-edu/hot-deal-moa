package com.example.hotdealmoa.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotdealmoa.category.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
