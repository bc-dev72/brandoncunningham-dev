package com.brandon.rest.controller;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {
    private HttpServletRequest httpServletRequest;

    public BaseController(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public String getIpHeader() {
        return httpServletRequest.getHeader("X-Real-IP"); //passed through nginx
    }

    public String getSessionId() {
        return httpServletRequest.getHeader("sessionId");
    }

    public String getUserAgent() {
        if(httpServletRequest.getHeader("user-agent") != null)
            return httpServletRequest.getHeader("user-agent");

        if(httpServletRequest.getHeader("User-Agent") != null)
            return httpServletRequest.getHeader("User-Agent");

        return null;
    }
}
