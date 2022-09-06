package com.ringtaillemur.rainmaker.controller.maindashboard.api;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.service.UserConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserConfigController {


    private final HttpSession session;

    private final UserConfigService userConfigService;

    @ResponseBody
    @GetMapping("/RepositorySelect")
    public ArrayList<UserRepositoryDto> userRepositoryListReturnRestAPI() {

        // todo : UserId의 경우는 세션을 통해 알아올 것이고, token의 경우는 이 유저아이디를 통해 DB에서 빼올것.
        String userId = "11979390";
        String token = "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";

        System.out.println(session.toString());
        return userConfigService.getUserRepositoryDtoByToken(token);
    }

    @PostMapping("/RepositorySelect")
    public String userRepositoryRegisterRestAPI(@RequestParam(name="repo_id") List<String> RepoInfos) {

        String token = "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";

        for(var repo : RepoInfos) {
            String[] repoArr = repo.split(","); // Id, Organ, Repo, Time 순.
            userConfigService.setUserWebhookByRepoName(token, repoArr[1], repoArr[2]);
        }

        // todo: repoId, token, repo Name, owner Name (Organization Name), 이걸로 azure function을 호출.
        return "redirect:http://localhost:3000/";
    }
}