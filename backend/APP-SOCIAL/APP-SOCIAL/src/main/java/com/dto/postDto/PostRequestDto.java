package com.dto.postDto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public record PostRequestDto(String testo, String username ) {

}
