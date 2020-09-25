package com.example.gateway.repository;

import com.example.gateway.model.RequestModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RequestRepository extends CrudRepository<RequestModel, Long> {

    Optional<RequestModel> findAllByRequestId(String requestId);
}
