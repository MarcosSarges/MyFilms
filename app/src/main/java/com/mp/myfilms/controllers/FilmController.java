package com.mp.myfilms.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mp.myfilms.DAO.FilmDao;
import com.mp.myfilms.adapter.ListFilmsAdapter;
import com.mp.myfilms.helpers.BitmapHelp;
import com.mp.myfilms.models.Film;

import java.util.List;

public class FilmController {

    private Context ctx;
    private String title, cast, athor, date_laouch;
    private Bitmap thumbnail;
    private Film film;

    public FilmController(Context ctx) {
        this.ctx = ctx;
    }

    public FilmController(Context ctx, Film film) {
        this.ctx = ctx;
        this.film = film;

    }


    public FilmController(Context ctx, String title, String cast, String athor, String date_laouch, Bitmap thumbnail) {
        this.ctx = ctx;
        this.title = title;
        this.cast = cast;
        this.athor = athor;
        this.date_laouch = date_laouch;
        this.thumbnail = thumbnail;
    }

    public List<Film> getAllFilms(RecyclerView rcView) {
        FilmDao filmDao = new FilmDao(ctx);
        List<Film> listFilms = filmDao.getAllFilms();

        //Configurar um adapter
        ListFilmsAdapter adapter = new ListFilmsAdapter(listFilms);

        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ctx);
        rcView.setLayoutManager(layoutManager);
        rcView.setHasFixedSize(true);
        rcView.addItemDecoration(new DividerItemDecoration(ctx, LinearLayout.VERTICAL));
        rcView.setAdapter(adapter);
        return listFilms;
    }


    public void updateFilm() {
        if (validation()) {
            FilmDao filmDao = new FilmDao(ctx);
            if (filmDao.update(film)) {
                Toast.makeText(ctx, "Filme atualizado.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ctx, "Filme não foi atualizado.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveFilm() {
        if (validation()) {
            FilmDao filmDao = new FilmDao(ctx);

            film = new Film(
                    this.title,
                    this.cast,
                    this.athor,
                    this.date_laouch,
                    BitmapHelp.BitmapToArrayBytes(this.thumbnail)
            );

            if (filmDao.save(film)) {
                Toast.makeText(ctx, "Filme salvo com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ctx, "Filme não foi salvo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validation() {
        if (this.date_laouch.isEmpty()) {
            Toast.makeText(ctx, "Você deve selecionar uma data.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (this.athor.isEmpty()) {
            Toast.makeText(ctx, "Você deve informar o nome do autor.", Toast.LENGTH_SHORT).show();
            return false;

        } else if (this.cast.isEmpty()) {
            Toast.makeText(ctx, "Você deve informar o elenco.", Toast.LENGTH_SHORT).show();
            return false;

        } else if (this.title.isEmpty()) {
            Toast.makeText(ctx, "Você deve informar o titulo do filme.", Toast.LENGTH_SHORT).show();
            return false;

        } else if (this.thumbnail == null) {
            Toast.makeText(ctx, "Você deve colocar a capa do filme.", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }

    public void delete(final Film film, final Context ctxAplication) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this.ctx);

        //Configura título e mensagem
        dialog.setTitle("Confirmar exclusão");
        dialog.setMessage("Deseja excluir o filme: " + film.getTitle() + " ?");

        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FilmDao filmDao = new FilmDao(ctxAplication);
                if (filmDao.delete(film)) {
                    Toast.makeText(ctxAplication,
                            "Sucesso ao excluir tarefa!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ctxAplication,
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
