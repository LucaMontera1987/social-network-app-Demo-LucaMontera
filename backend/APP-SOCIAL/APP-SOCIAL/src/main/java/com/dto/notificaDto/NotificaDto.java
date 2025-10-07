package com.dto.notificaDto;

import com.enums.tipoNotifica;

public record NotificaDto( Long id_destinatario, tipoNotifica notifica, Long eventoId) {

}
