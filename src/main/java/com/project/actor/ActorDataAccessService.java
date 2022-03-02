package com.project.actor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ActorDataAccessService implements ActorDao {

    private JdbcTemplate jdbcTemplate;

    public ActorDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;    }

    @Override
    public Optional<List<Actor>> selectAllActors() {
        String sql = """
                select * from project.public.actor;
                """;

        List<Actor> result = jdbcTemplate.query(sql, (rs, i) -> {
            return new Actor(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("movie"));
        });
        return Optional.of(result);
    }

    @Override
    public int insertActor(Actor actor) {
        String sql = """
                insert into project.public.actor(name, movie) values(?,?)
                """;

        return jdbcTemplate.update(sql, actor.name(),actor.movieId());
    }

    @Override
    public Optional<Actor> selectActor(int id) {
        return Optional.empty();
    }

    @Override
    public int deleteActor(int id) {
        return 0;
    }

    @Override
    public void update(int id) {

    }
}
