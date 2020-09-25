package com.example.gateway.configuration;

import com.example.gateway.model.FixerModel;
import com.example.gateway.repository.FixerRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class FixerConfiguration {

    @Autowired
    private FixerRepository fixerRepository;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Scheduled(cron = "*/15 * * * * *")
    public void run(){
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://data.fixer.io/api/latest?access_key=9619f55c25734db8e483f8f205743697"))
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
            Timestamp timestamp = Timestamp.from(
                    Instant.ofEpochMilli(
                            (convertedObject.get("timestamp").getAsLong()) * 1000));
            String base = convertedObject.get("base").getAsString();

            List<FixerModel> modelLists = new ArrayList<>();

            convertedObject
            .get("rates")
            .getAsJsonObject()
            .entrySet()
            .forEach(e -> modelLists.add(FixerModel.builder()
                    .base(base)
                    .timestamp(timestamp)
                    .currency(e.getKey())
            .rate(e.getValue().getAsBigDecimal())
            .build()));

            fixerRepository.saveAll(modelLists);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
