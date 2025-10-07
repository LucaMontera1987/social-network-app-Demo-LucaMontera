package com.dto.amiciziaDto;

import com.enums.StatoAmicizia;

public record AmicoResponseDto(Long idUser, Long idProfilo, StatoAmicizia statoAmicizia) {

}
