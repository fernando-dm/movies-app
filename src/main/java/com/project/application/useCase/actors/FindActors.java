package com.project.application.useCase.actors;

import com.project.domain.actor.Actor;
import com.project.domain.actor.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FindActors {

    private final ActorRepository actorRepository;

    public FindActors(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public Optional<List<Actor>> getActors() {
        return actorRepository.selectAllActors();
    }
}
