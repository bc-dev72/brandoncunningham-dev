package com.brandon.rest.repo;

import com.brandon.data.AnalyticsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AnalyticsRepo extends JpaRepository<AnalyticsModel, Long> {
    public Long countByType(String type);
    public List<AnalyticsModel> findByCreatedDateAfterAndType(Date date, String type);

    @Query("SELECT COUNT(DISTINCT ipAddress) FROM AnalyticsModel")
    public Long countByUniquePageLoads();


    @Query("SELECT COUNT(DISTINCT ipAddress) FROM AnalyticsModel WHERE createdDate >= ?1")
    public Long countByActiveUsers(Date date);
}
