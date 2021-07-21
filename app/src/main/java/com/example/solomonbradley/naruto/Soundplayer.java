package com.example.solomonbradley.naruto;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class Soundplayer{

    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX=2;

    private static SoundPool soundPool;
    private static int hitSound;
    private static int overSound;
    private static int jumpSound;
    private static int pinkSound;
    public Soundplayer(Context context){


       // SoundPool(int maxStreams,int streamType,int srcQuality)
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            audioAttributes=new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();
        }else {
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }

    hitSound=soundPool.load(context,R.raw.hit,1);
        overSound=soundPool.load(context,R.raw.over,1);
        jumpSound=soundPool.load(context,R.raw.jump,1);
        pinkSound=soundPool.load(context,R.raw.pink,1);


    }
    public void playHitSound(){

        //play(int SoundID, float leftVolume,float rightVolume,int priority,int loop,float rate)
        soundPool.play(hitSound,1.0f,1.0f,1,0,1.0f);
    }
    public void playOverSound(){

        soundPool.play(overSound,1.0f,1.0f,3,0,1.0f);
    }
    public void playJumpSound(){
        soundPool.play(jumpSound,1.0f,1.0f,0,0,1.0f);
    }
    public void playPinkSound(){
        soundPool.play(pinkSound,1.0f,1.0f,2,0,1.0f);
    }
}