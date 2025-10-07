package com.dto.postDto;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponseUpdate(Long idPost,String username, String testo,
		List<String> url,Long count, boolean isLike,String fotoProfilo, Long idProfilo, LocalDateTime data) {

}
