package com.kob.botrunningsystem.service.impl;

import com.kob.botrunningsystem.service.BotRunningService;
import com.kob.botrunningsystem.service.impl.utils.BotPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BotRunningServiceImpl implements BotRunningService {

    public static final BotPool botPool = new BotPool();

    @Override
    public String addBot(Integer userId, String botCode, String input) {
        log.info("addBot(userId: {}, botCode: {}, input: {})", userId, botCode, input);
        botPool.addBot(userId, botCode, input);
        return "add bot success";
    }
}
