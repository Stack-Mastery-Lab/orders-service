package com.relatandopapel.Repository;

import com.relatandopapel.Repository.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Integer> {
    List<Order> findByOwnerId(Integer ownerId);
}
