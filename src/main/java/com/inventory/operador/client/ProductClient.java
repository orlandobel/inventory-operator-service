package com.inventory.operador.client;

import com.inventory.operador.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "buscador-service")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ProductDTO getProductById(@PathVariable("id") String id);

    @PutMapping("/api/products/{id}/stock")
    ProductDTO updateStock(@PathVariable("id") String id, @RequestParam("newStock") Integer newStock);
}
