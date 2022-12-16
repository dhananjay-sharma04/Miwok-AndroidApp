package com.example.android.miwok;

import android.widget.ImageView;

public class Word {
    private String mDefaultTranslation;
    private String mGujaratiTranslation;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private int mAudioResourceID;

    public Word (String DefaultTranslation, String GujaratiTranslation, int audioResourceID){
        mDefaultTranslation = DefaultTranslation;
        mGujaratiTranslation = GujaratiTranslation;
        mAudioResourceID = audioResourceID;
    }

    public Word (String DefaultTranslation, String GujaratiTranslation, int ImageResourceId, int audioResourceID){
        mDefaultTranslation = DefaultTranslation;
        mGujaratiTranslation = GujaratiTranslation;
        mImageResourceId = ImageResourceId;
        mAudioResourceID = audioResourceID;
    }

//    get method for default language
    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }
//    get method for Gujarati language
    public String getGujaratiTranslation(){
        return mGujaratiTranslation;
    }
//    to Check image there or not
    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
//    get method for image
    public int getImageResourceId() {
        return mImageResourceId;
    }
//    get method for audio File
    public int getAudioResourceID() {
        return mAudioResourceID;
    }
}