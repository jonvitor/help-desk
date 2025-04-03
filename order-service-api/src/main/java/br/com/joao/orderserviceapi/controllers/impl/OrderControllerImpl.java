package br.com.joao.orderserviceapi.controllers.impl;

import br.com.joao.orderserviceapi.controllers.OrderController;
import br.com.joao.orderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService service;

    @Override
    public ResponseEntity<Void> save(CreateOrderRequest request) {
        return service.save(request);
    }
}
