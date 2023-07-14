package com.project.domain.movie;

import java.time.LocalDate;

public record Movie(Integer id, String name, LocalDate releaseDate) {
}
