package br.com.joao.orderserviceapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import models.exceptions.StandardError;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "OrderController", description = "Controller responsible for operations")
@RequestMapping("/api/orders")
public interface OrderController {

    @Operation(summary = "Save new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created"),
            @ApiResponse(
                    responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            ),
    })
    @PostMapping
    ResponseEntity<Void> save(@Valid @RequestBody final CreateOrderRequest request);

    @Operation(summary = "Update new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order Updated",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrderResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            ),
    })
    @PutMapping("/{id}")
    ResponseEntity<OrderResponse> update(
            @Parameter(description = "Order id", required = true)
            @PathVariable final Long id,
            @Valid @RequestBody final UpdateOrderRequest request);

    @Operation(summary = "Find order by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "User found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = OrderResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "User not found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            ),
    })
    @GetMapping("/{id}")
    ResponseEntity<OrderResponse> findById(
            @Parameter(description = "Order id", required = true, example = "5")
            @PathVariable final Long id
    );

    @Operation(summary = "Find order paginated")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Orders found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = OrderResponse.class)))
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            ),
    })
    @GetMapping()
    ResponseEntity<List<OrderResponse>> findAll();

    @Operation(summary = "Find orders paginated")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Orders found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = OrderResponse.class)))
            ),
            @ApiResponse(
                    responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            ),
    })
    @GetMapping("/page")
    ResponseEntity<Page<OrderResponse>> findAllPageable(
            @Parameter(description = "Page number", required = true, example = "0")
            @RequestParam(value = "page", defaultValue = "0") int page,

            @Parameter(description = "Page size", required = true, example = "10")
            @RequestParam(value = "size", defaultValue = "10") int size,

            @Parameter(description = "Order by", required = true, example = "id")
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,

            @Parameter(description = "Order direction", required = true, example = "ASC")
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    );
}
