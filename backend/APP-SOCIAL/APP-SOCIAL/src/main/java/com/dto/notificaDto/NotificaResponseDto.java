package com.dto.notificaDto;

import java.io.Serializable;

import com.enums.tipoNotifica;

public record NotificaResponseDto
(String usernameMittente, String contenuto, String urlFoto,
		Long idEvento, tipoNotifica tipo,
		String userDestinatario,Long idProfilo, Long idPost) implements Serializable {

}
