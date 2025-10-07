package com.restController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.CLoudinaryConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.repository.FotoRepo;
import com.service.FotoService;

@RestController
@RequestMapping("/api/foto")
public class FotoUploadSignatureController {

	private final Cloudinary cloudinary;
	private final CLoudinaryConfig cloudinaryConfig;
	private final FotoRepo fotoRepo;
	private final FotoService fotoService;

	@Autowired
	public FotoUploadSignatureController(Cloudinary cloudinary, CLoudinaryConfig cloudinaryConfig, FotoRepo fotoRepo,
			FotoService fotoService) {
		this.cloudinary = cloudinary;
		this.cloudinaryConfig = cloudinaryConfig;
		this.fotoRepo = fotoRepo;
		this.fotoService = fotoService;
	}

//firma la richiesta per salavare immagine su cloud da angular
	@GetMapping("/sign-upload")
	public ResponseEntity<Map<String, Object>> getUploadSignature() {
	Map<String, Object> responseSignature=fotoService.signature();

		return ResponseEntity.ok(responseSignature);
	}
	
	
	//SALVA IMMAGINE SU CLOUD
	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException{
	
		Map<String, Object> params= ObjectUtils.asMap(
				
				"folder","social/posts"
				
				);
		
		Map uploadResult=cloudinary.uploader().upload(file.getBytes(), params);
		
		String secureUrl=(String) uploadResult.get("secure_url");
		
		
		
		return ResponseEntity.ok(secureUrl);
	}

	//https://api.cloudinary.com/v1_1/dagumzxln/image/upload
	
//	file	L'immagine da caricare
//	api_key	818327316976951 (dal backend)
//	timestamp	1754674751 (dal backend)
//	signature	875e053239fac639f8f62341db502f94a3737954 (dal backend)
//	folder	social/posts (dal backend)
	
	@GetMapping("{publicId:.+}")
	public ResponseEntity<byte[]> getFotoPostId(@PathVariable String publicId){
		
			
			 byte[] fotoDownload=fotoService.sendFotoBytePost(publicId);
			 
		System.out.println("FOTO BYTE " + fotoDownload) ;
			 String contentType = "image/jpeg"; // Sostituisci con "image/png" se il formato è PNG

			    return ResponseEntity.ok()
			            .header(HttpHeaders.CONTENT_TYPE, contentType)  // Imposta il Content-Type
			            .body(fotoDownload); // Invia i byte dell'immagine
		
	}
	
	
}
