package com.kob.botrunningsystem.service.impl.utils;

import com.kob.botrunningsystem.utils.BotInterface;
import lombok.extern.slf4j.Slf4j;
import org.joor.Reflect;

import java.util.UUID;

@Slf4j
public class Consumer extends Thread {

    private Bot bot;

    public void startTimeout(long timeout, Bot bot) {
        this.bot = bot;
        this.start();

        try {
            this.join(timeout); // 线程等待timeout毫秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.interrupt(); // 线程中断
        }
    }

    private String addUid(String code, String uid) { // 在code中的Bot类名后添加uid
        int k = code.indexOf(" implements com.kob.botrunningsystem.utils.BotInterface");
        return code.substring(0, k) + uid + code.substring(k);
    }

    @Override
    public void run() {
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString().substring(0, 8);

        log.info("Reflect.compile(name: \n{}, code: \n{})", "com.kob.botrunningsystem.utils.Bot" + uid, addUid(bot.getBotCode(), uid));

        BotInterface botInterface = Reflect.compile(
                "com.kob.botrunningsystem.utils.Bot" + uid,
                addUid(bot.getBotCode(), uid)
        ).create().get();

//        BotInterface botInterface = Reflect.compile(
//                "com.kob.botrunningsystem.utils.Bot",
//                bot.getBotCode()
//        ).create().get();
//        如果尝试编译不加uid的代码，它居然编译本地的com.kob.botrunningsystem.utils.Bot文件，而不编译bot.getBotCode()
//        因为bot.getBotCode()返回值的值域在[0, 3]，特意把本地com.kob.botrunningsystem.utils.Bot的返回值改为100，下面log.info输出100
//        如果尝试编译加uid的代码，则之间说Reflect.compile返回值为null，ai说应该是编译错误的原因

        Integer direction = botInterface.nextMove(bot.getInput());
        log.info("userId: {}, move-direction: {}", bot.getUserId(), direction);
    }
}
