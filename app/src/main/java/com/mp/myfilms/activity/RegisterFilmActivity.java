package com.mp.myfilms.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.mp.myfilms.R;
import com.mp.myfilms.controllers.FilmController;
import com.mp.myfilms.fragments.DatePickerFragment;
import com.mp.myfilms.helpers.BitmapHelp;
import com.mp.myfilms.models.Film;

import java.text.DateFormat;
import java.util.Calendar;

public class RegisterFilmActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Button btn_open_date_picker;
    private Button btn_open_image_picker;

    private TextInputEditText edit_title;
    private TextInputEditText edit_author;
    private TextInputEditText edit_cast;
    private TextView txt_date_laouch;


    private ImageView img_thumbnail;
    private Bitmap bitmap_thumbnail;

    private Film film;
    private static final int PERMISSION_CODE = 1001;
    private static final int IMAGE_PICK_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_film);
        btn_open_date_picker = findViewById(R.id.btn_open_date_picker);
        btn_open_image_picker = findViewById(R.id.btn_open_image_picker);

        img_thumbnail = findViewById(R.id.img_thumbnail);

        edit_author = findViewById(R.id.edit_author);
        edit_title = findViewById(R.id.edit_title_film);
        edit_cast = findViewById(R.id.edit_cast);
        txt_date_laouch = findViewById(R.id.txt_date);

        film = (Film) getIntent().getSerializableExtra("itemFilm");

        if (film != null) {
            edit_author.setText(film.getAuthor());
            edit_title.setText(film.getTitle());
            edit_cast.setText(film.getElenco());
            txt_date_laouch.setText(film.getDateLaunch());
            img_thumbnail.setImageBitmap(BitmapHelp.BytesToBitmap(film.getThumbnail()));
        }


        btn_open_date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.button_save) {

            if (film != null) {
                FilmController filmController = new FilmController(
                        this,
                        film
                );
                filmController.updateFilm();
            } else {

                FilmController filmController = new FilmController(
                        this,
                        edit_title.getText().toString(),
                        edit_cast.getText().toString(),
                        edit_author.getText().toString(),
                        txt_date_laouch.getText().toString(),
                        bitmap_thumbnail
                );
                filmController.saveFilm();
            }

            Intent intent = new Intent(getApplicationContext(), ListFilmsActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.button_list) {
            Intent intent = new Intent(getApplicationContext(), ListFilmsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public void openImagePicker(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {

                String[] permissions = {Manifest.permission.CAMERA};
                ActivityCompat.requestPermissions(this, permissions, 0);

            } else {
                openCamera();
            }
        } else {
            openCamera();

        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        txt_date_laouch.setText(currentDateString);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            Bundle extras = data.getExtras();

            Bitmap img = (Bitmap) extras.get("data");
            bitmap_thumbnail = img;
            img_thumbnail.setImageBitmap(img);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Infelizemente a permição foi negada", Toast.LENGTH_SHORT).show();
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
