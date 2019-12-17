package com.example.ais;



import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ais.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.ais.BaiduiOCR;
import com.example.ais.Base64Util;

import org.json.JSONObject;
import com.example.ais.SaveToExcel;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.example.ais.GetDir.getExcelDir;


public class MainActivity extends AppCompatActivity implements MainContract.View{


    private Context mContext;
    private TextView textView;
    private ImageView imageView;
    private Button button;
    File mTmpFile;
    Uri imageUri;
    private LinearLayout save;
    private LinearLayout takepic;
    private LinearLayout commit;
    private MainPresenter mainPresenter;//
    private String excelPath;
    private SaveToExcel saveToExcel;
    private String Assets_name;
    private String Assets_numb;
    private String base_station_name;
    private String Assets_type;
    private String Manufacturer;
    private String numbs;
    private String state;
    @InjectView(R.id.基站名称)
    EditText 基站名称;
    @InjectView(R.id.资产名称)
    EditText 资产名称;
    @InjectView(R.id.资产标签号)
    EditText 资产标签号;
    @InjectView(R.id.规格型号)
    EditText 规格型号;
    @InjectView(R.id.生产厂商)
    EditText 生产厂商;
    @InjectView(R.id.数量)
    EditText 数量;




    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int MY_PERMISSIONS_REQUEST = 1;
    private String zhuangtai = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.inject(this);
        excelPath = getExcelDir()+ File.separator+"demo.xls";
        saveToExcel = new SaveToExcel(this,excelPath);
        mContext = this;
        // 初始化控件
        Spinner spinner = (Spinner) findViewById(R.id.状态);
        // 建立数据源
        String[] mItems = getResources().getStringArray(R.array.professionals);
        // 建立Adapter并且绑定数据源
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner .setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String[] languages = getResources().getStringArray(R.array.professionals);
                //Toast.makeText(MainActivity.this, "你点击的是:"+languages[pos], Toast.LENGTH_LONG).show();
                zhuangtai = languages[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        mainPresenter = new MainPresenter(this);//
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        save = (LinearLayout)findViewById(R.id.save);
        save.setOnClickListener(onClickListener);
        takepic = (LinearLayout)findViewById(R.id.takepic);
        takepic.setOnClickListener(onClickListener);
        commit = (LinearLayout)findViewById(R.id.commit);
        commit.setOnClickListener(onClickListener);

    }


    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.save:
                    Log.d("zt",zhuangtai);
                    Assets_name =资产名称.getText().toString().trim();
                    Assets_numb =资产标签号.getText().toString().trim();
                    Assets_type =规格型号.getText().toString().trim();
                    Manufacturer =生产厂商.getText().toString().trim();
                    base_station_name =基站名称.getText().toString().trim();
                    numbs =数量.getText().toString().trim();
                    state =zhuangtai.trim();
                    if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST);
                    } else {

                        saveToExcel.writeToExcel(base_station_name,Assets_name,Assets_numb,Assets_type,Manufacturer,numbs,state);
                        //Assets资产 base_station基站
                    }
                    资产名称.setText("");
                    资产标签号.setText("");
                    规格型号.setText("");
                    生产厂商.setText("");
                    基站名称.setText("");
                    数量.setText("");
                    Toast.makeText(MainActivity.this, "save", Toast.LENGTH_LONG);
                    break;
                case R.id.takepic:
                    takePhoto();
                    Toast.makeText(MainActivity.this, "takepic", Toast.LENGTH_LONG);
                    break;
                case R.id.commit:
                    Toast.makeText(MainActivity.this, "commit", Toast.LENGTH_LONG);
                    break;
                default:
                    break;
            }


        }
    };

    /**
     * 更新UI函数，在MainPresenter的getRecognitionResultByImage()回调成功时调用
     * @param map
     */
    @Override
    public void updateUI(Map<String, Object> map) {
        TextView textView1 = findViewById(R.id.资产名称);
        textView1.setText(String.valueOf(map.get("资产名称")));
        TextView textView2 = findViewById(R.id.规格型号);
        textView2.setText(String.valueOf(map.get("规格型号")));
        TextView textView3 = findViewById(R.id.资产标签号);
        textView3.setText(String.valueOf(map.get("资产标签号")));//需要换模板后更改
        textView.setText("识别结果：" + String.valueOf(map));
    }

    @Override
    public Context getActivity() {
        return MainActivity.this;
    }

    private boolean hasPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CODE);
            return false;
        }else {
            return true;
        }
    }

    private void takePhoto(){

        if (!hasPermission()) {
            return;
        }

        Intent intent = new Intent();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/img/";
        File file = new File(path);
        if (!file.exists()) {
                new File(path).mkdirs();
        }
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        String filename = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mTmpFile = new File(path, filename + ".jpg");
        mTmpFile.getParentFile().mkdirs();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String authority = getPackageName() + ".provider";
            imageUri = FileProvider.getUriForFile(this, authority, mTmpFile);
        } else {
            imageUri = Uri.fromFile(mTmpFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        startActivityForResult(intent, CAMERA_REQUEST_CODE);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        return;
                    }
                }
                takePhoto();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap photo = BitmapFactory.decodeFile(mTmpFile.getAbsolutePath());
            System.out.println("PhotoByteCount = " + photo.getByteCount());
            try {
                mainPresenter.getIOCRRecognitionResultByImage(photo);//识别拍照照片
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "图片识别异常:"+e.getMessage(), Toast.LENGTH_LONG).show();
            }
            //textView.setText(getResult(getBitmapByte(photo)));
            imageView.setImageBitmap(photo);
        }
    }


    public byte[] getBitmapByte(Bitmap bitmap){   //将bitmap转化为byte[]类型也就是转化为二进制
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        return out.toByteArray();
    }

}
