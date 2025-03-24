package br.com.joao.userserviceapi.mapper;

import br.com.joao.userserviceapi.entity.User;
import models.responses.UserReponse;
import org.mapstruct.Mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
    componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS
)
public interface UserMapper {

    UserReponse fromEntity(final User entity);
}
