package br.com.joao.orderserviceapi.services;

import br.com.joao.orderserviceapi.mapper.OrderMapper;
import br.com.joao.orderserviceapi.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    public void save(CreateOrderRequest request) {
        repository.save(mapper.toEntity(request));
    }
}
