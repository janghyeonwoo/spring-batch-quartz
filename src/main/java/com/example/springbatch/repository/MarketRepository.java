package com.example.springbatch.repository;

import com.example.springbatch.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market,Long> {
}
