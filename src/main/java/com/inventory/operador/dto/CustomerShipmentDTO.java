// CustomerShipmentDTO.java
package com.inventory.operador.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CustomerShipmentDTO {
    private String id;
    private String customerName;
    private String customerEmail;
    private List<CustomerShipmentItemDTO> items;
    private String status;
    private LocalDateTime shipmentDate;
    private String trackingNumber;
    private BigDecimal totalAmount;

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public List<CustomerShipmentItemDTO> getItems() { return items; }
    public void setItems(List<CustomerShipmentItemDTO> items) { this.items = items; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getShipmentDate() { return shipmentDate; }
    public void setShipmentDate(LocalDateTime shipmentDate) { this.shipmentDate = shipmentDate; }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}
