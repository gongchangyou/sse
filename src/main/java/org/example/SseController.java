package org.example;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;

@RestController
public class SseController {

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamEvents() {
        // 发送每秒一个事件
        return Flux.interval(Duration.ofSeconds(1))
                   .map(sequence -> "Event at: " + LocalTime.now());
    }
}
