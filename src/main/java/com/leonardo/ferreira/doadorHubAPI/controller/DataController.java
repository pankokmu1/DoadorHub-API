package com.leonardo.ferreira.doadorHubAPI.controller;


import com.leonardo.ferreira.doadorHubAPI.model.InputData;
import com.leonardo.ferreira.doadorHubAPI.model.ProcessedData;
import com.leonardo.ferreira.doadorHubAPI.service.DataProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class DataController {
    private final DataProcessingService service;

    public DataController(DataProcessingService service) {
        this.service = service;
    }

    @PostMapping("/data/{deviceId}")
    public CompletableFuture<ResponseEntity<String>> receiveData(
            @PathVariable String deviceId,
            @RequestBody InputData inputData) {
        return service.storeData(deviceId, inputData)
                .thenApply(data -> ResponseEntity.ok("Data received and is being processed asynchronously."));
    }

    @GetMapping("/processed-data/{deviceId}")
    public ResponseEntity<ProcessedData> getProcessedData(@PathVariable String deviceId) {
        ProcessedData processedData = service.getProcessedData(deviceId);
        return processedData != null ? ResponseEntity.ok(processedData) : ResponseEntity.notFound().build();
    }
}
