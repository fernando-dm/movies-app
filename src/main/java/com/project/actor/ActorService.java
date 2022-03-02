package com.project.actor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    private final ActorDao actorDao;

    public ActorService(ActorDao actorDao) {
        this.actorDao = actorDao;    }

    public Optional<List<Actor>> getActors() {
        return actorDao.selectAllActors();
    }

    public int addActor(Actor actor){
        return this.actorDao.insertActor(actor);
    }
}
