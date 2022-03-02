package com.project.actor;


import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/actors")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/list")
    public Optional<List<Actor>> listActors(){

        return actorService.getActors();
    }

    @PostMapping("/add")
    public int addNewActor(@RequestBody Actor actor){

        int i = actorService.addActor(actor);
        return i;
    }
}
