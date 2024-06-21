package com.micropos.batch.jpa;

import com.micropos.batch.model.User;
import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
@EntityScan("com.micropos.batch.model.User")
public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query(value = "INSERT IGNORE INTO user(user_id) VALUES (?)", nativeQuery = true)
    void InsertIgnore(String user_id);
}
