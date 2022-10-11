package com.ringtaillemur.rainmaker.domain.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OauthUserLevel {

	FIRST_AUTH_USER,
	AUTH_NOT_REPOSITORY_SELECT,
	AUTHED_HISTORY_COLLECT_NOT_ENDED_USER,
	AUTHED_HISTORY_COLLECT_ENDED_USER,
	AUTHED_FREE_USER,
	AUTHED_NOT_PAID_USER,
	AUTHED_PAID_SIZE_ONE_USER,
	AUTHED_PAID_SIZE_TWO_USER,
	ADMIN,
	FAILED,
}
