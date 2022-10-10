package com.ringtaillemur.rainmaker.dto.securitydto;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.ringtaillemur.rainmaker.dto.securitydto.LoginUser;

import lombok.Data;

@Data
@Component
public class SessionMemory {
	public HashMap<String, LoginUser> loginUserHashMap = new HashMap<>();
}
