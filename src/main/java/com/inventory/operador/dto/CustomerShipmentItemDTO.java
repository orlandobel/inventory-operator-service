// CustomerShipmentItemDTO.java
package com.inventory.operador.dto;

import java.math.BigDecimal;

public class CustomerShipmentItemDTO {
    private String productId;
    private Integer quantity;
    private BigDecimal unitPrice;

    // Getters y Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}
