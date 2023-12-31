package com.project.infrastructure.repository.postgresRepository;

import com.project.domain.movie.Movie;
import com.project.domain.movie.MovieRepository;
import com.project.infrastructure.repository.mapper.MovieRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MoviePostgresRepository implements MovieRepository {
    final JdbcTemplate jdbcTemplate;

    public MoviePostgresRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> selectMovies() {
        String sql = """
                 select  m.*
                from public.movie m
                group by m.id, m.name, m.release_date; """;

        return jdbcTemplate.query(sql, new MovieRowMapper());
    }

    @Override
    public Optional<Movie> selectMovieById(int id) {

        String sql = """
                 select  m.*
                from public.movie m
                         --left join public.actor a on
                        --m.id = a.movie 
                where m.id=? 
                group by m.id, m.name, m.release_date; """;

        List<Movie> movies1 = jdbcTemplate.query(sql, new MovieRowMapper(), id);
        return movies1.stream().findFirst();
    }

    @Override
    public int deleteMovie(int id) {
        String sql = """
                delete from public.movie where id=?
                """;
        return jdbcTemplate.update(sql, id);
    }


    @Override
    public int insertMovie(Movie movie) {
        String sql = """
                insert into public.movie(name,release_date) 
                values(?,?);
                """;
        return jdbcTemplate.update(sql,
                movie.name(), movie.releaseDate());
    }

    @Override
    public void update(Movie movie) {
        String sql = """
                    update public.movie 
                    set name=? , 
                    release_date=? 
                    where id=? 
                """;

        jdbcTemplate.update(sql,
                movie.name(),
                movie.releaseDate(),
                movie.id());
    }

}
