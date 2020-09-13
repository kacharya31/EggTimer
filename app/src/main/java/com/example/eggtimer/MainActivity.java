package com.example.eggtimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.sql.Time;

public class MainActivity extends AppCompatActivity {

    int totalTime = 30;

    MediaPlayer mediaPlayer;

    TextView timeTextView;

    SeekBar seekBar;

    Button startButton;

    CountDownTimer countDowntimer;

    public String calculateTime(int timeTotal) {
        String time;
        int minutes;
        int seconds;

        minutes = (int) Math.floor(timeTotal /60);
        seconds = timeTotal % 60;

        if (minutes < 10) {
            if (seconds < 10) {
                time = "0" + Integer.toString(minutes) + ":" + "0" + Integer.toString(seconds);
                return time;
            } else {
                time = "0" + Integer.toString(minutes) + ":" + Integer.toString(seconds);
                return time;
            }
        } else {
            if (seconds < 10) {
                time = Integer.toString(minutes) + ":" + "0" + Integer.toString(seconds);
                return time;
            } else {
                time = Integer.toString(minutes) + ":" + Integer.toString(seconds);
                return time;
            }
        }
    }

    public void buttonFunction() {
        startButton.setText("START");
        seekBar.setProgress(30);
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        seekBar.setEnabled(true);
        timeTextView.setText("00:30");
    }

    public void onClick(View view) {

        startButton = (Button) findViewById(R.id.startButton);

        if (startButton.getText() == "STOP") {
            buttonFunction();
            countDowntimer.cancel();
            if (mediaPlayer.isPlaying() == true) {
                mediaPlayer.pause();
            }
        } else {
            if (mediaPlayer.isPlaying() == true) {
                mediaPlayer.pause();
            }

            startButton.setText("STOP");

            seekBar.setEnabled(false);

            seekBar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

            int timeMilliseconds = totalTime * 1000;

            countDowntimer = new CountDownTimer(timeMilliseconds, 1000) {
                @Override
                public void onTick(long milliSecondsUntilDone) {
                    timeTextView.setText(calculateTime((int) (milliSecondsUntilDone / 1000)));
                }

                @Override
                public void onFinish() {
                    seekBar.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });

                    buttonFunction();

                    mediaPlayer.start();

                }
            }.start();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeTextView = findViewById(R.id.timerTextView);
        seekBar = findViewById(R.id.timeSeekBar);

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);

        timeTextView.setText("00:30");

        seekBar.setMin(0);
        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                totalTime = i;
                timeTextView.setText(calculateTime(i));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer.isPlaying() == true) {
                    mediaPlayer.pause();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}