package com.rootincode.demospringhttp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientDemoService {

    @Autowired
    private WebClient webClient;

    public void getSynchronousCall() {
        // GET /todos/1

        System.out.println("Before the request !");

        Todo firstTodo = webClient.get()
                .uri("/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Todo.class)
                .block();

        System.out.println("First todo : " + firstTodo);
        System.out.println("After the request !");

    }

    public void getAsynchronousCall(){
        System.out.println("Before the request !");
        Mono<Todo> todoMono = webClient.get()
                .uri("/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Todo.class);

        todoMono.subscribe(firstTodo -> {
            System.out.println("First todo : " + firstTodo);
        });

        System.out.println("After the request !");
    }

    public void postSynchronousCall() {
        Todo newTodo = new Todo(1L, "ceci est un titre test", true, 54L);

        webClient.post()
                .uri("/")
                .bodyValue(newTodo)
                .retrieve()
                .toEntity(Todo.class)
                .subscribe(todoResponseEntity -> {
                    System.out.println(todoResponseEntity.getStatusCode() + " " + todoResponseEntity.getBody());
                });
    }
}
