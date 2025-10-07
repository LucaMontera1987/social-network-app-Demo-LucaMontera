package com.dto.userDto;

import java.time.LocalDate;

public record UserResponseDto(Long id,String username,String nome,String cognome,String email,LocalDate dataNascita) {

}
