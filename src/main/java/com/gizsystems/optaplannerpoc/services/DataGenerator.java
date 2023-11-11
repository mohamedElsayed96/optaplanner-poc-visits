package com.gizsystems.optaplannerpoc.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gizsystems.optaplannerpoc.model.*;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@org.springframework.stereotype.Service
@Log4j2
public class DataGenerator {

    private static final Random RANDOM = new Random(37);


    private static final List<Agent> AGENTS = List.of(
            new Agent(UUID.randomUUID(), "Ann", new HashSet<>(Arrays.stream(Skill.values()).map(Enum::name).toList()), true),
            new Agent(UUID.randomUUID(), "Beth", buildSupportedServices(), false),
            new Agent(UUID.randomUUID(), "Carl", buildSupportedServices(), false),
            new Agent(UUID.randomUUID(), "Dennis", buildSupportedServices(), false),
            new Agent(UUID.randomUUID(), "Elsa", buildSupportedServices(), false),
            new Agent(UUID.randomUUID(), "Francis", buildSupportedServices(), false),
            new Agent(UUID.randomUUID(), "Gus", buildSupportedServices(), false),
            new Agent(UUID.randomUUID(), "Hugo", buildSupportedServices(), false)
    );


    private static final List<Visit> VISITS = List.of(
            new Visit(UUID.randomUUID(), "Mohamed", Skill.values()[RANDOM.nextInt(Skill.values().length)], true),
            new Visit(UUID.randomUUID(), "Ahmed", Skill.values()[RANDOM.nextInt(Skill.values().length)], false),
            new Visit(UUID.randomUUID(), "Ibrahim", Skill.values()[RANDOM.nextInt(Skill.values().length)], true),
            new Visit(UUID.randomUUID(), "Yousif", Skill.values()[RANDOM.nextInt(Skill.values().length)], false),
            new Visit(UUID.randomUUID(), "Shimaa", Skill.values()[RANDOM.nextInt(Skill.values().length)], false),
            new Visit(UUID.randomUUID(), "Asmaa", Skill.values()[RANDOM.nextInt(Skill.values().length)], false),
            new Visit(UUID.randomUUID(), "Kariman", Skill.values()[RANDOM.nextInt(Skill.values().length)], true),
            new Visit(UUID.randomUUID(), "Mahmoud", Skill.values()[RANDOM.nextInt(Skill.values().length)], true),
            new Visit(UUID.randomUUID(), "Elewa", Skill.values()[RANDOM.nextInt(Skill.values().length)], true),
            new Visit(UUID.randomUUID(), "Tamer", Skill.values()[RANDOM.nextInt(Skill.values().length)], false),
            new Visit(UUID.randomUUID(), "Youmna", Skill.values()[RANDOM.nextInt(Skill.values().length)], true),
            new Visit(UUID.randomUUID(), "Amer", Skill.values()[RANDOM.nextInt(Skill.values().length)], false),
            new Visit(UUID.randomUUID(), "Bassem", Skill.values()[RANDOM.nextInt(Skill.values().length)], false),
            new Visit(UUID.randomUUID(), "Faris", Skill.values()[RANDOM.nextInt(Skill.values().length)], false),
            new Visit(UUID.randomUUID(), "Emad", Skill.values()[RANDOM.nextInt(Skill.values().length)], true),
            new Visit(UUID.randomUUID(), "Moustafa", Skill.values()[RANDOM.nextInt(Skill.values().length)], true)


    );
    private static final List<Task> TASKS = VISITS.stream().map(customer -> new Task(UUID.randomUUID())).toList();


    private static Set<String> buildSupportedServices() {
        int index2 = RANDOM.nextInt(1, Skill.values().length);
        int index1 = RANDOM.nextInt(index2);
        return new HashSet<>(Arrays.asList(Skill.values()).subList(index1, index2).stream().map(Enum::name).toList());
    }

    public EmployeeShift generateEmployeeShift() {
        return new EmployeeShift(AGENTS, VISITS, TASKS);
    }

    @PostConstruct
    @SneakyThrows
    public void logData() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        log.info("Visits Data : {}", objectMapper.writeValueAsString(VISITS));
        log.info("Visits Data : {}", objectMapper.writeValueAsString(TASKS));
        log.info("Agents Data : {}", objectMapper.writeValueAsString(AGENTS));
    }
}
