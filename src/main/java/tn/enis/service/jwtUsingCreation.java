package tn.enis.service;

import org.springframework.stereotype.Component;
import java.util.Base64;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class jwtUsingCreation{
	String cleSecret = Base64.getEncoder().encodeToString(
		    Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded()
		);	public String genererToken(String username) {
		String jwttoken=Jwts.builder()
				.setSubject(username)
				.signWith(SignatureAlgorithm.HS256, cleSecret)
				.compact();
		return jwttoken;
	}
	public boolean validerToken(String token) {
		try{
			Jwts.parser().setSigningKey(cleSecret)
				.parseClaimsJws(token)  ;
		   return true;}
		catch(Exception e) {
			return false;
		}

}
	public String getUsernameFromToken(String token) { // <-- NOUVELLE MÉTHODE
        Claims claims = Jwts.parser()
                .setSigningKey(cleSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
	}
