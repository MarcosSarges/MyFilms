package com.mp.myfilms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mp.myfilms.R;
import com.mp.myfilms.controllers.FilmController;
import com.mp.myfilms.helpers.RecyclerItemClickListener;
import com.mp.myfilms.models.Film;

import java.util.List;

public class ListFilmsActivity extends AppCompatActivity {

    private RecyclerView recycler_list_films;
    private List<Film> films;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_films);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recycler_list_films = findViewById(R.id.recycler_list_films);

        recycler_list_films.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recycler_list_films,
                new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    }

                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(), RegisterFilmActivity.class);
                        intent.putExtra("itemFilm", films.get(position));
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        FilmController filmController = new FilmController(ListFilmsActivity.this);
                        filmController.delete(films.get(position), getApplicationContext());
                        filmController.getAllFilms(recycler_list_films);
                    }
                }
        ));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterFilmActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FilmController filmController = new FilmController(this);
        films = filmController.getAllFilms(recycler_list_films);
    }
}
