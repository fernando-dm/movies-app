package com.project.utils.mapper;

import com.project.actor.Actor;
import com.project.movie.Movie;
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

    private List<Actor> getActorList(ResultSet rs) throws SQLException {
        List<Actor> actorList = new ArrayList<Actor>();
        List<String> actors = Arrays.stream(rs.getArray("actor_list")
                        .toString()
                        .split(";"))
                .map(String::trim)
                .collect(Collectors.toList());

        for (String actor : actors) {
            actorList.add(new Actor(
                    Integer.parseInt(actor.split(",")[0].trim()),
                    actor.split(",")[1],
                    Integer.parseInt(actor.split(",")[2].trim())
            ));
        }
        return actorList;
    }
}
