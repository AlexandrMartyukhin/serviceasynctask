package ru.minilan.serviceasynctask;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    //private Handler handler = new Handler();
    TextView textView;
    TextView textView2;
    EditText editText;
    TextView textViewTemp;
    Button button;
    Button buttonGetWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewsFinder();
        setListeners();


    }

    private void viewsFinder() {
        textView = findViewById(R.id.textview);
        textView2 = findViewById(R.id.textview2);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        buttonGetWeather = findViewById(R.id.buttonGetWeather);
        textViewTemp = findViewById(R.id.textViewTemp);
    }

    private void setListeners() {
        button.setOnClickListener(onClickRunTaskListener);
        buttonGetWeather.setOnClickListener(onClickGetWeatherListener);
    }


    View.OnClickListener onClickRunTaskListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TaskMaker taskMaker = new TaskMaker(new TaskMaker.OnTaskListener() {
                @Override
                public void onStartup() {
                    textView2.setText("Task started...");
                }

                @Override
                public void onStatusProgress() {
                    textView2.setText("...task working...");
                }

                @Override
                public void onComplete() {
                    textView2.setText("Done!");
                }
            });
            taskMaker.createTask(1, 5);
        }
    };


    View.OnClickListener onClickGetWeatherListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            PendingIntent pendingIntent = createPendingResult(0, new Intent(), 0);
            Intent intent = new Intent(MainActivity.this, GetWeatherDataService.class);
            //String town = editText.getText().toString();
            intent.putExtra("TOWN", editText.getText().toString());
            intent.putExtra("PI", pendingIntent);
            startService(intent);

            Log.i("MyTAG", "town in onClick = " + editText.getText().toString());


        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 0) {
            int temp;
            temp = data.getExtras().getInt("TEMP");
            textViewTemp.setText(String.valueOf(temp));
        }

    }
}
