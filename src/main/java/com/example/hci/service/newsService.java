package com.example.hci.service;

import com.example.hci.model.History;
import com.example.hci.model.HistoryId;
import com.example.hci.model.News;
import com.example.hci.model.User;
import com.example.hci.repository.historyRepository;
import com.example.hci.repository.newsRepository;
import com.example.hci.repository.userRepository;
import com.example.hci.result.Result;
import com.example.hci.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class newsService {
    @Autowired
    newsRepository newsRepo;
    @Autowired
    userRepository userRepo;
    @Autowired
    historyRepository historyRepo;

    public Result readNews(String user_id,String id){
        User user = null;
        News news = null;
        if(newsRepo.existsById(id)){
            news = newsRepo.findById(id).get();
        }
        if(userRepo.existsById(user_id)){
            user = userRepo.findById(user_id).get();
        }
        if(news!=null) {
            news.setCount(news.getCount() + 1);
            newsRepo.save(news);
            Map<String,Object> result= new HashMap<>();
            result.put("content",news.getContent());
            result.put("title",news.getTitle());
            result.put("date",news.getDate().toString());
            result.put("url",news.getUrl());
            result.put("id",news.getId());
            result.put("part",news.getPart());
            result.put("count",news.getCount());
            if(user!=null){
                HistoryId historyId = new HistoryId();
                historyId.setNewsId(id);
                historyId.setUserId(user_id);
                History history=new History();
                if(historyRepo.existsById(historyId)){
                    history = historyRepo.findById(historyId).get();
                }else{
                    history.setId(historyId);
                }
                history.setDate(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));
                historyRepo.save(history);
            }
            return ResultFactory.buildSuccessResult(result);
        }
        return ResultFactory.buildFailResult("新闻获取失败");
    }

    public void publishNews(String title,String part,String content,String url){
        News news = new News();
        news.setCount(0);
        news.setContent(content);
        news.setPart(part);
        news.setDate(Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8)));
        news.setTitle(title);
        news.setUrl(url);
        news.setId(generateID(32));
        newsRepo.save(news);
    }

    public StringBuilder tryGetID(int length) {
        StringBuilder id=new StringBuilder();
        Random rd = new SecureRandom();
        for(int i=0;i<length;i++){
            int bit = rd.nextInt(10);
            id.append(bit);
        }
        return id;
    }

    public String generateID(int length) {
        while(true)
        {
            StringBuilder id=tryGetID(length);
            if(!newsRepo.existsById(id.toString())) return id.toString();
        }
    }
}
