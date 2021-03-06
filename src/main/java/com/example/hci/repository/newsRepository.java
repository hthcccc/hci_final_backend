package com.example.hci.repository;

import com.example.hci.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface newsRepository extends JpaRepository<News,String> {
    @Query(value = "select * from news where news.date>=?1 order by news.count desc limit ?2",nativeQuery = true)
    List<News> getTopX(String today,Integer x);

    @Query(value = "select m.part from (select t.part,count(t.part) from (select part from news join history on news.id=history.news_id where user_id=?1) as t(part) group by part order by count(part) desc limit 3) as m(part,count)",nativeQuery = true)
    List<String> getOnesInterestPart(String user_id);

    @Query(value = "select * from news where part in (select m.part from (select t.part,count(t.part) from (select part from news join history on news.id=history.news_id where user_id=?1) as t(part) group by part order by count(part) desc limit 3) as m(part,count)) order by date desc",nativeQuery = true)
    List<News> getOnesInterestNews(String user_id);

    @Query(value = "select F.part from (select T.part,count(T.part) from (select user_id,part from history join news on history.news_id = news.id where history.user_id=?1) T(user_id,part) group by T.part) F(part,count) order by F.count desc limit 1", nativeQuery = true)
    String getOnesFavoritePart(String user_id);

    @Query(value = "select n from News n where n.part=?1 order by n.date desc")
    List<News> getNewsByPart(String part);

    @Query(value = "select * from News where title like?1 order by date desc",nativeQuery = true)
    List<News> getNewsByKeyword(String keyword);
}
