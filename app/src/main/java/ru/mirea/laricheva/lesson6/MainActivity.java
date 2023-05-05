package ru.mirea.laricheva.lesson6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ru.mirea.laricheva.lesson6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPref =
                getSharedPreferences("mirea_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String group = sharedPref.getString("GROUP", "unknown");
        int number = sharedPref.getInt("NUMBER", 0);
        String film = sharedPref.getString("FAV_FILM", "unknown");
        Log.d("Values:", group + " " + number + " " + film);

        binding.editTextGroup.setText(group);
        binding.editTextNumber.setText(String.valueOf(number));
        binding.editTextFilm.setText(film);

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String group = String.valueOf(binding.editTextGroup.getText());
                int number = Integer.valueOf(binding.editTextNumber.getText().toString());
                String film = String.valueOf(binding.editTextFilm.getText());
                Log.d("Values:", group + " " + number + " " + film);

                editor.putString("GROUP", group);
                editor.putInt("NUMBER", number);
                editor.putString("FAV_FILM", film);
                editor.apply();
            }
        });
    }
}