package ru.msb.common.dao;

import io.vavr.Tuple;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public interface ResponseEntityDao {
    ConcurrentMap<Tuple, ResponseEntity<String>> RESPONSE_ENTITY_CONCURRENT_MAP = new ConcurrentHashMap<>();

    ResponseEntity<String> getResponseEntity(Tuple key);

    Collection<ResponseEntity<String>> getAllResponseEntities();

    Tuple save(ResponseEntity<String> responseEntity, String requestName);

}