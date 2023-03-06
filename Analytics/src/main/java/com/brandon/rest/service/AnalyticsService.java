package com.brandon.rest.service;

import com.brandon.rest.handler.AnalyticsHandler;
import com.brandon.rest.model.AnalyticsAction;
import com.brandon.rest.model.AnalyticsSummary;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    private AnalyticsHandler analyticsHandler;

    public AnalyticsService(AnalyticsHandler analyticsHandler) {
        this.analyticsHandler = analyticsHandler;
    }

    public boolean saveAction(AnalyticsAction analyticsAction, String ipAddress, String sessionId, String userAgent) {
        return analyticsHandler.saveAction(analyticsAction, ipAddress, sessionId, userAgent);
    }

    @Cacheable("expensiveSummary")
    public AnalyticsSummary getExpensiveSummary() {
        return analyticsHandler.getExpensiveSummary();
    }

    public AnalyticsSummary fillInLiveData(AnalyticsSummary currentSummary) {
        if(currentSummary == null)
            currentSummary = new AnalyticsSummary();
        AnalyticsSummary cheapSummary = analyticsHandler.getCheapSummary();
        currentSummary.setLiveUsers(cheapSummary.getLiveUsers());
        currentSummary.setTotalPageLoads(cheapSummary.getTotalPageLoads());
        currentSummary.setResumeDownloads(cheapSummary.getResumeDownloads());
        return currentSummary;
    }
}
