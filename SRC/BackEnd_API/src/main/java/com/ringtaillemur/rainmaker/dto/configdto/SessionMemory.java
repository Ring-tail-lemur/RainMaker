package com.ringtaillemur.rainmaker.dto.configdto;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.ringtaillemur.rainmaker.config.LoginUser;

import lombok.Data;

@Data
@Component
public class SessionMemory {
	public HashMap<String, LoginUser> loginUserHashMap = new HashMap<>();
}
