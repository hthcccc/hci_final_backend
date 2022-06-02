package com.example.hci.repository;

import com.example.hci.model.History;
import com.example.hci.model.HistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface historyRepository extends JpaRepository<History, HistoryId> {

}
