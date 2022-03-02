package com.project.actor;

import java.util.List;
import java.util.Optional;

public interface ActorDao {

    Optional<List<Actor>> selectAllActors();
    int insertActor(Actor actor);
    Optional<Actor> selectActor(int id);
    int deleteActor(int id);
    void update(int id);
}
