package com.example.hotdealmoa.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotdealmoa.order.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderCustomRepository {
}
