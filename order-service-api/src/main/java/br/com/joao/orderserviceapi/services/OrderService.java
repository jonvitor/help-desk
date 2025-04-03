package br.com.joao.orderserviceapi.services;

import br.com.joao.orderserviceapi.mapper.OrderMapper;
import br.com.joao.orderserviceapi.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    public ResponseEntity<Void> save(CreateOrderRequest request) {
        repository.save(mapper.toEntity(request));
        return ResponseEntity.ok().build();
    }
}
