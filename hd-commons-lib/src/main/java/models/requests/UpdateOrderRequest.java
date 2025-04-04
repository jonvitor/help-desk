package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public record UpdateOrderRequest(

        @Schema(description = "Requester ID", example = "67ea9dd3b1725434fe8d3140")
        @Size(min = 24, max = 45, message = "Requester ID must be between 24 and 45 characters")
        String requesterId,

        @Schema(description = "Customer ID", example = "67ea9dd3b1725434fe8d3140")
        @Size(min = 24, max = 45, message = "Customer ID must be between 24 and 45 characters")
        String customerId,

        @Schema(description = "Title of order", example = "Fix my computer")
        @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
        String title,

        @Schema(description = "Description of order", example = "My computer is not turning on")
        @Size(min = 10, max = 3000, message = "Description must be between 10 and 3000 characters")
        String description,

        @Schema(description = "Status of order", example = "Open")
        @Size(min = 4, max = 15, message = "Status must be between 4 and 15 characters")
        String status
) {
}
