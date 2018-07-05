package com.truechain.task.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AppProperties {

    public static String TOKEN_HEADER;

    public static String AGENT_HEADER;

    public static String UPLOAD_FILE_PATH;

    @PostConstruct
    public void init() {
        TOKEN_HEADER = tokenHeader;
        AGENT_HEADER = agentHeader;
        UPLOAD_FILE_PATH = uploadFilePath;
    }

    @Value("${app.token.header}")
    private String tokenHeader;

    @Value("${app.agent.header}")
    private String agentHeader;

    @Value("${app.upload.file-path}")
    private String uploadFilePath;
}
