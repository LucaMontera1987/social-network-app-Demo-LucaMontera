package com.dto.commentoDto;

import java.time.LocalDateTime;

public record CommentoResponseDto(Long idCommento,String username, String testo,String urlFoto, LocalDateTime data ) {

}
