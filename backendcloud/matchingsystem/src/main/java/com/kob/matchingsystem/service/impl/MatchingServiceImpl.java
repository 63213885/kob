package com.kob.matchingsystem.service.impl;

import com.kob.matchingsystem.service.MatchingService;
import com.kob.matchingsystem.service.impl.utils.MatchingPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MatchingServiceImpl implements MatchingService {
    public final static MatchingPool matchingPool = new MatchingPool();

    public String addPlayer(Integer userId, Integer rating, Integer botId) {
        log.info("add player: " + userId + " " + rating + " " + botId);
        matchingPool.addPlayer(userId, rating, botId);
        return "success";
    }

    public String removePlayer(Integer userId) {
        log.info("remove player: " + userId);
        matchingPool.removePlayer(userId);
        return "success";
    }
}
