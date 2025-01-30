package com.leonardo.ferreira.doadorHubAPI.service;

import com.leonardo.ferreira.doadorHubAPI.entity.ProcessedDataEntity;
import com.leonardo.ferreira.doadorHubAPI.model.InputData;
import com.leonardo.ferreira.doadorHubAPI.model.ProcessedData;
import com.leonardo.ferreira.doadorHubAPI.model.ImcByAgeRange;
import com.leonardo.ferreira.doadorHubAPI.model.ObesityData;
import com.leonardo.ferreira.doadorHubAPI.model.DonorData;
import com.leonardo.ferreira.doadorHubAPI.repository.ProcessedDataRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class DataProcessingService {
    private final ProcessedDataRepository repository;

    public DataProcessingService(ProcessedDataRepository repository) {
        this.repository = repository;
    }

    @Async
    @CachePut(value = "dataCache", key = "#deviceId")
    public CompletableFuture<ProcessedData> storeData(String deviceId, InputData inputData) {
        ProcessedData processedData = new ProcessedData(
                inputData.totalCandidatesPerState(),
                inputData.imcByAgeRange(),
                inputData.obesityPercentage(),
                inputData.averageAgeByBloodType(),
                inputData.donorsPerBloodType()
        );

        ProcessedDataEntity entity = new ProcessedDataEntity(
                deviceId,
                inputData.totalCandidatesPerState(),
                inputData.imcByAgeRange().stream()
                        .map(range -> range.toString())  // Converter para String
                        .toList(),
                inputData.obesityPercentage().entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString())),
                inputData.averageAgeByBloodType(),
                inputData.donorsPerBloodType().entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().donorsCount()))
        );

        repository.save(entity);
        return CompletableFuture.completedFuture(processedData);
    }

    @Cacheable(value = "dataCache", key = "#deviceId")
    public ProcessedData getProcessedData(String deviceId) {
        return repository.findById(deviceId)
                .map(entity -> new ProcessedData(
                        entity.getTotalCandidatesPerState(),
                        entity.getImcByAgeRange().stream()
                                .map(rangeStr -> {
                                    // Converter de String para ImcByAgeRange
                                    String[] parts = rangeStr.replace("{", "").replace("}", "").split(",");
                                    int minAge = Integer.parseInt(parts[0].split("=")[1]);
                                    int maxAge = Integer.parseInt(parts[1].split("=")[1]);
                                    double avgImc = Double.parseDouble(parts[2].split("=")[1]);
                                    return new ImcByAgeRange(Map.of("min_age", minAge, "max_age", maxAge), avgImc);
                                })
                                .toList(),
                        entity.getObesityPercentage().entrySet().stream()
                                .collect(Collectors.toMap(Map.Entry::getKey, e -> {
                                    String[] parts = e.getValue().replace("{", "").replace("}", "").split(",");
                                    int total = Integer.parseInt(parts[0].split("=")[1]);
                                    int obese = Integer.parseInt(parts[1].split("=")[1]);
                                    double percentage = Double.parseDouble(parts[2].split("=")[1]);
                                    return new ObesityData(total, obese, percentage);
                                })),
                        entity.getAverageAgeByBloodType(),
                        entity.getDonorsPerBloodType().entrySet().stream()
                                .collect(Collectors.toMap(Map.Entry::getKey, e -> new DonorData(e.getValue())))
                ))
                .orElse(null);
    }
}
