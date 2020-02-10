package com.mp.myfilms.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.mp.myfilms.DAO.FilmDao;
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
        this.title = film.getTitle();
        this.cast = film.getElenco();
        this.athor = film.getAuthor();
        this.date_laouch = film.getDateLaunch();
        this.thumbnail = BitmapHelp.BytesToBitmap(film.getThumbnail());
    }


    public FilmController(Context ctx, String title, String cast, String athor, String date_laouch, Bitmap thumbnail) {
        this.ctx = ctx;
        this.title = title;
        this.cast = cast;
        this.athor = athor;
        this.date_laouch = date_laouch;
        this.thumbnail = thumbnail;
    }

    public List<Film> getAllFilms() {
        FilmDao filmDao = new FilmDao(ctx.getApplicationContext());
        List<Film> listFilms = filmDao.getAllFilms();
        return listFilms;
    }


    public boolean updateFilm() {
        if (validation()) {
            FilmDao filmDao = new FilmDao(ctx.getApplicationContext());
            if (filmDao.update(film)) {
                Toast.makeText(ctx, "Filme atualizado.", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(ctx, "Filme não foi atualizado.", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    public boolean saveFilm() {
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
                return true;

            } else {
                Toast.makeText(ctx, "Filme não foi salvo", Toast.LENGTH_SHORT).show();
            }
        }
        return false;

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

    public void delete(final Film film) {

    }


}
