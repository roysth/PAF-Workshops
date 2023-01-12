package vttp.day29practice.repository;

import java.io.StringReader;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import vttp.day29practice.AppConfig;
import vttp.day29practice.model.Marvel;

@Repository
public class MarvelCache {

    @Autowired @Qualifier (AppConfig.CACHE_MARVEL)
    private RedisTemplate<String, String> redisTemplate;

    //the put method
    public void cache(String key, List<Marvel> values) {

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        values.stream()
            .forEach(c -> { 
                arrBuilder.add(c.toJson());
            });
        ops.set(key, arrBuilder.build().toString(), Duration.ofSeconds(300));
    }


    public Optional<List<Marvel>> get(String name) {

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String value = ops.get(name);
        if (null == value)
            return Optional.empty();

        JsonReader reader = Json.createReader(new StringReader(value));
        JsonArray results = reader.readArray();

        List<Marvel> heros = results.stream()
                .map(v -> (JsonObject)v)
                .map(v -> Marvel.fromCache(v))
                .toList();

        return Optional.of(heros);
    }
}
