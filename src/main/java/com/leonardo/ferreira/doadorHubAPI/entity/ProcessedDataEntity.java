package com.leonardo.ferreira.doadorHubAPI.entity;

import jakarta.persistence.*;
import java.util.Map;
import java.util.List;

@Entity
@Table(name = "processed_data")
public class ProcessedDataEntity {
    @Id
    private String deviceId;

    @ElementCollection
    private Map<String, Integer> totalCandidatesPerState;

    @ElementCollection
    private List<String> imcByAgeRange;

    @ElementCollection
    private Map<String, String> obesityPercentage;

    @ElementCollection
    private Map<String, Double> averageAgeByBloodType;

    @ElementCollection
    private Map<String, Integer> donorsPerBloodType;

    public ProcessedDataEntity() {}

    public ProcessedDataEntity(String deviceId, Map<String, Integer> totalCandidatesPerState,
                               List<String> imcByAgeRange, Map<String, String> obesityPercentage,
                               Map<String, Double> averageAgeByBloodType, Map<String, Integer> donorsPerBloodType) {
        this.deviceId = deviceId;
        this.totalCandidatesPerState = totalCandidatesPerState;
        this.imcByAgeRange = imcByAgeRange;
        this.obesityPercentage = obesityPercentage;
        this.averageAgeByBloodType = averageAgeByBloodType;
        this.donorsPerBloodType = donorsPerBloodType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Map<String, Integer> getTotalCandidatesPerState() {
        return totalCandidatesPerState;
    }

    public List<String> getImcByAgeRange() {
        return imcByAgeRange;
    }

    public Map<String, String> getObesityPercentage() {
        return obesityPercentage;
    }

    public Map<String, Double> getAverageAgeByBloodType() {
        return averageAgeByBloodType;
    }

    public Map<String, Integer> getDonorsPerBloodType() {
        return donorsPerBloodType;
    }
}
