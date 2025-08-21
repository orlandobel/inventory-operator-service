package com.inventory.operador.service;

import com.inventory.operador.dto.SupplyOrderItemDTO;
import com.inventory.operador.entity.SupplyOrder;
import com.inventory.operador.entity.SupplyOrderItem;
import com.inventory.operador.entity.OrderStatus;
import com.inventory.operador.repository.SupplyOrderRepository;
import com.inventory.operador.client.ProductClient;
import com.inventory.operador.dto.ProductDTO;
import com.inventory.operador.dto.SupplyOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplyOrderService {

    @Autowired
    private SupplyOrderRepository supplyOrderRepository;

    @Autowired
    private ProductClient productClient;

    public List<SupplyOrderDTO> getAllSupplyOrders() {
        return supplyOrderRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private SupplyOrderDTO mapToDTO(SupplyOrder entity) {
        SupplyOrderDTO dto = new SupplyOrderDTO();
        dto.setId(entity.getId());
        dto.setSupplierId(entity.getSupplierId());
        dto.setStatus(entity.getStatus().name().toLowerCase());
        dto.setOrderDate(entity.getOrderDate());
        dto.setExpectedDelivery(entity.getExpectedDelivery());
        dto.setActualDelivery(entity.getActualDelivery());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setNotes(entity.getNotes());

        dto.setItems(entity.getItems().stream().map(item -> {
            SupplyOrderItemDTO i = new SupplyOrderItemDTO();
            i.setProductId(item.getProductId());
            i.setQuantity(item.getQuantity());
            i.setUnitPrice(item.getUnitPrice());
            i.setReceivedQuantity(item.getReceivedQuantity());
            return i;
        }).toList());

        return dto;
    }

    public SupplyOrder getSupplyOrderById(String id) {
        return supplyOrderRepository.findById(id).orElse(null);
    }

    public List<SupplyOrder> getSupplyOrdersBySupplierId(String supplierId) {
        return supplyOrderRepository.findBySupplierId(supplierId);
    }

    public List<SupplyOrder> getSupplyOrdersByStatus(OrderStatus status) {
        return supplyOrderRepository.findByStatus(status);
    }

    @Transactional
    public SupplyOrder createSupplyOrder(SupplyOrderDTO dto) {
        SupplyOrder order = new SupplyOrder();

        order.setId(dto.getId());
        order.setSupplierId(dto.getSupplierId());
        order.setStatus(OrderStatus.valueOf(dto.getStatus().toUpperCase()));
        order.setOrderDate(dto.getOrderDate());
        order.setExpectedDelivery(dto.getExpectedDelivery());
        order.setActualDelivery(dto.getActualDelivery());
        order.setTotalAmount(dto.getTotalAmount());
        order.setNotes(dto.getNotes());

        // Map items
        if (dto.getItems() != null) {
            List<SupplyOrderItem> items = dto.getItems().stream().map(i -> {
                SupplyOrderItem item = new SupplyOrderItem();
                item.setProductId(i.getProductId());
                item.setQuantity(i.getQuantity());
                item.setUnitPrice(i.getUnitPrice());
                item.setReceivedQuantity(i.getReceivedQuantity());
                return item;
            }).collect(Collectors.toList());
            order.setItems(items);
        }

        return supplyOrderRepository.save(order);
    }

    @Transactional
    public SupplyOrder updateSupplyOrder(String id, SupplyOrder supplyOrder) {
        if (supplyOrderRepository.existsById(id)) {
            supplyOrder.setId(id);
            for (SupplyOrderItem item : supplyOrder.getItems()) {
                item.setSupplyOrder(supplyOrder);
            }
            return supplyOrderRepository.save(supplyOrder);
        }
        return null;
    }

    @Transactional
    public SupplyOrder receiveSupplyOrder(String id) {
        SupplyOrder order = supplyOrderRepository.findById(id).orElse(null);
        if (order == null) return null;

        order.setStatus(OrderStatus.RECEIVED);
        order.setActualDelivery(LocalDateTime.now());

        // Update stock for each item
        for (SupplyOrderItem item : order.getItems()) {
            try {
                ProductDTO product = productClient.getProductById(item.getProductId());
                if (product != null) {
                    int newStock = product.getCurrentStock() + (item.getReceivedQuantity() != null ?
                            item.getReceivedQuantity() : item.getQuantity());
                    productClient.updateStock(item.getProductId(), newStock);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error updating stock for product: " + item.getProductId(), e);
            }
        }

        return supplyOrderRepository.save(order);
    }

    public boolean deleteSupplyOrder(String id) {
        if (supplyOrderRepository.existsById(id)) {
            supplyOrderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}