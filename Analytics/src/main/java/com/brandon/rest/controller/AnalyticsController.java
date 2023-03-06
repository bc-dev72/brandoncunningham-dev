package com.brandon.rest.controller;

import com.brandon.rest.model.AnalyticsAction;
import com.brandon.rest.service.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController extends BaseController {
    private AnalyticsService analyticsService;

    public AnalyticsController(HttpServletRequest httpServletRequest, AnalyticsService analyticsService) {
        super(httpServletRequest);
        this.analyticsService = analyticsService;
    }

    @PostMapping
    public ResponseEntity action(
            @RequestBody AnalyticsAction analyticsAction) {
        boolean success = analyticsService.saveAction(analyticsAction, getIpHeader(), getSessionId(), getUserAgent());
        return ResponseEntity.status(success ? 201 : 200).body(null);
    }

    @GetMapping
    public ResponseEntity getSummary(
            @RequestParam(value = "type", required = false) String type) {
        if(type != null && type.equals("lite")) {
            return ResponseEntity.ok(analyticsService.fillInLiveData(null));
        } else {
            return ResponseEntity.ok(analyticsService.fillInLiveData(analyticsService.getExpensiveSummary()));
        }
    }
}