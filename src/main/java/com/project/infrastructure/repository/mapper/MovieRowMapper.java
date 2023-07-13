package com.project.infrastructure.repository.mapper;

import com.project.domain.actor.Actor;
import com.project.domain.movie.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {

        return new Movie(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("release_date"))
        );
    }
}
