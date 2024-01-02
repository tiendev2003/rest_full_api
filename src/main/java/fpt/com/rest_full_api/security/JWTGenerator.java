package fpt.com.rest_full_api.security;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

//import java.security.KeyPair;
import io.jsonwebtoken.Claims;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JWTGenerator {
	// private static final KeyPair keyPair =
	// Keys.keyPairFor(SignatureAlgorithm.RS256);
	private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

	public String generateToken(Authentication authentication) {

		String token = Jwts.builder()

				.issuedAt(new Date())
				.expiration(new Date(new Date().getTime() + 86400000))
				.signWith(key)
				.claim("email", authentication.getName())
				.compact();
		System.out.println("New token :");
		System.out.println(token);
		return token;
	}

	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
		String email = String.valueOf(claims.get("email"));

		return email;
	}

	public String getEmailFromJwtToken(String token) {
		token = token.substring(7);

		Claims claims = Jwts.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload();
		String email = String.valueOf(claims.get("email"));

		return email;
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser()
					.verifyWith(key)
					.build()
					.parseSignedClaims(token)
					.getPayload();
			return true;
		} catch (Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",
					ex.fillInStackTrace());
		}
	}

}
