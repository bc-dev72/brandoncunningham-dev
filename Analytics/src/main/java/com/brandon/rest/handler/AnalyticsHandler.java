package com.brandon.rest.handler;

import com.brandon.data.AnalyticsModel;
import com.brandon.rest.mapper.AnalyticsMapper;
import com.brandon.rest.model.AnalyticsAction;
import com.brandon.rest.model.AnalyticsSummary;
import com.brandon.rest.repo.AnalyticsRepo;
import com.brandon.util.ActionTypes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class AnalyticsHandler {
    private final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd");

    private AnalyticsRepo analyticsRepo;
    private AnalyticsMapper analyticsMapper;

    public AnalyticsHandler(AnalyticsRepo analyticsRepo, AnalyticsMapper analyticsMapper) {
        this.analyticsRepo = analyticsRepo;
        this.analyticsMapper = analyticsMapper;
    }

    public boolean saveAction(AnalyticsAction analyticsAction, String ipAddress, String sessionId, String userAgent) {
        if(!ActionTypes.VALID_TYPES.contains(analyticsAction.getType())) {
            //invalid action type entered
            return false;
        }

        AnalyticsModel model = analyticsMapper.mapToAnalyticsModel(analyticsAction, ipAddress, sessionId, userAgent);
        analyticsRepo.save(model);

        return true;
    }


    public AnalyticsSummary getCheapSummary() {
        AnalyticsSummary summary = new AnalyticsSummary();
        summary.setLiveUsers(analyticsRepo.countByActiveUsers(new Date(System.currentTimeMillis()-TimeUnit.MINUTES.toMillis(15))).intValue());
        summary.setTotalPageLoads(analyticsRepo.countByUniquePageLoads().intValue());
        summary.setResumeDownloads(analyticsRepo.countByType(ActionTypes.RESUME_DOWNLOAD).intValue());
        return summary;
    }


    @Cacheable("expensiveSummary")
    public AnalyticsSummary getExpensiveSummary() {
        List<AnalyticsModel> historyData = analyticsRepo.findByCreatedDateAfterAndType(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(32)), ActionTypes.PAGE_LOAD);
        Map<String, Integer> mappedDailyCount = new HashMap<>();
        for(AnalyticsModel model : historyData) {
            String key = dateTimeFormatter.format(model.getCreatedDate());
            mappedDailyCount.put(key, mappedDailyCount.getOrDefault(key, 0)+1);
        }

        List<String> labels = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();

        for(int i = 30; i >= 0; i--) {
            String key = dateTimeFormatter.format(new Date(System.currentTimeMillis()-TimeUnit.DAYS.toMillis(i)));
            labels.add(key.split("-", 2)[1]);
            counts.add(mappedDailyCount.getOrDefault(key, 0));
        }

        AnalyticsSummary summary = new AnalyticsSummary();
        summary.setChartLabels(labels);
        summary.setChartValues(counts);
        return summary;
    }


}
