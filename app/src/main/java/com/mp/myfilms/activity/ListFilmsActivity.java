package com.mp.myfilms.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mp.myfilms.DAO.FilmDao;
import com.mp.myfilms.R;
import com.mp.myfilms.adapter.ListFilmsAdapter;
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

                        final Film film = films.get(position);

                        AlertDialog.Builder dialog = new AlertDialog.Builder(ListFilmsActivity.this);
                        dialog.setTitle("Confirmar exclusão");
                        dialog.setMessage("Deseja excluir o filme: " + film.getTitle() + " ?");

                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FilmDao filmDao = new FilmDao(getApplicationContext());
                                if (filmDao.delete(film)) {
                                    films = filmDao.getAllFilms();
                                    loadList();
                                    Toast.makeText(getApplicationContext(),
                                            "Sucesso ao excluir tarefa!",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "Erro ao excluir tarefa!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        dialog.setNegativeButton("Não", null);

                        //Exibir dialog
                        dialog.create();
                        dialog.show();
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
    protected void onResume() {
        FilmController filmController = new FilmController(getApplicationContext());
        films = filmController.getAllFilms();
        loadList();
        super.onResume();
    }


    public void loadList() {
        ListFilmsAdapter adapter = new ListFilmsAdapter(films);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_list_films.setLayoutManager(layoutManager);
        recycler_list_films.setHasFixedSize(true);
        recycler_list_films.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recycler_list_films.setAdapter(adapter);
    }


}
