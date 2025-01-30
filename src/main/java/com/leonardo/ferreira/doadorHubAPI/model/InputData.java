package com.leonardo.ferreira.doadorHubAPI.model;

import java.util.Map;
import java.util.List;

public record InputData(
        Map<String, Integer> totalCandidatesPerState,
        List<ImcByAgeRange> imcByAgeRange,
        Map<String, ObesityData> obesityPercentage,
        Map<String, Double> averageAgeByBloodType,
        Map<String, DonorData> donorsPerBloodType
) {}
