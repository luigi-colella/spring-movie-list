package com.example.popcorndatabase.info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Controller used to retrieve app's info
 */
@RestController
public class InfoController {

    @GetMapping("/info")
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
