package com.example.ais;


import java.net.URLEncoder;

import com.example.ais.Base64Util;
import com.example.ais.FileUtil;
import com.example.ais.HttpUtil;

public class BaiduiOCR {
	public static void getResult(byte[] imgData) {
        /**
         * ��Ҫ��ʾ���������蹤����
         * FileUtil,Base64Util,HttpUtil,GsonUtils���
         * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
         * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
         * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
         * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
         * ����
         */
        // iocrʶ��apiUrl
        String recogniseUrl = "https://aip.baidubce.com/rest/2.0/solution/v1/iocr/recognise";


        //String filePath = "D:\\Workspace\\Javawork\\baiduocr\\res\\test.jpg";
        try {
                //byte[] imgData = FileUtil.readFileByBytes(filePath);
                String imgStr = Base64Util.encode(imgData);
                // ����ģ�����
                String recogniseParams = "templateSign=7d59619c3d1e98c44f61a98bd81994a7&image=" + URLEncoder.encode(imgStr, "UTF-8");
                // �������������
                //String classifierParams = "classifierId=your_classfier_id&image=" + URLEncoder.encode(imgStr, "UTF-8");
                
                
                String accessToken = auth.getAuth();
                // ����ģ��ʶ��
                String result = HttpUtil.post(recogniseUrl, accessToken, recogniseParams);
                // ���������ʶ��
                // String result = HttpUtil.post(recogniseUrl, accessToken, classifierParams);
                
                System.out.println(result);
                getJson.Json(result);
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
}
