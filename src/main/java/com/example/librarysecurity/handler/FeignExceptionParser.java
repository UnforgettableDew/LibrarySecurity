package com.example.librarysecurity.handler;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FeignExceptionParser {
    private final Pattern pattern = Pattern.compile("\\[(.*)\\]: \\[(.*)\\]");
    private final Pattern jsonPattern = Pattern.compile("\\{\\\"message\\\":\\\"(.*)\\\",\\\"httpStatus\\\":\\\"(.*)\\\",\\\"timestamp\\\":\\\"(.*)\\\"\\}");

    private Map<String, Object> response;
    private void parse(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String status = matcher.group(1);
            String json = matcher.group(2);

            Matcher jsonMatcher = jsonPattern.matcher(json);
            if (jsonMatcher.find()) {
                String message = jsonMatcher.group(1);
                String httpStatus = jsonMatcher.group(2);
                String timestamp = jsonMatcher.group(3);

                response = new HashMap<>();
                response.put("message", message);
                response.put("httpStatus", httpStatus);
                response.put("timestamp", timestamp);

            }
        }
    }
    public Map<String, Object> getResponse(String exceptionMessage){
        parse(exceptionMessage);
        return response;
    }
}