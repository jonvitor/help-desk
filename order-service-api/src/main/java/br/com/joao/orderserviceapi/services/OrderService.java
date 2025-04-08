package br.com.joao.orderserviceapi.services;

import br.com.joao.orderserviceapi.clients.UserServiceFeignClient;
import br.com.joao.orderserviceapi.entities.Order;
import br.com.joao.orderserviceapi.mapper.OrderMapper;
import br.com.joao.orderserviceapi.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import models.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.valueOf;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final UserServiceFeignClient client;

    public void save(CreateOrderRequest request) {
        final var requester = validateUser(request.requesterId());
        final var customer = validateUser(request.customerId());

        log.info("Requester {} and Customer {} found", requester, customer);

        repository.save(mapper.toEntity(request));
    }

    public OrderResponse update(Long id, UpdateOrderRequest request) {
        var order = find(id);
        validateUsers(request);

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

    private void validateUsers(UpdateOrderRequest request) {
        if (request.requesterId() != null) {
            validateUser(request.requesterId());
        }

        if (request.customerId() != null) {
            validateUser(request.customerId());
        }
    }

    private UserResponse validateUser(String userId) {
        final var user = client.findById(userId).getBody();
        log.info("User found: {}", user);

        return user;
    }
}
