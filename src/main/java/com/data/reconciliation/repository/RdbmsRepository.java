package com.data.reconciliation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.data.reconciliation.entity.EntityRdbms;

import java.util.List;

public interface RdbmsRepository extends JpaRepository<EntityRdbms, Long> {

    @Query("SELECT e FROM EntityRdbms e WHERE e.id > :currentId")
    List<EntityRdbms> findByCurrentId(long currentId);

    @Query("SELECT MAX(e.id) FROM EntityRdbms e")
    Long findMaxId();
}
