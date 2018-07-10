package com.truechain.task.admin;

import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import javaslang.Function2;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class Swagger2Makedown {
    public static void main(String[] orgs){
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .build();
        try {
            Swagger2MarkupConverter.from(new URL("http://127.0.0.1:8080/v2/api-docs"))
                    .withConfig(config)
                    .build()
                    .toFolder(Paths.get("src/docs/asciidoc/generated"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String a = "1";
    }
}
