package br.com.joao.orderserviceapi.mapper;

import br.com.joao.orderserviceapi.entities.Order;
import models.requests.CreateOrderRequest;
import models.responses.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
    componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)
public interface OrderMapper {

    OrderResponse fromEntity(final Order entity);

    @Mapping(target = "id", ignore = true)
    Order toEntity(final CreateOrderRequest request);
}
