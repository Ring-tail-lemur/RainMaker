package com.ringtaillemur.rainmaker;

import static org.junit.jupiter.api.Assertions.*;

import com.ringtaillemur.rainmaker.domain.GitOrganization;
import com.ringtaillemur.rainmaker.domain.GitUser;
import com.ringtaillemur.rainmaker.repository.GitUserRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RainmakerWebserverApplicationTest {

  @Autowired
  GitUserRepository gitUserRepository;

  @Test
  void test() {
    List<GitUser> all = gitUserRepository.findAll();
    for (GitUser gitUser : all) {
      System.out.println("gitUser = " + gitUser);
      //9
    }
  }
}
