package com.example.hci.repository;

import com.example.hci.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface newsRepository extends JpaRepository<News,String> {
}
