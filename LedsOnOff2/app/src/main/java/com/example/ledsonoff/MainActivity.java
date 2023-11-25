package com.example.ledsonoff;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String led = "/sys/class/leds/beaglebone:green:usr3/brightness";
    private boolean isLedOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonOn = (Button) findViewById(R.id.buttonOn);
        Button buttonOff = (Button) findViewById(R.id.buttonOff);

        buttonOn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                /*try {
                    setOnButton();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }*/

                try {
                    if (!isLedOn) {
                        setOnButton();
                        isLedOn = true;
                    } else {
                        System.out.println("LED usr3 is already ON\n");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        buttonOff.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                /*try {
                    setOnButton();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }*/
                try {
                    if (isLedOn) {
                        setOffButton();
                        isLedOn = false;
                    } else {
                        System.out.println("LED usr3 is already OFF\n");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setOnButton() throws IOException {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        File file = new File(led);
        if (file.canRead()) {
            fileInputStream = new FileInputStream(file);
            if (fileInputStream.read() != '0') {
                System.out.println("LED usr3 already ON\n");
                return;
            } else {
                System.out.println("Turning LED ON\n");
            }
        } else {
            System.out.println("Can't read LED\n");
        }
        if (file.canWrite()) {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write('1');
        }
    }

    public void setOffButton() throws IOException {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        File file = new File(led);
        if (file.canRead()) {
            fileInputStream = new FileInputStream(file);
            if (fileInputStream.read() != '1') {
                System.out.println("LED usr3 already OFF\n");
                return;
            } else {
                System.out.println("Turning LED OFF\n");
            }
        } else {
            System.out.println("Can't read LED\n");
        }
        if (file.canWrite()) {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write('0');
        }
    }
}
