package com.data.reconciliation.repository;

import java.util.List;

import com.data.reconciliation.entity.EntityEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsRepository  extends ElasticsearchRepository<EntityEs, String>{
	 
    List<EntityEs> findAll();
}
