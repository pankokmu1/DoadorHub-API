package com.leonardo.ferreira.doadorHubAPI.model;

import java.util.Map;

public record ImcByAgeRange(Map<String, Integer> range, double averageImc) {}
