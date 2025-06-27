package io.eddvance.production.lowcarb.consumption;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("consumption_history")
public class ConsumptionHistory {

    @Id
    private Long id;

    private String email;
    private Double consumption;
    private Double carbonRate;
    private Double greenRate;
    private Double totalCost;
    private LocalDateTime consumptionTime;

    public ConsumptionHistory() {
    }

    public ConsumptionHistory(String email, Double consumption, Double carbonRate, Double greenRate, Double totalCost) {
        this.email = email;
        this.consumption = consumption;
        this.carbonRate = carbonRate;
        this.greenRate = greenRate;
        this.totalCost = totalCost;
        this.consumptionTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }

    public Double getCarbonRate() {
        return carbonRate;
    }

    public void setCarbonRate(Double carbonRate) {
        this.carbonRate = carbonRate;
    }

    public Double getGreenRate() {
        return greenRate;
    }

    public void setGreenRate(Double greenRate) {
        this.greenRate = greenRate;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDateTime getConsumptionTime() {
        return consumptionTime;
    }

    public void setConsumptionTime(LocalDateTime consumptionTime) {
        this.consumptionTime = consumptionTime;
    }
}
