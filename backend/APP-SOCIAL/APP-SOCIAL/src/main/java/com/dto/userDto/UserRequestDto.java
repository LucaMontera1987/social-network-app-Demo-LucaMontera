package com.dto.userDto;

import java.time.LocalDate;

public record UserRequestDto(String username,String nome,String cognome,String password,String email,LocalDate dataNascita) {

}
