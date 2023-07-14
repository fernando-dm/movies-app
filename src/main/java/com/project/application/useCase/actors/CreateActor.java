package com.project.application.useCase.actors;

import com.project.domain.actor.Actor;
import com.project.domain.actor.ActorRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateActor {

    private final ActorRepository actorRepository;

    public CreateActor(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public int addActor(Actor actor) {
        return this.actorRepository.insertActor(actor);
    }
}
