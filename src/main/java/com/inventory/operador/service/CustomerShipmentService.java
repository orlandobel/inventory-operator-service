package com.inventory.operador.service;

import com.inventory.operador.dto.CustomerShipmentDTO;
import com.inventory.operador.dto.CustomerShipmentItemDTO;
import com.inventory.operador.entity.CustomerShipment;
import com.inventory.operador.entity.CustomerShipmentItem;
import com.inventory.operador.entity.ShipmentStatus;
import com.inventory.operador.repository.CustomerShipmentRepository;
import com.inventory.operador.client.ProductClient;
import com.inventory.operador.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerShipmentService {

    @Autowired
    private CustomerShipmentRepository customerShipmentRepository;

    @Autowired
    private ProductClient productClient;

    public List<CustomerShipmentDTO> getAllCustomerShipments() {
        return customerShipmentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private CustomerShipmentDTO mapToDTO(CustomerShipment shipment) {
        CustomerShipmentDTO dto = new CustomerShipmentDTO();
        dto.setId(shipment.getId());
        dto.setCustomerName(shipment.getCustomerName());
        dto.setCustomerEmail(shipment.getCustomerEmail());
        dto.setStatus(shipment.getStatus().name().toLowerCase());
        dto.setShipmentDate(shipment.getShipmentDate());
        dto.setTrackingNumber(shipment.getTrackingNumber());
        dto.setTotalAmount(shipment.getTotalAmount());
        dto.setItems(
                shipment.getItems().stream().map(item -> {
                    CustomerShipmentItemDTO i = new CustomerShipmentItemDTO();
                    i.setProductId(item.getProductId());
                    i.setQuantity(item.getQuantity());
                    i.setUnitPrice(item.getUnitPrice());
                    return i;
                }).toList()
        );
        return dto;
    }


    public CustomerShipment getCustomerShipmentById(String id) {
        return customerShipmentRepository.findById(id).orElse(null);
    }

    public List<CustomerShipment> getCustomerShipmentsByEmail(String email) {
        return customerShipmentRepository.findByCustomerEmail(email);
    }

    public List<CustomerShipment> getCustomerShipmentsByStatus(ShipmentStatus status) {
        return customerShipmentRepository.findByStatus(status);
    }

    @Transactional
    public CustomerShipment createCustomerShipment(CustomerShipmentDTO dto) {
        CustomerShipment shipment = new CustomerShipment();
        shipment.setCustomerName(dto.getCustomerName());
        shipment.setCustomerEmail(dto.getCustomerEmail());

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<CustomerShipmentItem> items = new ArrayList<>();

        for (CustomerShipmentItemDTO itemDTO : dto.getItems()) {
            // Validar producto
            ProductDTO product = productClient.getProductById(itemDTO.getProductId());
            if (product == null) {
                throw new RuntimeException("Product not found: " + itemDTO.getProductId());
            }
            if (product.getCurrentStock() < itemDTO.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName() +
                        ". Available: " + product.getCurrentStock() +
                        ", Required: " + itemDTO.getQuantity());
            }

            // Reservar stock
            int newStock = product.getCurrentStock() - itemDTO.getQuantity();
            productClient.updateStock(itemDTO.getProductId(), newStock);

            // Crear item de envío
            CustomerShipmentItem item = new CustomerShipmentItem();
            item.setProductId(itemDTO.getProductId());
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(itemDTO.getUnitPrice());
            item.setCustomerShipment(shipment);

            items.add(item);

            // Acumular total
            totalAmount = totalAmount.add(itemDTO.getUnitPrice().multiply(new BigDecimal(itemDTO.getQuantity())));
        }

        shipment.setItems(items);
        shipment.setTotalAmount(totalAmount);
        shipment.setTrackingNumber("TN-" + System.currentTimeMillis());
        shipment.setStatus(ShipmentStatus.PREPARING);
        shipment.setShipmentDate(LocalDateTime.now());

        return customerShipmentRepository.save(shipment);
    }


    @Transactional
    public CustomerShipment updateCustomerShipment(String id, CustomerShipment shipment) {
        if (customerShipmentRepository.existsById(id)) {
            shipment.setId(id);
            for (CustomerShipmentItem item : shipment.getItems()) {
                item.setCustomerShipment(shipment);
            }
            return customerShipmentRepository.save(shipment);
        }
        return null;
    }

    @Transactional
    public CustomerShipment updateShipmentStatus(String id, ShipmentStatus status) {
        CustomerShipment shipment = customerShipmentRepository.findById(id).orElse(null);
        if (shipment != null) {
            shipment.setStatus(status);
            return customerShipmentRepository.save(shipment);
        }
        return null;
    }

    public boolean deleteCustomerShipment(String id) {
        if (customerShipmentRepository.existsById(id)) {
            customerShipmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
