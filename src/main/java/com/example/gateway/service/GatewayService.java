package com.example.gateway.service;

import com.example.gateway.model.FixerModel;
import com.example.gateway.model.RequestModel;
import com.example.gateway.repository.FixerRepository;
import com.example.gateway.repository.RequestRepository;
import com.example.gateway.rest.CurrentRequest;
import com.example.gateway.rest.HistoryRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GatewayService {

    public static final String EXT_SERVICE_2 = "EXT_SERVICE_2";

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private FixerRepository fixerRepository;

    public BigDecimal current(CurrentRequest request) {
        if (Strings.isNotEmpty(request.getRequestId())) {
            Optional<RequestModel> requestModel = requestRepository.findAllByRequestId(request.getRequestId());
            if (requestModel.isPresent()) {
                throw new IllegalArgumentException("Duplicated request id");
            }
        } else {
            throw new IllegalArgumentException("No request id");
        }

        FixerModel fixerModel = fixerRepository.findTopByCurrencyOrderByTimestampDesc(request.getCurrency());
        RequestModel saveRequestModel = RequestModel
                .builder()
                .requestId(request.getRequestId())
                .serviceId(EXT_SERVICE_2)
                .timestamp(getTimestamp(request.getTimestamp()))
                .clientId(request.getClient()).build();
        requestRepository.save(saveRequestModel);

        return fixerModel.getRate();
    }

    public List<BigDecimal> history(HistoryRequest request) {
        if (Strings.isNotEmpty(request.getRequestId())) {
            Optional<RequestModel> requestModel = requestRepository.findAllByRequestId(request.getRequestId());
            if (requestModel.isPresent()) {
                throw new IllegalArgumentException("Duplicated request id");
            }
        } else {
            throw new IllegalArgumentException("No request id");
        }

        List<BigDecimal> response = new ArrayList<>();
        fixerRepository
                .findLastNRecordsByCurrency(request.getCurrency(), request.getPeriod())
                .forEach(f -> response.add(f.getRate()));
        RequestModel saveRequestModel = RequestModel
                .builder()
                .requestId(request.getRequestId())
                .serviceId(EXT_SERVICE_2)
                .timestamp(getTimestamp(request.getTimestamp()))
                .clientId(request.getClient()).build();
        requestRepository.save(saveRequestModel);

        return response;
    }

    private Timestamp getTimestamp(Long value){
        return Timestamp.from(
                Instant.ofEpochMilli(
                        (value) * 1000));
    }
}
