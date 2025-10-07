package com.dto.exceptionDto;

import java.time.LocalDateTime;

public record ExceptionDto(int status, String error, String message, LocalDateTime data, String path) {

}
