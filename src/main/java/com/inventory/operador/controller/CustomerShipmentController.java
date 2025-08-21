package com.inventory.operador.controller;

import com.inventory.operador.dto.CustomerShipmentDTO;
import com.inventory.operador.entity.CustomerShipment;
import com.inventory.operador.entity.ShipmentStatus;
import com.inventory.operador.service.CustomerShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@CrossOrigin(origins = "*")
public class CustomerShipmentController {

    @Autowired
    private CustomerShipmentService customerShipmentService;

    @GetMapping
    public List<CustomerShipmentDTO> getAllCustomerShipments() {
        return customerShipmentService.getAllCustomerShipments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerShipment> getCustomerShipmentById(@PathVariable String id) {
        CustomerShipment shipment = customerShipmentService.getCustomerShipmentById(id);
        if (shipment != null) {
            return ResponseEntity.ok(shipment);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/customer/{email}")
    public List<CustomerShipment> getCustomerShipmentsByEmail(@PathVariable String email) {
        return customerShipmentService.getCustomerShipmentsByEmail(email);
    }

    @GetMapping("/status/{status}")
    public List<CustomerShipment> getCustomerShipmentsByStatus(@PathVariable ShipmentStatus status) {
        return customerShipmentService.getCustomerShipmentsByStatus(status);
    }

    @PostMapping
    public ResponseEntity<Void> createCustomerShipment(@RequestBody CustomerShipmentDTO dto) {
        try {
            customerShipmentService.createCustomerShipment(dto);
            return ResponseEntity.ok().build(); // solo indicamos éxito
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // error
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<CustomerShipment> updateCustomerShipment(@PathVariable String id, @RequestBody CustomerShipment shipment) {
        CustomerShipment updated = customerShipmentService.updateCustomerShipment(id, shipment);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<CustomerShipment> updateShipmentStatus(@PathVariable String id, @RequestParam ShipmentStatus status) {
        CustomerShipment updated = customerShipmentService.updateShipmentStatus(id, status);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerShipment(@PathVariable String id) {
        boolean deleted = customerShipmentService.deleteCustomerShipment(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}