package com.inventory.operador.repository;

import com.inventory.operador.entity.CustomerShipment;
import com.inventory.operador.entity.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerShipmentRepository extends JpaRepository<CustomerShipment, String> {
    List<CustomerShipment> findByCustomerEmail(String customerEmail);
    List<CustomerShipment> findByStatus(ShipmentStatus status);
    List<CustomerShipment> findByTrackingNumber(String trackingNumber);
}
