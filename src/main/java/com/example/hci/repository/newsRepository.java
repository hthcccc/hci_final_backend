package com.example.hci.repository;

import com.example.hci.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface newsRepository extends JpaRepository<News,String> {
    @Query(value = "select * from news where news.date>=?1 order by news.count desc limit ?2",nativeQuery = true)
    List<News> getTopX(String today,Integer x);
}
