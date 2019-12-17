package com.example.ais;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.Map;


public interface MainContract {

    interface View {
        void updateUI(Map<String, Object> map);
        Context getActivity();
    }

    interface Presenter{
        void getAccessToken();
        void getRecognitionResultByImage(Bitmap bitmap);
        void getIOCRRecognitionResultByImage(Bitmap bitmap);
    }

}
