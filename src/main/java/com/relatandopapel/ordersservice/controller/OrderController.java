package com.relatandopapel.ordersservice.controller;

import com.relatandopapel.ordersservice.controller.model.CreateOrderRequestDto;
import com.relatandopapel.ordersservice.controller.model.CreateOrderResponseDto;
import com.relatandopapel.ordersservice.controller.model.GetOrdersResponseDto;
import com.relatandopapel.ordersservice.service.CreateOrdersService;
//import com.relatandopapel.ordersservice.service.GetOrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController                         // ← Era @Service, estaba mal
@RequestMapping("/api/v1/")             // ← Faltaba esta anotación
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrdersService createOrdersService;
//    private final GetOrdersService getOrdersService;

    // GET /api/v1/orders?owner_id=1
//    @GetMapping("orders")
//    public ResponseEntity<GetOrdersResponseDto> getRecentOrders(
//            @RequestParam("owner_id") Integer ownerId) {
//        return ResponseEntity.ok(getOrdersService.getRecentOrders(ownerId));
//    }

    // POST /api/v1/orders
    @PostMapping("orders")
    public ResponseEntity<CreateOrderResponseDto> createOrder(
            @Valid @RequestBody CreateOrderRequestDto request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createOrdersService.createOrder(request));
    }
}