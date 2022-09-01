package com.ringtaillemur.rainmaker.controller.maindashboard.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class userConfigController {
    @ResponseBody
    @PostMapping("/RepositorySelect")
    public String userConfig(@RequestParam(name="repo_id") List<String> repoIds) {
        System.out.println(repoIds);
        return "";
    }
}
