package com.sangeng.job;

import com.sangeng.domain.entity.Article;
import com.sangeng.mapper.ArticleMapper;
import com.sangeng.service.ArticleService;
import com.sangeng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.ws.Action;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: 甘廿甘廿
 * @create: 2023-10-24 19:05
 * @Description:
 */
@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/55 * * * * ?")
    public void TestJob(){
        // 从redis中获取浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
        List<Article> articleList = viewCountMap.entrySet().stream().map(entry -> {
            return new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue());
        }).collect(Collectors.toList());
        // 更新到数据库中
        articleService.updateBatchById(articleList);
    }
}
