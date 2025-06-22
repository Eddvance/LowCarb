package io.eddvance.production.lowcarb.ratecollector.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "rate_history")
public class RateRecord {

    @Id
    private Long id;

    private Double rate;

    private LocalDateTime rateTime;

    public RateRecord(Double rate, LocalDateTime rateTime) {
        this.rate = rate;
        this.rateTime = rateTime;
    }

    public RateRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public LocalDateTime getRateTime() {
        return rateTime;
    }

    public void setRateTime(LocalDateTime rateTime) {
        this.rateTime = rateTime;
    }
}
