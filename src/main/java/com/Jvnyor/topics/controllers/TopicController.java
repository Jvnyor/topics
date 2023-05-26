package com.Jvnyor.topics.controllers;

import com.Jvnyor.topics.dtos.*;
import com.Jvnyor.topics.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topics")
public class TopicController {
    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity<TopicResponse> createTopic(@RequestBody TopicRequest topicRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(topicService.createTopic(topicRequest));
    }

    @PostMapping("/{topicId}/session")
    public ResponseEntity<TopicResponse> openVotingSession(@PathVariable Long topicId,
                                                           @RequestParam(required = false) Integer durationMinutes) {
        return ResponseEntity.ok(topicService.openVotingSession(topicId, durationMinutes));
    }

    @PostMapping("/{topicId}/votes")
    public ResponseEntity<UserResponse> registerVote(@PathVariable Long topicId,
                                                     @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(topicService.registerVote(topicId, userRequest));
    }

    @GetMapping("/{topicId}/result")
    public ResponseEntity<Result> getVotingResult(@PathVariable Long topicId) {
        return ResponseEntity.ok(topicService.getVotingResult(topicId));
    }
}
