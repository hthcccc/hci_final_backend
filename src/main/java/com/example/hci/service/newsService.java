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
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class newsService {
    @Autowired
    newsRepository newsRepo;
    @Autowired
    userRepository userRepo;
    @Autowired
    historyRepository historyRepo;

    public Result getOnesInterest(String user_id){
        if(user_id.isEmpty()||!userRepo.existsById(user_id)){
            Sort sort = Sort.sort(News.class).descending();
            sort.getOrderFor("date");
            List<News> newsList=newsRepo.findAll(sort);
            List<Map<String,Object>> result = new ArrayList<>();
            for(News news1 : newsList){
                Map<String,Object> element = new HashMap<>();
                element.put("news_id",news1.getId());
                element.put("count",news1.getCount());
                element.put("date",news1.getDate());
                element.put("part",news1.getPart());
                element.put("title",news1.getTitle());
                element.put("url",news1.getUrl());
                result.add(element);
            }
            return ResultFactory.buildSuccessResult(result);

        }else{
            List<News> newsList=newsRepo.getOnesInterestNews(user_id);
            List<Map<String,Object>> result = new ArrayList<>();
            for(News news1 : newsList){
                Map<String,Object> element = new HashMap<>();
                element.put("news_id",news1.getId());
                element.put("count",news1.getCount());
                element.put("date",news1.getDate());
                element.put("part",news1.getPart());
                element.put("title",news1.getTitle());
                element.put("url",news1.getUrl());
                result.add(element);
            }
            return ResultFactory.buildSuccessResult(result);
        }

    }

    public Result getTopX(Integer x){
        TimeZone timeZone  = TimeZone.getDefault() ;
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(timeZone);
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(1+calendar.get(Calendar.MONTH));
        String day = String.valueOf(calendar.get(Calendar.DATE));
        String today=year+"-"+month+"-"+day+" 00:00:00";
        System.out.println(today);
        List<News> newsList=newsRepo.getTopX(today,x);
        List<Map<String,Object>> result = new ArrayList<>();
        for(News news1 : newsList){
            Map<String,Object> element = new HashMap<>();
            element.put("news_id",news1.getId());
            element.put("count",news1.getCount());
            element.put("date",news1.getDate());
            element.put("part",news1.getPart());
            element.put("title",news1.getTitle());
            element.put("url",news1.getUrl());
            result.add(element);
        }
        return ResultFactory.buildSuccessResult(result);
    }

    public Result searchNews(String keyword){
        News news = new News();
        news.setTitle(keyword);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher=exampleMatcher.withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<News> example=Example.of(news,exampleMatcher);
        Sort sort = Sort.sort(News.class).descending();
        sort.getOrderFor("date");
        List<News> newsList=newsRepo.findAll(example,sort);
        List<Map<String,Object>> result = new ArrayList<>();
        for(News news1 : newsList){
            Map<String,Object> element = new HashMap<>();
            element.put("news_id",news1.getId());
            element.put("count",news1.getCount());
            element.put("date",news1.getDate());
            element.put("title",news1.getTitle());
            element.put("url",news1.getUrl());
            result.add(element);
        }
        return ResultFactory.buildSuccessResult(result);
    }

    public Result getNewsByPart(String part){
        News news = new News();
        news.setPart(part);
        Example<News> example=Example.of(news);
        Sort sort = Sort.sort(News.class).descending();
        sort.getOrderFor("date");
        List<News> newsList=newsRepo.findAll(example,sort);
        List<Map<String,Object>> result = new ArrayList<>();
        for(News news1 : newsList){
            Map<String,Object> element = new HashMap<>();
            element.put("news_id",news1.getId());
            element.put("count",news1.getCount());
            element.put("date",news1.getDate());
            element.put("title",news1.getTitle());
            element.put("url",news1.getUrl());
            result.add(element);
        }
        return ResultFactory.buildSuccessResult(result);
    }

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
