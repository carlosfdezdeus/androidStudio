package com.example.ledswitch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ledswitch.databinding.ActivityMainBinding;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.internal.MaterialCheckable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'ledswitch' library on application startup.
    static {
        System.loadLibrary("ledswitch");
    }

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Switch switchLed = (Switch) findViewById(R.id.ledSwitch);

        switchLed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Obtener el valor del interruptor (isChecked es true si el interruptor está encendido)
                int valorSwitch = isChecked ? 1 : 0;
                // Obtener el estado del led
                int valorBrigthness = ledstatus();
                if(valorSwitch != valorBrigthness){
                    writeled(valorSwitch);
                }
                // Actualizar la interfaz de usuario mostrando el estado actual del LED
                TextView led = findViewById(R.id.ledSwitch);

                // Convertir el entero a una cadena antes de establecerlo en el TextView
                led.setText("LED Status: " + String.valueOf(valorSwitch));
            }
        });

        /*
        // Example of a call to a native method
        TextView tv = binding.sampleText;
        tv.setText(stringFromJNI());
         */
    }

    //public native String stringFromJNI();
    public native int ledstatus();
    public native int writeled(int valor);

    /**
     * A native method that is implemented by the 'ledswitch' native library,
     * which is packaged with this application.
     */


}