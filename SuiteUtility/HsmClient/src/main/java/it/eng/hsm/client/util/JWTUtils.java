package it.eng.hsm.client.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JWTUtils {

    public static final long JWT_TOKEN_VALIDITY_IN_SECONDS = 60L * 60 * 24; // 24 ora

//    public String getUsernameFromToken(String token) throws NoSuchAlgorithmException, Exception {
//        return getClaimFromToken(token, Claims::getSubject);
//    }

//    public Date getExpirationDateFromToken(String token) throws NoSuchAlgorithmException, Exception {
//        return getClaimFromToken(token, Claims::getExpiration);
//    }

//    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws NoSuchAlgorithmException, Exception {
//        final var claims = getAllClaimsFromToken(token);
//        return claimsResolver.apply(claims);
//    }

    public static String generateToken(PrivateKey secret, Certificate cert, String alias, String cfg, 
    		String urlAud, String appCode) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("aud", urlAud);
        claims.put("client_id", appCode);
        claims.put("app_code", appCode);
        claims.put("iat", new Date(System.currentTimeMillis()));
        claims.put("nbf", new Date(System.currentTimeMillis()));
        claims.put("iss", alias);
        claims.put("user_cf","");
        claims.put("cfg", cfg);
        
        return doGenerateToken(claims, secret, cert, alias);
    }

//	public static boolean validateToken(String token, String username, PublicKey publicKey) {
//		try {
//			final String usernameIntoToken = getUsernameFromToken(token);
//			return (usernameIntoToken.equals(username) && !isTokenExpired(token));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		Jws<Claims> jwt = Jwts.parser().setSigningKey(publicKey).build().parseClaimsJws(token);
//
//		JwsHeader header = jwt.getHeader();
//		System.out.println(header.toString());
//
//		Claims payload = jwt.getPayload();
//		System.out.println(payload.toString());
//		return true;
//	}

//	public boolean validateToken(String token) {
//		try {
//			return !isTokenExpired(token);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}

//    private Claims getAllClaimsFromToken(String token) throws NoSuchAlgorithmException, Exception {
//    	byte[] decodedBytes = Base64.getDecoder().decode(secret);
//    	X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(decodedBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(keySpecX509);
//        
//        return Jwts.parser().setSigningKey(pubKey).parseClaimsJws(token).getBody();
//    }

//    private Boolean isTokenExpired(String token) throws NoSuchAlgorithmException, Exception {
//        final var expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }

    private static String doGenerateToken(Map<String, Object> claims, PrivateKey secret, Certificate cert, String alias) {
    	String tokenUuid = UUID.randomUUID().toString();
		return Jwts.builder()
				.header()
				.add("typ","JWT")
				.add("x5c", cert)
				.and()
				.claims(claims)
				.subject(alias)
				.issuedAt(new Date(System.currentTimeMillis()))
		        .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY_IN_SECONDS * 1000))
		        .id(tokenUuid)
		        .signWith( secret)
		        .compact();
    }
    
    public PublicKey generateJwtKeyDecryption(String jwtPublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] keyBytes = Base64.getDecoder().decode(jwtPublicKey);
        X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(keyBytes);
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }
    
    public PrivateKey generateJwtKeyEncryption(String jwtPrivateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] keyBytes = Base64.getDecoder().decode(jwtPrivateKey);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(keyBytes);
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

}