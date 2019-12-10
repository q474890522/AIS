package com.example.ais;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainPresenter implements MainContract.Presenter{
    private MainContract.View mView;
    private BaiduOCRService baiduOCRService;

    public MainPresenter(MainContract.View mView) {

        this.mView = mView;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://aip.baidubce.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        //开启retrofit
        baiduOCRService = retrofit.create(BaiduOCRService.class);
    }

    /**
     * 图片上传到百度IOCR获取结果
     * @param bitmap 传入bitmap的图片
     */
    @Override
    public void getIOCRRecognitionResultByImage(Bitmap bitmap) {
        //base64编码
        String encodeResult = bitmapToString(bitmap);
        //调用retrofit网络接口，注册JavaRX
        baiduOCRService.getIOCRRecognitionResultByImage(IOCRUtils.ACCESS_TOKEN, encodeResult,IOCRUtils.templateSign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IOCRRecognitionBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(IOCRRecognitionBean iocrRecognitionBean) {
                        Log.i("onNext", "getRecognitionResultByImage()回调成功");
                        Log.v("iOCR返回结果", iocrRecognitionBean.toString());
                        Map<String, Object> resultMap = new HashMap<>();
                        for(IOCRRecognitionBean.DataBean.RetBean retBean : iocrRecognitionBean.getData().getRet()) {
                            String word_name = retBean.getWord_name();
                            String word = retBean.getWord();
                            resultMap.put(word_name, word);
                            //IOCRWordObject.setIOCRWordObject(word_name, word);
                        }
                        mView.updateUI(resultMap);

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("onError", "getRecognitionResultByImage()回调失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("onComplete","getRecognitionResultByImage()回调完成");
                        //System.out.println(IOCRWordObject.toWordString());
                    }
                });
    }

    @Override
    public void getAccessToken() {

    }

    @Override
    public void getRecognitionResultByImage(Bitmap bitmap) {

    }

    private String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }
}
