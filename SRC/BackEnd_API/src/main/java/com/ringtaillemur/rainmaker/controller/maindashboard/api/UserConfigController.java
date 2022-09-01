package com.ringtaillemur.rainmaker.controller.maindashboard.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserConfigController {
    @ResponseBody
    @GetMapping("/RepositorySelect")
    public String userRepositoryListReturnRestAPI() {

        // todo : UserId의 경우는 세션을 통해 알아올 것이고, token의 경우는 이 유저아이디를 통해 DB에서 빼올것.
        String userId = "11979390";
        String token = "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";

        return "";
    }

    @PostMapping("/RepositorySelect")
    public String userRepositoryRegisterRestAPI(@RequestParam(name="repo_id") List<String> repoIds) {
        System.out.println(repoIds);
        return "redirect:http://localhost:3000/";
    }
}
