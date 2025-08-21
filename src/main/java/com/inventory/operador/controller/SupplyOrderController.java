package com.inventory.operador.controller;

import com.inventory.operador.dto.SupplyOrderDTO;
import com.inventory.operador.entity.SupplyOrder;
import com.inventory.operador.entity.OrderStatus;
import com.inventory.operador.service.SupplyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/supply-orders")
@CrossOrigin(origins = "*")
public class SupplyOrderController {

    @Autowired
    private SupplyOrderService supplyOrderService;

    @GetMapping
    public List<SupplyOrderDTO> getAllSupplyOrders() {
        return supplyOrderService.getAllSupplyOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplyOrder> getSupplyOrderById(@PathVariable String id) {
        SupplyOrder order = supplyOrderService.getSupplyOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/supplier/{supplierId}")
    public List<SupplyOrder> getSupplyOrdersBySupplierId(@PathVariable String supplierId) {
        return supplyOrderService.getSupplyOrdersBySupplierId(supplierId);
    }

    @GetMapping("/status/{status}")
    public List<SupplyOrder> getSupplyOrdersByStatus(@PathVariable OrderStatus status) {
        return supplyOrderService.getSupplyOrdersByStatus(status);
    }

    @PostMapping
    public ResponseEntity<SupplyOrder> createSupplyOrder(@RequestBody SupplyOrderDTO dto) {
        try {
            SupplyOrder created = supplyOrderService.createSupplyOrder(dto);
            return ResponseEntity.ok(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<SupplyOrder> updateSupplyOrder(@PathVariable String id, @RequestBody SupplyOrder supplyOrder) {
        SupplyOrder updated = supplyOrderService.updateSupplyOrder(id, supplyOrder);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/receive")
    public ResponseEntity<SupplyOrder> receiveSupplyOrder(@PathVariable String id) {
        try {
            SupplyOrder received = supplyOrderService.receiveSupplyOrder(id);
            if (received != null) {
                return ResponseEntity.ok(received);
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplyOrder(@PathVariable String id) {
        boolean deleted = supplyOrderService.deleteSupplyOrder(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}