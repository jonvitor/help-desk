package br.com.joao.orderserviceapi.services;

import br.com.joao.orderserviceapi.entities.Order;
import br.com.joao.orderserviceapi.mapper.OrderMapper;
import br.com.joao.orderserviceapi.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.valueOf;

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

    public Page<OrderResponse> findAllPageable(int page, int size, String orderBy, String direction) {
        PageRequest request = PageRequest.of(
                page,
                size,
                valueOf(direction),
                orderBy
        );

        return repository.findAll(request)
                .map(mapper::fromEntity);
    }
}
