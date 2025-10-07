package com.security;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;


@Configuration
public class JwtConfig {
	
	@Value("${jwt.private.key}")
	private String privateKey;
	
	@Value("${jwt.public.key}")
	private String publicKeyJwt;
	//	
	//	# Genera la chiave privata (RSA 2048 bit)
	//	openssl genrsa -out private.pem 2048
	//
	//	# Genera la chiave pubblica dalla chiave privata
	//	openssl rsa -in private.pem -pubout -out public.pem

	private RSAPrivateKey loadPrivateKey(String privateKey) throws Exception {
		String key = new String(Files.readAllBytes(new File(privateKey).toPath())).replace("-----BEGIN PRIVATE KEY-----", "")
				.replace("-----END PRIVATE KEY-----", "").replaceAll("\\s", "");
		byte[] keyBytes = Base64.getDecoder().decode(key);

		return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
	}

	
	private RSAPublicKey loadPublicKey(String publicKey) throws Exception {
		String key = new String(Files.readAllBytes(new File(publicKey).toPath())).replace("-----BEGIN PUBLIC KEY-----", "")
				.replace("-----END PUBLIC KEY-----", "").replaceAll("\\s", "");

		byte[] keyBytes = Base64.getDecoder().decode(key);

		return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(keyBytes));

	}

	@Bean
	public JwtEncoder jwtEncoder() throws Exception {
		RSAPrivateKey PrivateKey = loadPrivateKey(privateKey);
		RSAPublicKey PublicKey = loadPublicKey(publicKeyJwt);

		JWK jwk = new RSAKey.Builder(PublicKey).privateKey(PrivateKey).keyID("rsa-key").build();
		return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));

	}

	@Bean
	public JwtDecoder jwtDecoder() throws Exception {
		
		RSAPublicKey publicKey = loadPublicKey(publicKeyJwt);
		return NimbusJwtDecoder.withPublicKey(publicKey).build();

	}

}
