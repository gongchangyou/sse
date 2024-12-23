package org.example;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.LocalTime;

@RestController
public class SseController {
    private final Sinks.Many<String> sink;

    public SseController() {
        // 创建一个多订阅者 Sink，用于广播消息
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamEvents() {
        // 发送每秒一个事件
        return sink.asFlux();
    }

    // 将数据推送到 SSE 流的方法
    public void pushToStream(String message) {
        sink.tryEmitNext(message);
    }

    @Scheduled(fixedRate = 1000) // 每隔 1 秒执行一次
    public void executeTask() {
        pushToStream("Event at: " + LocalTime.now());
    }

}
