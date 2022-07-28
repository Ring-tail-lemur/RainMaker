package org.example.functions;

import javax.persistence.*;

@Entity
public class Git_organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long git_organization_id;
    public String name;
    public int remote_identifier;

    public Long getGit_organization_id() {
        return git_organization_id;
    }

    public void setGit_organization_id(Long git_organization_id) {
        this.git_organization_id = git_organization_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRemote_identifier() {
        return remote_identifier;
    }

    public void setRemote_identifier(int remote_identifier) {
        this.remote_identifier = remote_identifier;
    }
}
