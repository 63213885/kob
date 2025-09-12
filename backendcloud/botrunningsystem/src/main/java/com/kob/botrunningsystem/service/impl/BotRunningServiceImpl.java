package com.kob.botrunningsystem.service.impl;

import com.kob.botrunningsystem.service.BotRunningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BotRunningServiceImpl implements BotRunningService {

    @Override
    public String addBot(Integer userId, String botCode, String input) {
        log.info("add bot: " + userId + " " + botCode + " " + input);
        return "add bot success";
    }
}
