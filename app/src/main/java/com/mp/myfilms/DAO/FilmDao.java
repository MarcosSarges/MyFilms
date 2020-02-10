package com.mp.myfilms.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mp.myfilms.helpers.DBHelp;
import com.mp.myfilms.models.Film;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilmDao {

    private SQLiteDatabase read;
    private SQLiteDatabase white;

    public FilmDao(Context ctx) {
        DBHelp db = new DBHelp(ctx);
        read = db.getReadableDatabase();
        white = db.getWritableDatabase();
    }

    public boolean save(Film film) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelp.COLUMN_TITLE, film.getTitle());
        cv.put(DBHelp.COLUMN_AUTHOR, film.getAuthor());
        cv.put(DBHelp.COLUMN_CAST, film.getElenco());

        cv.put(DBHelp.COLUMN_DATE, film.getDateLaunch());
        cv.put(DBHelp.COLUMN_THUMBNAIL, film.getThumbnail());

        try {
            white.insert(DBHelp.TABLE_FILMS, null, cv);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Film film) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelp.COLUMN_TITLE, film.getTitle());
        cv.put(DBHelp.COLUMN_DATE, film.getDateLaunch());
        cv.put(DBHelp.COLUMN_AUTHOR, film.getAuthor());
        cv.put(DBHelp.COLUMN_THUMBNAIL, film.getThumbnail());
        cv.put(DBHelp.COLUMN_CAST, film.getElenco());

        try {
            String[] args = {film.getId().toString()};
            white.update(DBHelp.TABLE_FILMS, cv, DBHelp.COLUMN_ID + "=?", args);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean delete(Film film) {
        try {
            String[] args = {film.getId().toString()};
            white.delete(DBHelp.TABLE_FILMS, DBHelp.COLUMN_ID + "=?", args);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();


        String sql = "SELECT * FROM " + DBHelp.TABLE_FILMS + " ;";
        Cursor c = read.rawQuery(sql, null);

        while (c.moveToNext()) {

            Film film = new Film();

            Long id = c.getLong(c.getColumnIndex(DBHelp.COLUMN_ID));
            String title = c.getString(c.getColumnIndex(DBHelp.COLUMN_TITLE));
            String author = c.getString(c.getColumnIndex(DBHelp.COLUMN_AUTHOR));
            String elenco = c.getString(c.getColumnIndex(DBHelp.COLUMN_CAST));
            String dataLaunch = c.getString(c.getColumnIndex(DBHelp.COLUMN_DATE));
            byte[] img = c.getBlob(c.getColumnIndex(DBHelp.COLUMN_THUMBNAIL));

            film.setId(id);
            film.setTitle(title);
            film.setAuthor(author);
            film.setElenco(elenco);
            film.setDateLaunch(dataLaunch);
            film.setThumbnail(img);
            films.add(film);
        }

        Collections.sort(films);

        return films;
    }
}
