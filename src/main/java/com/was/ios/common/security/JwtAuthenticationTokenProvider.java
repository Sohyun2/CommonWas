package com.was.ios.common.security;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtAuthenticationTokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenProvider.class);

	private static final SignatureAlgorithm TOKEN_TYPE = SignatureAlgorithm.HS256;
    private static final String SECRET_KEY = "HaeundaeBeachInBusan_SouthKorea";
    private static final String DATA_KEY = "userInfo";
    private static final long EXPIRATION_MS = 1000 * 60 * 60 * 24;
    
	public String createToken(JSONObject jsonObject) {
		long curTime = System.currentTimeMillis();
		
		return Jwts.builder().setHeaderParam("typ", "JWT")
							.claim(DATA_KEY, jsonObject)
							.setIssuedAt(new Date(curTime)) // 발급시각
							.setExpiration(new Date(curTime + EXPIRATION_MS)) // 만료시각
							.signWith(TOKEN_TYPE, this.getSecretKey()) // 복호화할 때 사용하는 signature설정
							.compact();
	}
	
	private byte[] getSecretKey() {
		byte[] key = null;
		try {
			key = this.SECRET_KEY.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
        	logger.error("Making secret Key Error :: ", e);
        }
		return key;
	}
	
	public JSONObject decodingToken(String token) {
		JSONObject jsonObj = null;
		
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(this.getSecretKey())
											.parseClaimsJws(token);			
			jsonObj = new ObjectMapper().convertValue(claims.getBody().get(DATA_KEY), JSONObject.class);
		}
		catch(Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		return jsonObj;
	}
}
