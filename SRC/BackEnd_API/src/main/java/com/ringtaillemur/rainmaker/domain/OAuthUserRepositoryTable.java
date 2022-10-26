package com.ringtaillemur.rainmaker.domain;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OAuthUserRepositoryTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oauth_user_id")
	private OAuthUser oAuthUser;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "repository_id")
	private Repository repository;

	public void setOAuthUser(OAuthUser oAuthUser) {
		oAuthUser.getOAuthUserRepositoryTables().add(this);
		this.oAuthUser = oAuthUser;
	}
	
	
	public void removeOAuthUser(OAuthUser oAuthUser) {
		oAuthUser.getOAuthUserRepositoryTables().remove(this);
		this.oAuthUser = oAuthUser;
	}
}
