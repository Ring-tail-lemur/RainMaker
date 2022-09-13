package com.ringtaillemur.rainmaker.controller.maindashboard.api;

import com.ringtaillemur.rainmaker.dto.webdto.responsedto.RegisterRepoIdDto;
import com.ringtaillemur.rainmaker.dto.webdto.responsedto.UserRepositoryDto;
import com.ringtaillemur.rainmaker.service.UserConfigService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        return userConfigService.getUserRepositoryDtoByToken(userConfigService.getToken(userConfigService.getUserId()));
    }

    @PostMapping("/RepositorySelect")
    public String userRepositoryRegisterRestAPI(@RequestBody RegisterRepoIdDto repoIds) {

        userConfigService.registerRepository(repoIds);
        return "redirect:http://localhost:3000/";
    }
}

/*
todo 웹훅 등록
* curl -H "Authorization: Bearer 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" -i https://api.github.com/hub -F "hub.mode=subscribe" -F "hub.topic=https://github.com/Ring-tail-lemur/test-for-fake-project/events/push" -F "hub.callback=https://webhook.site/3d6e59ed-e609-434a-bf0c-52cd3c563062"
* curl -H "Authorization: Bearer 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" -i https://api.github.com/hub -F "hub.mode=subscribe" -F "hub.topic=https://github.com/Ring-tail-lemur/test-for-fake-project/events/pull_request" -F "hub.callback=https://webhook.site/3d6e59ed-e609-434a-bf0c-52cd3c563062"
*
* */
