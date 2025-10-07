package com.dto.messagePrivate;

import java.time.LocalDateTime;

public record MessagePrivateDto(String message,Long idMittente, String usernameDestinatario, LocalDateTime data) {

}
