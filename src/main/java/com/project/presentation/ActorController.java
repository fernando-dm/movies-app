package com.project.presentation;


import com.project.application.useCase.actors.FindActors;
import com.project.application.useCase.actors.CreateActor;
import com.project.domain.actor.Actor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/actors")
public class ActorController {

    private final FindActors findActors;
    private final CreateActor createActor;

    public ActorController(FindActors findActors, CreateActor createActor) {
        this.findActors = findActors;
        this.createActor = createActor;
    }

    @GetMapping("/list")
    public ResponseEntity<Optional<List<Actor>>> listActors() {
        return new ResponseEntity<>(findActors.getActors(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Integer> addNewActor(@RequestBody Actor actor) {
        return new ResponseEntity<>(createActor.addActor(actor), HttpStatus.CREATED);
    }
}
