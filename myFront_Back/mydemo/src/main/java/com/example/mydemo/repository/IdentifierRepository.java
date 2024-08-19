package com.example.mydemo.repository;

import com.example.mydemo.model.Identifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentifierRepository extends JpaRepository<Identifier, Long> {
    // JpaRepository 提供了基本的 CRUD 操作
}
