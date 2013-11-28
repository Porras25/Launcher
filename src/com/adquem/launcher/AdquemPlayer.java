package com.adquem.launcher;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaRecorder;

import android.util.AttributeSet;
import android.widget.VideoView;

public class AdquemPlayer extends VideoView implements OnPreparedListener, OnCompletionListener, OnErrorListener {
    private MediaPlayer mediaPlayer;
    public Boolean sound=true;
    public Boolean getSound() {
		return sound;
	}

	public void setSound(Boolean sound) {
		this.sound = sound;
	}

	public AdquemPlayer(Context context, AttributeSet attributes) {
        super(context, attributes);

        this.setOnPreparedListener(this);
        this.setOnCompletionListener(this);
        this.setOnErrorListener(this);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        this.mediaPlayer.setLooping(true);
        this.mute();
    }

  
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {  }

    public void mute() {
        this.setVolume(0);
        this.setSound(false);
    }

    public void unmute() {
        this.setVolume(100);
        this.setSound(true);
    }
    
    

    private void setVolume(int amount) {
        final int max = 100;
        final double numerator = max - amount > 0 ? Math.log(max - amount) : 0;
        final float volume = (float) (1 - (numerator / Math.log(max)));

        this.mediaPlayer.setVolume(volume, volume);
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {return false;}

}