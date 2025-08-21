package com.inventory.operador.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customer_shipments")
public class CustomerShipment {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;

    @NotBlank
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Email
    @NotBlank
    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @OneToMany(mappedBy = "customerShipment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CustomerShipmentItem> items;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private ShipmentStatus status;

    @NotNull
    @Column(name = "shipment_date", nullable = false)
    private LocalDateTime shipmentDate;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @NotNull
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    public CustomerShipment() {
        this.shipmentDate = LocalDateTime.now();
        this.status = ShipmentStatus.PREPARING;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public List<CustomerShipmentItem> getItems() { return items; }
    public void setItems(List<CustomerShipmentItem> items) { this.items = items; }

    public ShipmentStatus getStatus() { return status; }
    public void setStatus(ShipmentStatus status) { this.status = status; }

    public LocalDateTime getShipmentDate() { return shipmentDate; }
    public void setShipmentDate(LocalDateTime shipmentDate) { this.shipmentDate = shipmentDate; }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}
