package com.dto.profiloAmico;

public record ProfiloAmicoDto(Long idProfilo,String username,String fotoCopertina,
		String fotoProfilo, boolean myProfile,  boolean profileAmico, Long idProfileMyProfile ) {

}
