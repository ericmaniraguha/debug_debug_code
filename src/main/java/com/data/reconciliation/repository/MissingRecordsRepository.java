package com.data.reconciliation.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


import com.data.reconciliation.entity.MissingRecordsEntity;

public interface MissingRecordsRepository extends ElasticsearchRepository<MissingRecordsEntity, Long> {
}
