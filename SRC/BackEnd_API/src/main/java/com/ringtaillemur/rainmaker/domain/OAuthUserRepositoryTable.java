package com.ringtaillemur.rainmaker.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OAuthUserRepositoryTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oauth_user_id")
	private OAuthUser oAuthUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "repository_id")
	private Repository repository;
}
