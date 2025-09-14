package com.kob.botrunningsystem.utils;

import java.util.ArrayList;
import java.util.List;

public class Bot implements com.kob.botrunningsystem.utils.BotInterface {

    static class Cell {
        public int x, y;
        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private boolean check_tail_increasing(int step) { // 检测当前回合，蛇的长度是否增加
        if (step <= 10) return true;
        return step % 3 == 1;
    }

    public List<Cell> getCells(int sx, int sy, String steps) {
        System.out.println("steps = " + steps);
        steps = steps.substring(1, steps.length() - 1);
        List<Cell> res = new ArrayList<>();
        int[][] to = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int x = sx, y = sy;
        res.add(new Cell(sx, sy));
        int step = 0;
        for (int i = 0; i < steps.length(); i++) {
            int d = steps.charAt(i) - '0';
            x += to[d][0];
            y += to[d][1];
            res.add(new Cell(x, y));
            if (!check_tail_increasing(++step)) {
                res.remove(0);
            }
        }
        return res;
    }

    @Override
    public Integer nextMove(String input) {
        String[] strs = input.split("#");
        int[][] g = new int[13][14];
        for (int i = 0, k = 0; i < 13; i++) {
            for (int j = 0; j < 14; j++, k++) {
                if (strs[0].charAt(k) == '1') {
                    g[i][j] = 1;
                }
            }
        }

        int aSx = Integer.parseInt(strs[1]), aSy = Integer.parseInt(strs[2]);
        int bSx = Integer.parseInt(strs[4]), bSy = Integer.parseInt(strs[5]);

        List<Cell> aCells = getCells(aSx, aSy, strs[3]);
        List<Cell> bCells = getCells(bSx, bSy, strs[6]);
        for (Cell cell : aCells) {
            g[cell.x][cell.y] = 1;
        }
        for (Cell cell : bCells) {
            g[cell.x][cell.y] = 1;
        }

        int[][] to = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        System.out.println("aCells.size() = " + aCells.size());
        for (int i = 0; i < 4; i++) {
            int x = aCells.get(aCells.size() - 1).x + to[i][0];
            int y = aCells.get(aCells.size() - 1).y + to[i][1];
            if (x >= 0 && x < 13 && y >= 0 && y < 14 && g[x][y] == 0) {
                return i;
            }
        }

        return 0;
    }
}
