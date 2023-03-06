package com.brandon.rest.mapper;

import com.brandon.data.AnalyticsModel;
import com.brandon.rest.model.AnalyticsAction;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AnalyticsMapper {

    public AnalyticsModel mapToAnalyticsModel(AnalyticsAction analyticsAction, String ipAddress, String sessionId, String userAgent) {
        AnalyticsModel model = new AnalyticsModel();
        model.setIpAddress(ipAddress);

        if(sessionId != null && sessionId.length() > 100) {
            sessionId = sessionId.substring(0, 100);
        }
        model.setSessionId(sessionId);

        if(userAgent != null && userAgent.length() > 2499) {
            userAgent = userAgent.substring(0, 2499);
        }
        model.setUserAgent(userAgent);

        model.setType(analyticsAction.getType());
        model.setCreatedDate(new Date());
        return model;
    }


}
