package com.tseong.learning.patterns._09_adapter;

public class Mp4Player implements AdavancedMediaPlayer {

    @Override
    public void playVlc(String fileName) {
        // do nothing
    }

    @Override
    public void playMp4(String fileName) {
        System.out.println("Playing mp4 file. Name:" + fileName);
    }
}
