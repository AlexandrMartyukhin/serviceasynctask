package ru.minilan.serviceasynctask;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    //private Handler handler = new Handler();
    TextView textView;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textview);
        textView2 = findViewById(R.id.textview2);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(onClickListener);
    }



    View.OnClickListener onClickListener = new View.OnClickListener() {
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
}
