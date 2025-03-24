package models.requests;

import models.enums.ProfileEnum;

import java.util.Set;

public record CreateUserRequest(
    String name,
    String email,
    String password,
    Set<ProfileEnum> profiles
) { }
