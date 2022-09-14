package com.ringtaillemur.rainmaker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ringtaillemur.rainmaker.domain.enumtype.OauthUserLevel;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Setter
@NoArgsConstructor
@Table(name = "oauth_user")
public class OAuthUser extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@Column(name = "user_remote_id")
	Long userRemoteId;
	String name;
	String oauthToken;
	String url;
	@Enumerated(value = EnumType.STRING)
	OauthUserLevel user_level;

	@Builder
	public OAuthUser(Long id, String name, String url, String token, OauthUserLevel inputUserLevel) {
		this.userRemoteId = id;
		this.name = name;
		this.oauthToken = token;
		this.url = url;
		this.user_level = inputUserLevel;
	}

	public OAuthUser update(String oauthToken) {
		this.oauthToken = oauthToken;
		return this;
	}

}
