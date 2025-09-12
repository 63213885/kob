package com.kob.matchingsystem.service.impl.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
public class MatchingPool extends Thread {
    private static List<Player> players = new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock();
    private static RestTemplate restTemplate;

    private final static String startGameUrl = "http://127.0.0.1:3000/pk/start/game/";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        MatchingPool.restTemplate = restTemplate;
    }

    public void addPlayer(Integer userId, Integer rating) {
        lock.lock();
        try {
            players.add(new Player(userId, rating, 0));
        } finally {
            lock.unlock();
        }
    }
    public void removePlayer(Integer userId) {
        lock.lock();
        try {
            for (Player player : players) {
                if (player.getUserId().equals(userId)) {
                    players.remove(player);
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private void increaseWaitingTime() {
        for (Player player : players) {
            player.setWaitingTime(player.getWaitingTime() + 1);
        }
    }
    private boolean checkMatched(Player a, Player b) { // 检查两名玩家是否匹配
        int ratingDelta = Math.abs(a.getRating() - b.getRating());
        int waitingTime = Math.min(a.getWaitingTime(), b.getWaitingTime());
        return ratingDelta <= waitingTime * 10;
    }
    private void sendResult(Player a, Player b) { // 发送匹配结果
        log.info("sending result: " + a.getUserId() + " " + b.getUserId());
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("a_id", a.getUserId().toString());
        data.add("b_id", b.getUserId().toString());
        restTemplate.postForObject(startGameUrl, data, String.class);
    }
    private void matchPlayers() { // 尝试匹配玩家
        log.info("matching players: " + players.toString());
        boolean[] used = new boolean[players.size()];
        for (int i = 0; i < players.size(); i++) {
            if (used[i]) continue;
            for (int j = i + 1; j < players.size(); j++) {
                if (used[j]) continue;
                if (checkMatched(players.get(i), players.get(j))) {
                    used[i] = used[j] = true;
                    sendResult(players.get(i), players.get(j));
                    break;
                }
            }
        }

        List<Player> newPlayers = new LinkedList<>();
        for (int i = 0; i < players.size(); i++) {
            if (!used[i]) newPlayers.add(players.get(i));
        }
        players = newPlayers;
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                lock.lock();
                try {
                    increaseWaitingTime();
                    matchPlayers();
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
