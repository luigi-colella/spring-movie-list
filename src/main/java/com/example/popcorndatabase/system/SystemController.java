package com.example.popcorndatabase.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Controller used to handle not domain specific requests.
 */
@Controller
public class SystemController {

    /**
     * Show homepage.
     */
    @GetMapping("")
    public String showHomepage() {
        return "redirect:/movie";
    }

    /**
     * Return GIT info.
     */
    @GetMapping("/info")
    @ResponseBody
    public Map<String, String> getInfo(
            @Value("${git.branch}") String branch,
            @Value("${git.commit.id}") String commitId,
            @Value("${git.commit.message.full}") String commitMessage
    ) {
        Map<String, String> result = new LinkedHashMap<>();
        result.put("branch", branch);
        result.put("commit_id", commitId);
        result.put("commit_message", commitMessage);
        return result;
    }
}
