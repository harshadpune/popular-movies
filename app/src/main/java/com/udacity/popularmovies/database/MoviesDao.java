package com.udacity.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.udacity.popularmovies.dao.MoviesData;
import java.util.List;

/**
 * Created by HARSHAD on 02/07/2018.
 */

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM movies")
    LiveData<List<MoviesData>> loadAllMovies();

    @Delete
    void deleteMovie(MoviesData moviesData);

    @Insert
    void insertFavoriteMovie(MoviesData moviesData);

    @Query("SELECT * FROM movies WHERE id=:id")
    LiveData<MoviesData> getMovieById(String id);
}
