package ru.mirea.laricheva.employeedb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDatabase db = App.getInstance().getDatabase();
        SuperheroDao superheroDao = db.superheroDao();

        Superhero superhero = new Superhero();
        superhero.name = "Batman";
        superhero.enemy = "Superman";
        superhero.trait = "Rich, secretive";
        superheroDao.insert(superhero);

        List<Superhero> superheroList = superheroDao.getAll();
        superhero = superheroDao.getById(1);
        superhero.enemy = "Catwoman";
        superheroDao.update(superhero);
        Log.d("SUBD", superhero.name + " " + superhero.enemy + " " + superhero.trait);
    }
}