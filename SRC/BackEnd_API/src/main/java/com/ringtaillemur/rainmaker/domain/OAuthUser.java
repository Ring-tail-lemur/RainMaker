package com.ringtaillemur.rainmaker.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class OAuthUser extends BaseEntity{
    @Id
    Long id;
    String name;
    String html_url;
    String oauth_token;
    String email;

    @Column(nullable = true)
    String created_at;

    @Column(nullable = true)
    String updated_at;

    @Builder
    public OAuthUser(Long id, String name, String html_url, String token, String email){
        this.id = id;
        this.name = name;
        this.oauth_token = token;
        this.html_url = html_url;
        this.email = email;
    }

    public OAuthUser update(String oauth_token){
        this.oauth_token = oauth_token;
        return this;
    }

}
