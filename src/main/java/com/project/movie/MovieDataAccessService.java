package com.project.movie;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieDataAccessService implements MovieDao {
    final JdbcTemplate jdbcTemplate;

    public MovieDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;}

    @Override
    public List<Movie> selectMovies() {
        String sql = """
                 select  m.*,
                 array_to_string(array_agg(a.id||', '||a.name||', '||a.movie), '; ') AS actor_list
                from project.public.movie m
                         left join project.public.actor a on
                        m.id = a.movie
                group by m.id, m.name, m.release_date; """;

        List<Movie> movies = jdbcTemplate.query(sql, new MovieRowMapper());
        return movies;
    }

    // ver como agrego los valores de actores a movies como arreglo/lista
    @Override
    public Optional<Movie> selectMovieById(int id) {

        String sql = """
                 select  m.*,
                 array_to_string(array_agg(a.id||', '||a.name||', '||a.movie), '; ') AS actor_list
                from project.public.movie m
                         left join project.public.actor a on
                        m.id = a.movie 
                where m.id=? 
                group by m.id, m.name, m.release_date; """;

        List<Movie> movies1 = jdbcTemplate.query(sql, new MovieRowMapper(), id);
        return movies1.stream().findFirst();
    }

    @Override
    public int deleteMovie(int id) {
        String sql = """
                delete from project.public.movie where id=?
                """;
        return jdbcTemplate.update(sql, id);
    }


    @Override
    public int insertMovie(Movie movie) {
        String sql = """
                insert into project.public.movie(name,release_date) 
                values(?,?);
                """;
        return jdbcTemplate.update(sql,
                movie.name(), movie.releaseDate());
    }

    @Override
    public void update(Movie movie) {
        String sql = """
                    update project.public.movie 
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
