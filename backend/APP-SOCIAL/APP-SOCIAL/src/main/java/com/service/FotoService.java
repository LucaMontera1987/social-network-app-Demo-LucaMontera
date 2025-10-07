package com.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.CLoudinaryConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.model.Foto;
import com.model.Post;
import com.model.Profilo;
import com.repository.FotoRepo;
import com.repository.ProfiloRepo;

@Service
public class FotoService {

	private final Cloudinary cloudinary;
	private final CLoudinaryConfig cloudinaryConfig;
	private final FotoRepo fotoRepo;
	private final ProfiloRepo profiloRepo;

	

@Autowired
	public FotoService(Cloudinary cloudinary, CLoudinaryConfig cloudinaryConfig, FotoRepo fotoRepo,
			ProfiloRepo profiloRepo) {
		this.cloudinary = cloudinary;
		this.cloudinaryConfig = cloudinaryConfig;
		this.fotoRepo = fotoRepo;
		this.profiloRepo = profiloRepo;
	}

	//genera firma per upload in cloud se vuoi fare upload direttamente da angular
	public Map<String, Object> signature() {

		Long timestamp = System.currentTimeMillis() / 1000;

		Map<String, Object> paramsToSign = ObjectUtils.asMap("timestamp", timestamp, "folder", "social/posts"
		// cambiare
		// cartella
		);

		String signature = cloudinary.apiSignRequest(paramsToSign, cloudinaryConfig.getApiSecret());

		Map<String, Object> response = new HashMap<>();
		response.put("timestamp", timestamp);
		response.put("signature", signature);
		response.put("apiKey", cloudinaryConfig.getApiKey());
		response.put("cloudName", cloudinaryConfig.getCloudName());
		response.put("folder", "social/posts");

		return response;

	}

	public List<Foto> uploadFotoPost(List<MultipartFile> file, Post post) throws IOException {

		Map<String, Object> params = ObjectUtils.asMap(
				"folder",
				"social/posts");
		
		List<Foto> foto_secure_url=new ArrayList<Foto>();

		for(MultipartFile fileTmp:file) {
		Map uploadResult = cloudinary.uploader().upload(fileTmp.getBytes(), params);
		
		String secureUrl = (String) uploadResult.get("secure_url");
		Foto fotoTmp=new Foto();
		fotoTmp.setUrl(secureUrl);
		foto_secure_url.add(fotoTmp);
		}
		
		return foto_secure_url;
		
	}
	
	public Foto uploadFotoProfilo(MultipartFile file, Profilo profilo) throws IOException {
		String uuid=UUID.randomUUID().toString();
		
		Map<String, Object> params = ObjectUtils.asMap(
				"folder", 
				"social/profilo",
				"public_id", "foto" + uuid,
				 "overwrite", true,
				    "invalidate", true
				);

		
		Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
		
		String secureUrl = (String) uploadResult.get("secure_url");
		
		Foto fotoTmp=new Foto();
		fotoTmp.setUrl(secureUrl);
		fotoTmp.setProfilo(profilo);
		fotoRepo.save(fotoTmp);
		
	
		return fotoTmp;
			
	}
	
	
	
	public byte[] sendFotoByteProfilo(String publicId) {

	//	String imageUrl = cloudinary.url().secure(true).resourceType("image").generate("social/profilo/" + publicId);
		String imageUrl=publicId;
		try {
			InputStream inputStream = new URL(imageUrl).openStream();
			return inputStream.readAllBytes();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	

	public byte[] sendFotoBytePost(String publicId) {

		String imageUrl = cloudinary.url().secure(true).resourceType("image").generate("social/posts/" + publicId);

		try {
			InputStream inputStream = new URL(imageUrl).openStream();
			return inputStream.readAllBytes();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	
	public List<String> getFotoByIdPost(Long idPost){
		
		List<String> fotoTmp=fotoRepo.loadFotoPost(idPost);
		return fotoTmp;
		
	}
}
