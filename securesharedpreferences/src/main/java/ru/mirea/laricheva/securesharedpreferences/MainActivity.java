package ru.mirea.laricheva.securesharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.security.GeneralSecurityException;

import ru.mirea.laricheva.securesharedpreferences.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String poet;
    private String image;
    private SharedPreferences secureSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
        try
        {
            String mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
            secureSharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    mainKeyAlias,
                    getBaseContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            poet = secureSharedPreferences.getString("POET", "default");
            image = secureSharedPreferences.getString("IMAGE", "default");
        }
        catch (GeneralSecurityException | IOException e)
        {
            throw new RuntimeException(e);
        }

        binding.imageView.setImageResource(getResources().getIdentifier(image, "drawable", getPackageName()));
        Log.d("Values:", poet + " " + image);
        binding.editTextPoet.setText(poet);
        binding.editTextImage.setText(image);

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String poetSave = String.valueOf(binding.editTextPoet.getText());
                String imageSave = String.valueOf(binding.editTextImage.getText());
                binding.imageView.setImageResource(getResources().getIdentifier(imageSave, "drawable", getPackageName()));
                Log.d("Values:", poetSave + " " + imageSave);
                secureSharedPreferences.edit().putString("POET", poetSave).apply();
                secureSharedPreferences.edit().putString("IMAGE", imageSave).apply();
            }
        });
    }
}