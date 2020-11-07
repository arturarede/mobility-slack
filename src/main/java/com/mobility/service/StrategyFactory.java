package com.mobility.service;

import com.mobility.model.entity.StationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class StrategyFactory {
    private Map<StationType, Strategy> strategies;

    @Autowired
    public StrategyFactory(Set<Strategy> strategySet) {
        createStrategy(strategySet);
    }

    public Strategy findStrategy(StationType strategyName) {
        return strategies.get(strategyName);
    }

    private void createStrategy(Set<Strategy> strategySet) {
        strategies = new HashMap<>();
        strategySet.forEach(strategy -> strategies.put(strategy.getStrategyName(), strategy));
    }
}