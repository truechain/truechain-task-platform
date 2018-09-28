package com.truechain.task.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AppProperties {

    public static String TOKEN_HEADER;

    public static String AGENT_HEADER;

    public static String UPLOAD_FILE_PATH;

    public static String TASK_ICON_PATH;

    public static String TASK_ICON_URL;

    public static String RESUME_URL;

    @PostConstruct
    public void init() {
        TOKEN_HEADER = tokenHeader;
        AGENT_HEADER = agentHeader;
        UPLOAD_FILE_PATH = uploadFilePath;
        TASK_ICON_PATH = taskIconPath;
        TASK_ICON_URL = taskIconUrl;
        RESUME_URL = resumeUrl;
    }

    @Value("${app.token.header}")
    private String tokenHeader;

    @Value("${app.agent.header}")
    private String agentHeader;

    @Value("${app.upload.file-path}")
    private String uploadFilePath;

    @Value("${app.upload.task-icon-path}")
    private String taskIconPath;

    @Value("${app.upload.task-icon-url}")
    private String taskIconUrl;

    @Value("${app.upload.resume-url}")
    private String resumeUrl;
}
