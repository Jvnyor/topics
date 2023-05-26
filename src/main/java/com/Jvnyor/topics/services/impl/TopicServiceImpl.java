package com.Jvnyor.topics.services.impl;

import com.Jvnyor.topics.dtos.*;
import com.Jvnyor.topics.entities.Topic;
import com.Jvnyor.topics.entities.User;
import com.Jvnyor.topics.enums.Vote;
import com.Jvnyor.topics.repositories.TopicRepository;
import com.Jvnyor.topics.repositories.UserRepository;
import com.Jvnyor.topics.services.TopicService;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class TopicServiceImpl implements TopicService {
    public static final String URL = "https://user-info.herokuapp.com/users/";
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TopicResponse createTopic(TopicRequest topicRequest) {
        log.info("Creating a topicDTO: {}", topicRequest);
        return TopicResponse.convert(topicRepository.save(Topic.convert(topicRequest)));
    }

    @Override
    public TopicResponse openVotingSession(Long topicId, Integer durationMinutes) {
        log.info("Opening a voting session\ntopicId: {}", topicId);
        Optional<Topic> topicOptional = topicRepository.findById(topicId);
        if (topicOptional.isPresent()) {
            LocalDateTime closingTime = LocalDateTime.now().plusMinutes(durationMinutes != null ? durationMinutes : 1);
            log.info("durationMinutes: {}", durationMinutes);
            Topic topic = topicOptional.get();
            topic.setClosingDate(closingTime);
            return TopicResponse.convert(topicRepository.save(topic));
        }
        return null;
    }

    @Override
    public UserResponse registerVote(Long topicId, UserRequest userRequest) {
        log.info("Registering a vote\ntopicId: {}\nuserRequest: {}", topicId, userRequest);
        Optional<Topic> topicOptional = topicRepository.findById(topicId);
        if (topicOptional.isPresent() && topicOptional.get().getClosingDate().isAfter(LocalDateTime.now())) {
            Topic topic = topicOptional.get();
            if (!hasVoted(topic.getId(), userRequest.getId())) {
                User user = User.convert(userRequest);
                user.setTopic(topic);
                User userSaved = userRepository.save(user);
                UserResponse userResponse = UserResponse.convert(userSaved);
                userResponse.setTopicId(topic.getId());
                return userResponse;
            }
        }
        return null;
    }

    @Override
    public Result getVotingResult(Long topicId) {
        log.info("Getting voting result by topicId: {}", topicId);
        Optional<Topic> topicOptional = topicRepository.findById(topicId);
        if (topicOptional.isPresent() && topicOptional.get().getClosingDate().isBefore(LocalDateTime.now())) {
            Topic topic = topicOptional.get();
            return Result.builder()
                    .topicId(topic.getId())
                    .yesQuantity(userRepository.countByTopicAndVote(topic.getId(), Vote.YES.toString()))
                    .noQuantity(userRepository.countByTopicAndVote(topic.getId(), Vote.NO.toString()))
                    .build();
        }
        return null;
    }

    private boolean hasVoted(Long topicId, Long associateId) {
        return userRepository.existsByTopicAndAssociateId(topicId, associateId);
    }

    @SneakyThrows
    private boolean cpfIsValid(String cpf) {
        log.info("Checking if CPF is able to vote");
        HttpResponse<JsonNode> response = Unirest.get(URL + cpf)
                .asJson();
        log.info("Response Status: {}", response.getStatus());
        String json = response.isSuccess() ? response.toString() : null;
        log.info("Json: {}", json);
        ObjectMapper objectMapper = new ObjectMapper();
        if (json != null) {
            log.info("Is able to vote: {}", cpf);
            CpfResponse cpfResponse = objectMapper.readValue(json, CpfResponse.class);
            return cpfResponse.getStatus().equals("ABLE_TO_VOTE");
        }
        log.info("Is unable to vote: {}", cpf);
        return false;
    }
}
