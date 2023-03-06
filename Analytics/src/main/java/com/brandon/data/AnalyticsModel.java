package com.brandon.data;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ANALYTICS")
@Data
public class AnalyticsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "A_ID")
    private Long id;

    @Column(name = "A_TYPE")
    private String type;

    @Column(name = "A_IP")
    private String ipAddress;

    @Column(name = "A_SESSION")
    private String sessionId;

    @Column(name = "A_USER")
    private String userAgent;

    @Column(name = "A_CRTD_DATE")
    private Date createdDate;

}
