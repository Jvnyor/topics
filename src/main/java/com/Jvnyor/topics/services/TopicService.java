package com.Jvnyor.topics.services;

import com.Jvnyor.topics.dtos.*;
import org.springframework.stereotype.Service;

@Service
public interface TopicService {

    TopicResponse createTopic(TopicRequest topicRequest);

    TopicResponse openVotingSession(Long topicId, Integer durationMinutes);

    UserResponse registerVote(Long topicId, UserRequest userRequest);

    Result getVotingResult(Long topicId);
}
