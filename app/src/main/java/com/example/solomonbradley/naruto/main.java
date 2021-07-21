package com.example.solomonbradley.naruto;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.Math;
import java.util.Timer;
import java.util.TimerTask;

public class main extends AppCompatActivity {
private TextView scoreLabel;
    private  TextView startLabel;
    private ImageView box;
    private TextView lifeLabel;
    private ImageView orange;
    private ImageView pink;
    private ImageView black;
    private ImageView black2;
    private ImageView tree1;
    private ImageView tree2;

    //size
    private int frameHeight;
    private int boxSize;
    private int screenWidth;
    private int screenHeight;

    //position
    private int boxY;
    private int orangeX;
    private int orangeY;
    private int pinkX;
    private int pinkY;
    private int blackX;
    private int blackY;
    private int black2X;
    private int black2Y;
    private int tree1X;
    private int tree2X;
    private int tree1Y;
    private int tree2Y;

    //Speed
    private int boxSpeed =Math.round(screenHeight/60F);
    private int orangeSpeed=Math.round(screenWidth/60F);
    private int pinkSpeed=Math.round(screenWidth/36F);
    private int blackSpeed=Math.round(screenWidth/45F);


private int life=3;
    //Speed box:20 orange:12 pink:20 black:16


   

    //Score
    private int score=0;

    //Initialise class
    private Handler handler = new Handler();
    private Timer timer= new Timer();
    private Soundplayer sound;

    //Status Check
    private boolean action_flg=false;
    private boolean start_flg=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound=new Soundplayer(this);

        tree1=(ImageView)findViewById(R.id.tree1);
        tree2=(ImageView)findViewById(R.id.tree2);
        scoreLabel=(TextView)findViewById(R.id.scoreLabel);
        startLabel=(TextView)findViewById(R.id.startLabel);
        lifeLabel=(TextView)findViewById(R.id.lifeLabel);
        box=(ImageView)findViewById(R.id.box);
        orange=(ImageView)findViewById(R.id.orange);
        pink=(ImageView)findViewById(R.id.pink);

        black2=(ImageView)findViewById(R.id.kunai);
        orange.setX(-80);
        orange.setY(-80);
        pink.setX(-80);
        pink.setY(-80);


        black2.setX(-80);
        black2.setY(-80);
//Get Screen Size
        WindowManager wm= getWindowManager();
        Display disp=wm.getDefaultDisplay();
        Point size=new Point();
        disp.getSize(size);
        screenWidth=size.x;
        screenHeight=size.y;
       tree1.setX(-80);
        tree1.setY(-80);

        tree2.setX(-80);
        tree2.setY(-80);
       tree1X=1000;
        tree2X=500;

    }
    public void changePos(){


        //Trees
        tree1X-=10;
        tree2X-=10;
        if (tree1X < -900 ){

            tree1X=screenWidth;

        }
        if (tree2X < -900 ){

            tree2X=screenWidth;

        }
        tree1Y=(int)Math.floor(1*(frameHeight -tree1.getHeight()));
        tree2Y=(int)Math.floor(1*(frameHeight -tree2.getHeight()));
        tree1.setX(tree1X);
        tree2.setX(tree2X);
        tree1.setY(tree1Y);
        tree2.setY(tree2Y);

        hitCheck();
        //Orange
        orangeX-=12;
        if (orangeX < 0 ){

            orangeX=screenWidth+20;
            orangeY=(int)Math.floor(Math.random()*(frameHeight -orange.getHeight()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);



        //kunai

        black2X-=24;
        if(black2X<0){
            black2X=screenWidth +10;
            black2Y=(int)Math.floor(Math.random()*(frameHeight-orange.getHeight()));
        }
        black2.setX(black2X);
        black2.setY(black2Y);

        //Pink
        pinkX-=20;
        if(pinkX<0){
            pinkX=screenWidth+5000;
            pinkY=(int)Math.floor(Math.random()*(frameHeight-pink.getHeight()));
        }
        pink.setX(pinkX);
        pink.setY(pinkY);


        //MoveBox
        if(action_flg==true){
            //Touching
            boxY-=22;
        }else{
            //Releasing
            boxY+=22;
        }
        //check box pos
        if(boxY<0)
            boxY=0;

        if(boxY>frameHeight-boxSize)
            boxY=frameHeight-boxSize;

        box.setY(boxY);
    scoreLabel.setText("Score: "+score);
        lifeLabel.setText("Life: "+life);
    }

    public void hitCheck(){
        //If the center of the ball is in the box, it counts as a hit
        //Orange
        int orangeCenterX=orangeX + orange.getWidth()/2;
        int orangeCenterY=orangeY+orange.getHeight()/2;
        //0<=orangeCenterX<=boxWidth
        //boxY<=OrangeCenterY<=boxy+boxHeight

        if(0<=orangeCenterX && orangeCenterX <= boxSize && boxY <= orangeCenterY && orangeCenterY <= boxY + boxSize){
            orangeX=-10;
            score+=10;
            sound.playHitSound();

        }
        //pink

        int pinkCenterX=pinkX+pink.getWidth()/2;
        int pinkCenterY=pinkY+pink.getHeight()/2;
        if(0<=pinkCenterX && pinkCenterX <= boxSize && boxY <= pinkCenterY && pinkCenterY <= boxY + boxSize){
            pinkX=-10;
            score+=30;
            sound.playPinkSound();

        }
        //Black

        //Kunai
        int black2CenterX=black2X+black2.getWidth()/4;
        int black2CenterY=black2Y+black2.getHeight()/2;
        if(0<=black2CenterX && black2CenterX <= boxSize && boxY <= black2CenterY && black2CenterY <= boxY + boxSize){
            if(life==1) {
                //Stop timer
                timer.cancel();
                timer = null;
                sound.playOverSound();

                Intent intent = new Intent(getApplicationContext(), result.class);
                intent.putExtra("SCORE", score);
                startActivity(intent);
            }else {
                sound.playOverSound();
                life = life - 1;
                lifeLabel.setText("Life: " + life);
                black2X=-80;
            }

        }

    }
    public boolean onTouchEvent(MotionEvent me){
        if(start_flg==false){
            start_flg=true;
            FrameLayout frame=(FrameLayout)findViewById(R.id.frame);
            frameHeight=frame.getHeight();
            boxY=(int)box.getY();
            boxSize=box.getHeight();
            startLabel.setVisibility(View.GONE);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable(){
                        @Override
                        public void run(){
                            changePos();
                        }

                    });
                }
            },0,20);


        }else{
            if(me.getAction()== MotionEvent.ACTION_DOWN){
                sound.playJumpSound();
                action_flg=true;
            }else if(me.getAction()==MotionEvent.ACTION_UP){
                action_flg=false;
        }
            }


        return true;
    }
    //Disable Return button
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }


}
