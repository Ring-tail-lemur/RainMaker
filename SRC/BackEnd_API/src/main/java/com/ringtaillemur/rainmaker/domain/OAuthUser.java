package com.ringtaillemur.rainmaker.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.json.JSONObject;

import com.ringtaillemur.rainmaker.domain.enumtype.OauthUserLevel;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "oauth_user")
public class OAuthUser extends BaseEntity {

	@Id
	@Column(name = "user_remote_id")
	private Long userRemoteId;
	private String name;
	private String oauthToken;
	private String url;

	@Enumerated(value = EnumType.STRING)
	OauthUserLevel userLevel;

	@OneToMany(mappedBy = "oAuthUser", cascade = CascadeType.PERSIST)
	private List<OAuthUserRepositoryTable> OAuthUserRepositoryTables = new ArrayList<>();

	@Builder
	public OAuthUser(Long id, String name, String url, String token, OauthUserLevel inputUserLevel) {
		this.userRemoteId = id;
		this.name = name;
		this.oauthToken = token;
		this.url = url;
		this.userLevel = inputUserLevel;
	}

	public OAuthUser(String userInfoLine, String oAuthToken) {
		JSONObject userInfoJSONObject = new JSONObject(userInfoLine);
		userRemoteId = userInfoJSONObject.getLong("id");
		name = userInfoJSONObject.getString("login");
		url = userInfoJSONObject.getString("url");
		oauthToken = oAuthToken;
		userLevel = OauthUserLevel.FIRST_AUTH_USER;
	}

	public OAuthUser update(String oauthToken) {
		this.oauthToken = oauthToken;
		return this;
	}

	public void setOAuthUserRepositoryTables(List<OAuthUserRepositoryTable> OAuthUserRepositoryTables) {
		OAuthUserRepositoryTables.stream()
			.filter(table -> !getOAuthUserRepositoryTables().contains(table))
			.forEach(this::addOAuthUserRepositoryTable);
	}

	public void addOAuthUserRepositoryTable(OAuthUserRepositoryTable oAuthUserRepositoryTable) {
		oAuthUserRepositoryTable.setOAuthUser(this);
	}

}
