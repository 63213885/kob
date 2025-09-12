package com.kob.matchingsystem.service.impl;

import com.kob.matchingsystem.service.MatchingService;
import com.kob.matchingsystem.service.impl.utils.MatchingPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MatchingServiceImpl implements MatchingService {
    public final static MatchingPool matchingPool = new MatchingPool();

    public String addPlayer(Integer userId, Integer rating) {
        log.info("add player: " + userId + " " + rating);
        matchingPool.addPlayer(userId, rating);
        return "success";
    }

    public String removePlayer(Integer userId) {
        log.info("remove player: " + userId);
        matchingPool.removePlayer(userId);
        return "success";
    }
}
