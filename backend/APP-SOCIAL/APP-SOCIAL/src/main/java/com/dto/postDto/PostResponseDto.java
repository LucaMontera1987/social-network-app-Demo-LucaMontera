package com.dto.postDto;

import java.util.List;

import com.model.Foto;

public record PostResponseDto(Long idPost,String username, String testo,
		List<String> url,Long count, boolean isLike) {

}
