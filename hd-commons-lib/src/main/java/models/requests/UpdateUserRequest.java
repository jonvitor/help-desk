package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.With;
import models.enums.ProfileEnum;

import java.util.Set;

@With
public record UpdateUserRequest(
    @Schema(description = "User name", example = "Vitor")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    String name,

    @Schema(description = "User email", example = "vitor@mail.com")
    @Size(min = 6, max = 50, message = "Email must be between 6 and 50 characters")
    String email,

    @Schema(description = "User password", example = "123456")
    @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
    String password,

    @Schema(description = "User profiles", example = "[\"PROFILE_ADMIN\"]")
    Set<ProfileEnum> profiles
) { }
