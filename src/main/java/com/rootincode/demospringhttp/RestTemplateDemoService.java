package com.rootincode.demospringhttp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RestTemplateDemoService {

    @Autowired
    private RestTemplate restTemplate;

    public void getWithGetForObject() {
        // GET /todos/1
        Todo firstTodo = restTemplate.getForObject("/1", Todo.class);
        System.out.println(firstTodo);
    }

    public void getWithGetForEntity() {
        // GET /todos/2
        ResponseEntity<Todo> secondTodoResponse = restTemplate.getForEntity("/2", Todo.class);

        HttpStatus secondTodoStatus = secondTodoResponse.getStatusCode();
        System.out.println("second todo status : " + secondTodoStatus);

        Todo secondTodo = secondTodoResponse.getBody();
        System.out.println("second todo : " + secondTodo);
    }

    public void getWithHeadersAndParams() {

        // GET with headers and query params

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        String urlWithQueryParams = UriComponentsBuilder.fromHttpUrl("/")
                .queryParam("userId", 3)
                .toUriString();

        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<Todo[]> todosResponse = restTemplate.exchange(
                urlWithQueryParams,
                HttpMethod.GET,
                httpEntity,
                Todo[].class
        );

        if (todosResponse.getStatusCode() == HttpStatus.OK && todosResponse.hasBody()) {
            Todo[] todos = todosResponse.getBody();
            for (Todo todo : todos) {
                System.out.println("Multiple todo : " + todo);
            }
        }
    }

    public void postWithPostForObject() {
        // POST /todos
        Todo newTodo = new Todo(18L, "titre test", false, 36L);
        Todo savedTodo = restTemplate.postForObject("/", newTodo, Todo.class);
        System.out.println("savedTodo" + savedTodo);
    }
}
