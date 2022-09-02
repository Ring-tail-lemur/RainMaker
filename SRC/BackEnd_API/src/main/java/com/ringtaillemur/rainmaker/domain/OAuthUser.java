package com.ringtaillemur.rainmaker.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "oauth_user")
public class OAuthUser extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "user_remote_id")
    Long user_remote_id;
    String name;
    String oauth_token;
    String url;
    @Builder
    public OAuthUser(Long id, String name, String url, String token){
        this.user_remote_id = id;
        this.name = name;
        this.oauth_token = token;
        this.url = url;
    }

    public OAuthUser update(String oauth_token){
        this.oauth_token = oauth_token;
        return this;
    }
}
