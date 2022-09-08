package com.ringtaillemur.rainmaker.domain;

import com.ringtaillemur.rainmaker.domain.enumtype.OauthUserLevel;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Setter
@NoArgsConstructor
@Table(name = "oauth_user")
public class OAuthUser extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "user_remote_id")
    Long userRemoteId;
    String name;
    String oauthToken;
    String url;
    OauthUserLevel user_level;
    @Builder
    public OAuthUser(Long id, String name, String url, String token){
        this.userRemoteId = id;
        this.name = name;
        this.oauthToken = token;
        this.url = url;
    }

    public OAuthUser update(String oauth_token){
        this.oauthToken = oauth_token;
        return this;
    }


}
