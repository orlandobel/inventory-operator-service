package com.inventory.operador.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SupplyOrderDTO {

    private String id;
    private String supplierId;
    private String status;
    private LocalDateTime orderDate;
    private LocalDateTime expectedDelivery;
    private LocalDateTime actualDelivery;
    private BigDecimal totalAmount;
    private String notes;
    private List<SupplyOrderItemDTO> items;

    public SupplyOrderDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getExpectedDelivery() {
        return expectedDelivery;
    }

    public void setExpectedDelivery(LocalDateTime expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
    }

    public LocalDateTime getActualDelivery() {
        return actualDelivery;
    }

    public void setActualDelivery(LocalDateTime actualDelivery) {
        this.actualDelivery = actualDelivery;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<SupplyOrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<SupplyOrderItemDTO> items) {
        this.items = items;
    }
}
