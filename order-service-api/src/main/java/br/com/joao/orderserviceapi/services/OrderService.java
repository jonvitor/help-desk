package br.com.joao.orderserviceapi.services;

import br.com.joao.orderserviceapi.entities.Order;
import br.com.joao.orderserviceapi.mapper.OrderMapper;
import br.com.joao.orderserviceapi.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;

    public void save(CreateOrderRequest request) {
        repository.save(mapper.toEntity(request));
    }

    public OrderResponse update(Long id, UpdateOrderRequest request) {
        var order = find(id);

        return mapper.fromEntity(
                repository.save(mapper.copyProperties(request, order))
        );
    }

    public OrderResponse findById(Long id) {
        return mapper.fromEntity(find(id));
    }

    private Order find(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Object not found with id: " + id + " and type: " + Order.class.getSimpleName()
        ));
    }

    public List<OrderResponse> findAll() {
        var orders = repository.findAll();

        return orders.stream()
                .map(mapper::fromEntity)
                .toList();
    }
}
