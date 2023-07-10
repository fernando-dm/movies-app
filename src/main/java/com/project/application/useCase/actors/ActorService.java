package com.project.application.useCase.actors;

import com.project.domain.actor.Actor;
import com.project.domain.actor.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;    }

    public Optional<List<Actor>> getActors() {
        return actorRepository.selectAllActors();
    }

    public int addActor(Actor actor){
        return this.actorRepository.insertActor(actor);
    }
}
