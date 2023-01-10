package com.example.hotdealmoa.review.repository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hotdealmoa.review.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {

	List<Review> findAllByProductId(Long id);

	Optional<Review> findByOrderId(Long id);

}
