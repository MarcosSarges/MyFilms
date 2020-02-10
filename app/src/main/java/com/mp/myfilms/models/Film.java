package com.mp.myfilms.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class Film implements Serializable, Comparable<Film> {

    private Long id;

    private String title;
    private String elenco;
    private String author;
    private String dateLaunch;

    private byte[] thumbnail;

    public Film() {
    }

    public Film(Long id, String title, String elenco, String author, String dateLaunch, byte[] thumbnail) {
        this.id = id;
        this.title = title;
        this.elenco = elenco;
        this.author = author;
        this.dateLaunch = dateLaunch;
        this.thumbnail = thumbnail;
    }

    public Film(String title, String elenco, String author, String dateLaunch, byte[] thumbnail) {
        this.title = title;
        this.elenco = elenco;
        this.author = author;
        this.dateLaunch = dateLaunch;
        this.thumbnail = thumbnail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getElenco() {
        return elenco;
    }

    public void setElenco(String elenco) {
        this.elenco = elenco;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateLaunch() {
        return dateLaunch;
    }

    public void setDateLaunch(String dateLaunch) {
        this.dateLaunch = dateLaunch;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int compareTo(Film film) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
        try {
            Date dateA = df.parse(this.dateLaunch);
            Date dateB = df.parse(film.dateLaunch);
            return (int) (dateB.getTime() - dateA.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
