package com.inventory.operador.repository;

import com.inventory.operador.entity.SupplyOrder;
import com.inventory.operador.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SupplyOrderRepository extends JpaRepository<SupplyOrder, String> {
    List<SupplyOrder> findBySupplierId(String supplierId);
    List<SupplyOrder> findByStatus(OrderStatus status);
    List<SupplyOrder> findBySupplierIdAndStatus(String supplierId, OrderStatus status);
}