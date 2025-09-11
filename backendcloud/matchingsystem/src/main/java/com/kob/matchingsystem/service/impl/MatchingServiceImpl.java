package com.kob.matchingsystem.service.impl;

import com.kob.matchingsystem.service.MatchingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MatchingServiceImpl implements MatchingService {
    public String addPlayer(Integer userId, Integer rating) {
        log.info("add player: " + userId + " " + rating);
        return "success";
    }

    public String removePlayer(Integer userId) {
        log.info("remove player: " + userId);
        return "success";
    }
}
