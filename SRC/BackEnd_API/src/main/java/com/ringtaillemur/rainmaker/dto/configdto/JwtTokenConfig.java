package com.ringtaillemur.rainmaker.dto.configdto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class JwtTokenConfig {
	@Value("${spring.security.jwt.secret}")
	public String jwtSecret;
	@Value("${spring.security.jwt.authorities_key}")
	public String jwtAuthoritiesKey;
}
