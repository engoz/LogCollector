package com.example.service;

import com.example.domain.CityLog;
import com.example.repository.LogRepository;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class KafkaConsumerService {

    int count = 0;

    @Autowired
    LogRepository logRepository;

    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(topics="${kafka.topic}")
    public void consume(@Payload String message) {
        try {
            String cityMap = saveToDb(message);

            if (cityMap != null)
                template.convertAndSend("/topic/citylogs", cityMap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String saveToDb(String message) throws IOException {

        String countCity = null;
        JsonFactory jfactory = new JsonFactory();
        JsonParser jParser = jfactory.createParser(message);

        String appname = null;
        String level = null;
        String msg = null;
        Number timestamp = null;


        while (jParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldname = jParser.getCurrentName();
            if ("appname".equals(fieldname)) {
                jParser.nextToken();
                appname = jParser.getText();
            }

            if ("level".equals(fieldname)) {
                jParser.nextToken();
                level = jParser.getText();
            }

            if ("message".equals(fieldname)) {
                jParser.nextToken();
                msg = jParser.getText();
            }

            if ("timestamp".equals(fieldname)) {
                jParser.nextToken();
                timestamp = jParser.getNumberValue();
            }


        }

        if(appname != null && appname != "") {

            CityLog cityLog = new CityLog(appname, level, timestamp, msg);
            logRepository.save(cityLog);

            int size = logRepository.findAllByName(appname).size();
            JSONObject json = new JSONObject();

            json.put("city",appname);
            json.put("logCount",size);


            countCity = json.toString();

            jParser.close();
            return countCity;
        }

        return null;
    }

    public  boolean isNumeric(String str)
    {
        try
        {
            @SuppressWarnings("unused")
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

}
