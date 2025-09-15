package com.kob.backend.service.ranklist;

import com.alibaba.fastjson.JSONObject;

public interface GetRanklistService {
    public JSONObject getList(Integer page);
}
