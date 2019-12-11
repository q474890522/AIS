package com.example.ais;

import android.graphics.Bitmap;

import java.util.Map;


public interface MainContract {

    interface View {
        void updateUI(Map<String, Object> map);
    }

    interface Presenter{
        void getAccessToken();
        void getRecognitionResultByImage(Bitmap bitmap);
        void getIOCRRecognitionResultByImage(Bitmap bitmap);
    }

}
