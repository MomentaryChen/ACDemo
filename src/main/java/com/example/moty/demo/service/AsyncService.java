package com.example.moty.demo.service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Async("taskExecutor")
    public void asyncTask() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User in async: " + (auth != null ? auth.getName() : "null"));
    }

    @Async("taskExecutor")
    public CompletableFuture<String> asyncTask1() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User in async2: " + (auth != null ? auth.getName() : "null"));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return CompletableFuture.completedFuture(
            Objects.isNull(auth) ? "Trigger By null" : "Trigger By " + auth.getName()
        );
    }

}
