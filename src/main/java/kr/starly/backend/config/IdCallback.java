package kr.starly.backend.config;

import kr.starly.backend.common.annotation.Id;
import kr.starly.backend.common.util.IdGenerator;
import org.bson.Document;
import org.jspecify.annotations.NonNull;
import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.core.mapping.event.ReactiveBeforeSaveCallback;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Component
public class IdCallback implements ReactiveBeforeSaveCallback<Object> {

    @Override
    public @NonNull Publisher<Object> onBeforeSave(@NonNull Object entity, @NonNull Document document, @NonNull String collection) {
        IdGenerator.fill(entity);

        Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .ifPresent(field -> {
                    try {
                        field.setAccessible(true);
                        document.put("_id", field.get(entity));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });

        return Mono.just(entity);
    }
}