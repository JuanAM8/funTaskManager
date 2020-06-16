package com.example.testingtfg.minigames;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;
import java.util.HashMap;
/*Clase que gestiona el sonido de los minijuegos*/
public class SoundsManager {

    //region Constantes y Parámetros
    private static final int MAX_STREAMS = 10;
    private HashMap<SoundEvent, Integer> soundsMap;
    private Context context;
    private SoundPool soundPool;
    //endregion

    //Constructor
    public SoundsManager(Context context){
        this.context = context;
        loadSounds();
    }

    //Ejecuta un sonido concreto en función al parámetro recibido
    public void playSound(SoundEvent event){
        Integer soundId = soundsMap.get(event);
        if (soundId != null){
            soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    //Carga todos los sonidos a usar
    private void loadSounds() {
        createSoundPool();
        soundsMap = new HashMap<SoundEvent, Integer>();
        //Todos estos sonidos son propiedad de sus creadores
        //Title: Explosion11
        //Creator: V-ktor
        //Source: https://freesound.org/people/V-ktor/sounds/435414/
        //License: CC0 1.0 Universal Public Domain Dedication
        //https://creativecommons.org/publicdomain/zero/1.0/
        loadEventSound(context, SoundEvent.MeteorHit, "mg1_hit.wav");
        //Title: Retro Laser Shot
        //Creator: MATRIXXX_
        //Source: https://freesound.org/people/MATRIXXX_/sounds/415058/
        //License: Creative Commons Attribution 3.0 https://creativecommons.org/licenses/by/3.0/
        //No modifications were made to the source file
        loadEventSound(context, SoundEvent.Shoot, "mg1_shoot.wav");
        //Title: Glass17
        //Creator: Craxic
        //Source: //https://freesound.org/people/Craxic/sounds/204691/
        //License: Creative Commons Attribution 3.0 https://creativecommons.org/licenses/by/3.0/
        //No modifications were made to the source file
        loadEventSound(context, SoundEvent.Glass, "mg2_glass.wav");
        //Title: 8-bit Video Game Sounds » Coins 1
        //Creator: ProjectsU012
        //Source: https://freesound.org/people/ProjectsU012/sounds/341695/
        //License: Creative Commons Attribution 3.0 https://creativecommons.org/licenses/by/3.0/
        //No modifications were made to the source file
        loadEventSound(context, SoundEvent.Coin, "mg3_coin.wav");
        //Title:  failure 2
        //Creator: Leszek_Szary
        //Source: https://freesound.org/people/Leszek_Szary/sounds/171672/
        //License: CC0 1.0 Universal Public Domain Dedication
        //https://creativecommons.org/publicdomain/zero/1.0/
        loadEventSound(context, SoundEvent.Defeat, "mg3_fail.wav");
    }

    //Crea el gestor de sonido
    private void createSoundPool(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }
        else {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(MAX_STREAMS)
                    .build();
        }
    }

    //Carga un sonido asociado a un evento
    private void loadEventSound(Context context, SoundEvent event, String... filename){
        try{
            AssetFileDescriptor descriptor = context.getAssets().
                    openFd("sound/" + filename[0]);
            int soundId = soundPool.load(descriptor, 1);
            soundsMap.put(event, soundId);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
