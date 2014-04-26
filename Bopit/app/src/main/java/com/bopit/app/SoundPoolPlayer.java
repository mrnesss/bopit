package com.bopit.app;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by diegoarmandocontrerashernandez on 26/04/14.
 */
public class SoundPoolPlayer {
    private SoundPool mShortPlayer= null;
    private HashMap mSounds = new HashMap();

    public SoundPoolPlayer(Context pContext)
    {
        // setup Soundpool
        this.mShortPlayer = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);


        mSounds.put(R.raw.sflip, this.mShortPlayer.load(pContext, R.raw.sflip, 0));
        mSounds.put(R.raw.sflip, this.mShortPlayer.load(pContext, R.raw.shake, 1));
        mSounds.put(R.raw.sflip, this.mShortPlayer.load(pContext, R.raw.sswipe, 2));
        mSounds.put(R.raw.sflip, this.mShortPlayer.load(pContext, R.raw.tap, 3));
        mSounds.put(R.raw.sflip, this.mShortPlayer.load(pContext, R.raw.turn, 4));
        mSounds.put(R.raw.sflip, this.mShortPlayer.load(pContext, R.raw.twist, 5));

    }

    public void playShortResource(int piResource) {
        int iSoundId = (Integer) mSounds.get(piResource);
        this.mShortPlayer.play(iSoundId, 0.99f, 0.99f, 0, 0, 1);
    }

    // Cleanup
    public void release() {
        // Cleanup
        this.mShortPlayer.release();
        this.mShortPlayer = null;
    }
}