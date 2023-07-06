package com.project.movie;

import com.project.actor.Actor;

import java.time.LocalDate;
import java.util.List;

public record Movie(Integer id, String name, LocalDate releaseDate) {
}
