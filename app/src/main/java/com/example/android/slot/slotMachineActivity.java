package com.example.android.slot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import android.content.Intent;
import java.util.Random;


public class slotMachineActivity extends AppCompatActivity {
    private ImageView fruit1;
    private ImageView fruit2;
    private ImageView fruit3;
    private TextView displayPoints;
    private SeekBar seekBar;
    private Handler handler;
    private TextView startButton;
    private Update1 update1;
    private Update2 update2;
    private Update3 update3;

    private Button rulesButton;
    private boolean stopped;

    private int[] images = {R.drawable.pear, R.drawable.strawberry, R.drawable.grape, R.drawable.cherry};
    final Random rand = new Random();
    private long fruit1_speed;
    private long fruit2_speed;
    private long fruit3_speed;
    private int pnts;
    private int counter1;
    private int counter2;
    private int counter3;
    private int x;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_machine);


        fruit1 = findViewById(R.id.fruit1);
        fruit2 = findViewById(R.id.fruit2);
        fruit3 = findViewById(R.id.fruit3);
        displayPoints = findViewById(R.id.displayPoints);
        startButton = findViewById(R.id.startButton);
        rulesButton = (Button) findViewById(R.id.rulesButton);
        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                directions();
            }
        });
        seekBar = findViewById(R.id.seekbar);
        handler = new Handler();

        update1 = new Update1();
        update2 = new Update2();
        update3 = new Update3();
        stopped = true;
        counter1 = 0;
        counter2 = 1;
        counter3 = 2;
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        fruit1_speed = (seekBar.getProgress() + 1) * rand.nextInt(3) + 1;
                        fruit2_speed = (seekBar.getProgress() + 1) * rand.nextInt(3) + 1;
                        fruit3_speed = (seekBar.getProgress() + 1) * rand.nextInt(3) + 1;

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );
        fruit1.setImageDrawable(getResources().getDrawable(images[0]));
        fruit2.setImageDrawable(getResources().getDrawable(images[1]));
        fruit3.setImageDrawable(getResources().getDrawable(images[2]));


        if(savedInstanceState != null){
            displayPoints.setText(savedInstanceState.getString("Woohoo Points!"));
            pnts=Integer.parseInt(displayPoints.getText().toString());
        }
    }
    public void startButtonPressed(View v){
        if(stopped==true){
            startButton.setText("Stop");
            fruit1_speed=(seekBar.getProgress()+1)*rand.nextInt(3)+1;
            fruit2_speed=(seekBar.getProgress()+1)*rand.nextInt(3)+1;
            fruit3_speed=(seekBar.getProgress()+1)*rand.nextInt(3)+1;
            handler.postDelayed(update1, fruit1_speed*100);
            handler.postDelayed(update2, fruit2_speed*100);
            handler.postDelayed(update3, fruit3_speed*100);
            stopped = false;
        }else{
            startButton.setText("start");
            handler.removeCallbacks(update1);
            handler.removeCallbacks(update2);
            handler.removeCallbacks(update3);

            if(counter1 == counter2 && counter2 == counter3){
                Toast.makeText(this, " Woohoo, 50 points!", Toast.LENGTH_LONG).show();
                pnts += 50;
            }else if (counter1 == 0 || counter2 == 0 || counter3 == 0){
                Toast.makeText(this, "Woohoo, 10 points!", Toast.LENGTH_LONG).show();
                pnts += 10;
            }
            displayPoints.setText(pnts + "");
            System.out.println(counter1);
            System.out.println(counter2);
            System.out.println(counter3);
            stopped = true;
        }
    }

    private class Update1 implements Runnable{
        @Override
        public void run(){
            if(counter1 < 3){
                counter1 += 1;
                fruit1.setImageDrawable(getResources().getDrawable(images[counter1]));
            }else{
                counter1 = 0;
                fruit1.setImageDrawable(getResources().getDrawable(images[0]));
            }
            handler.postDelayed(update1, fruit1_speed*100);
        }
    }
    private class Update2 implements Runnable{
        @Override
        public void run(){
            if(counter2 < 3){
                counter2 += 1;
                fruit2.setImageDrawable(getResources().getDrawable(images[counter2]));
            }else{
                counter2 = 0;
                fruit2.setImageDrawable(getResources().getDrawable(images[0]));
            }
            handler.postDelayed(update1, fruit2_speed*100);
        }
    }
    private class Update3 implements Runnable{
        @Override
        public void run(){
            if(counter3 < 3){
                counter3 += 1;
                fruit3.setImageDrawable(getResources().getDrawable(images[counter3]));
            }else{
                counter3 = 0;
                fruit3.setImageDrawable(getResources().getDrawable(images[0]));
            }
            handler.postDelayed(update3, fruit3_speed*100);
        }
    }
    public void directions(){
        Intent intent = new Intent(this, directionsActivity.class);
        intent.putExtra("POINTS", pnts);
        startActivityForResult(intent, 1);
    }
}
