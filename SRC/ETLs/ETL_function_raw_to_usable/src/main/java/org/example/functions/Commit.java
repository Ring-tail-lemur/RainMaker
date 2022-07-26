package org.example.functions;

// github action test 14
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Commit {

  @Id
  private Long commit_id;

  private Long commit_num;

  public Long getCommit_id() {
    return commit_id;
  }

  public void setCommit_id(Long commit_id) {
    this.commit_id = commit_id;
  }

  public Long getCommit_num() {
    return commit_num;
  }

  public void setCommit_num(Long commit_num) {
    this.commit_num = commit_num;
  }
}
