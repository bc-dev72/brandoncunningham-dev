package com.brandon.rest.model;

import lombok.Data;

import java.util.List;

@Data
public class AnalyticsSummary {
    private Integer liveUsers;
    private Integer totalPageLoads;
    private Integer resumeDownloads;

    private List<String> chartLabels;
    private List<Integer> chartValues;
}
