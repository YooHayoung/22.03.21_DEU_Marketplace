package com.deu.marketplace.domain.chatLog.repository;

import com.deu.marketplace.domain.chatLog.entity.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
}
