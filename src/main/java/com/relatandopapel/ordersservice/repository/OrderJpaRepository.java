package com.relatandopapel.ordersservice.repository;

import com.relatandopapel.ordersservice.repository.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Integer> {
    List<Order> findByOwnerId(Integer ownerId);

    List<Order> findByOwnerIdOrderByOrderDateDesc(Integer ownerId);
}
