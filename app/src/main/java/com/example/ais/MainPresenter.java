package com.example.ais;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

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


public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    private BaiduOCRService baiduOCRService;
    private GlobalData app;

    public MainPresenter(MainContract.View mView) {

        this.mView = mView;
        this.app = (GlobalData) this.mView.getActivity().getApplicationContext();

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
    public void getIOCRRecognitionResultByImage(Bitmap bitmap, String accessToken) {
        Bitmap bitmapAfterCompress = ImageCompressL(bitmap); //压缩bitmap
        System.out.println(bitmapAfterCompress.getByteCount());
        //System.out.println(bitmap.getByteCount());
        //base64编码
        //String encodeResult = bitmapToString(bitmap);
        String encodeResult = bitmapToString(bitmapAfterCompress); //压缩后base64编码
        //调用retrofit网络接口，注册JavaRX
        baiduOCRService.getIOCRRecognitionResultByImage(accessToken, encodeResult,IOCRUtils.templateSign)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IOCRRecognitionBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(IOCRRecognitionBean iocrRecognitionBean) {
                        Log.i("onNext", "getRecognitionResultByImage()回调成功");
                        Map<String, Object> resultMap = new HashMap<>();//存储结果
                        try {
                        Log.v("iOCR返回结果", iocrRecognitionBean.toString());
                        System.out.println("Error_msg = " + iocrRecognitionBean.getError_msg());
                        System.out.println("Error_code = " + iocrRecognitionBean.getError_code());
                            for(IOCRRecognitionBean.DataBean.RetBean retBean : iocrRecognitionBean.getData().getRet()) {
                                String word_name = retBean.getWord_name();
                                String word = retBean.getWord();
                                resultMap.put(word_name, word);
                            }
                        } catch(Exception e) {
                            String errorText = "图片识别失败：" + e.getMessage() +
                                    "(" + "Error_msg=" + iocrRecognitionBean.getError_msg() + ", " +
                                    "Error_code=" + iocrRecognitionBean.getError_code() + ")";
                            Toast.makeText(mView.getActivity(), errorText, Toast.LENGTH_LONG).show();
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
                    }
                });
    }

    @Override
    public void getAccessToken() {
        baiduOCRService.getAccessToken(IOCRUtils.GRANT_TYPE, IOCRUtils.CLIENT_ID, IOCRUtils.CLIENT_SECRET)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AccessTokenBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AccessTokenBean accessTokenBean) {

                        Log.i("onNext", "getAccessToken()回调成功");
                        try{
                            String accessToken = accessTokenBean.getAccess_token();
                            app.setAccessToken(accessToken);
                        } catch (Exception e) {
                            String errorText = "图片识别失败：" + e.getMessage() + "可能AccessToken获取失败";
                            Toast.makeText(mView.getActivity(), errorText, Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("onError", "getAccessToken()回调失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("onComplete","getAccessToken()回调完成");
                    }
                });
    }

    @Override
    public void getRecognitionResultByImage(Bitmap bitmap) {

    }

    /**
     * 图片Base64编码
     * @param bitmap
     * @return
     */
    private String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    /**
     * 图片压缩算法，解决百度IOCR上传限制4MB的问题
     * @param bitmap
     * @return
     */
    private Bitmap ImageCompressL(Bitmap bitmap) {
        double targetwidth = Math.sqrt(IOCRUtils.COMPRESS_RATIO); //压缩大小 4.00 * 1024 * 700
        if (bitmap.getWidth() > targetwidth || bitmap.getHeight() > targetwidth) {
            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 计算宽高缩放率
            double x = Math.max(targetwidth / bitmap.getWidth(), targetwidth
                    / bitmap.getHeight());
            // 缩放图片动作
            matrix.postScale((float) x, (float) x);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }
}
