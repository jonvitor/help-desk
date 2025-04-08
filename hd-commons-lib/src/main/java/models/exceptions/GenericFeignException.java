package models.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class GenericFeignException extends  RuntimeException {

    private final Integer status;
    private final transient Map<String, Object> error;
}
