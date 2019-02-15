package com.merlin.inquery.service.codeverify;

import com.alibaba.fastjson.JSONObject;
import com.merlin.inquery.model.VerifyCode;
import com.merlin.inquery.model.txocr.TxOcrDataItemList;
import com.merlin.inquery.model.txocr.TxOcrDataItemWord;
import com.merlin.inquery.model.txocr.TxOcrResponse;
import com.merlin.inquery.util.MD5util;
import com.merlin.inquery.util.RandomUtil;
import com.merlin.inquery.util.TimeUtil;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 腾讯通用ocr识别
 * 详见文档 https://ai.qq.com/doc/ocrgeneralocr.shtml
 *
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-15 10:29
 */

@Service
public class TxOcrVerifyService implements VerifyCodeService {
    private static final Logger logger = LoggerFactory.getLogger(TxOcrVerifyService.class);
    private static final String tx_common_ocr_url = "https://api.ai.qq.com/fcgi-bin/ocr/ocr_generalocr";

    private static final String app_id = "002111986544";
    private static final String app_key = "005cy8k1d8XsnQdkXT";

    @Qualifier("httpClient")
    @Autowired
    CloseableHttpClient httpClient;

    @Override
    public List<VerifyCode> verifyCode(byte[] img) throws Exception{
        HttpPost post = new HttpPost(tx_common_ocr_url);
        List<BasicNameValuePair> params = new ArrayList<>();
        String timeStamp = TimeUtil.getCurrentSecond();
        String nonceString = RandomUtil.randomStr();
        String imgString = buildImg(img);

        params.add(new BasicNameValuePair("app_id", app_id));
        params.add(new BasicNameValuePair("time_stamp", timeStamp));
        params.add(new BasicNameValuePair("nonce_str", nonceString));
        params.add(new BasicNameValuePair("sign", buildSign(app_id, timeStamp, nonceString, imgString)));
        params.add(new BasicNameValuePair("image", imgString));
        post.setEntity(new UrlEncodedFormEntity(params, Charset.forName("utf-8")));

        List<VerifyCode> rs = new ArrayList<>(4);
        try (CloseableHttpResponse response = httpClient.execute(post)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                String resp = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
                TxOcrResponse txOcrResponse = JSONObject.parseObject(resp, TxOcrResponse.class);

                if (txOcrResponse.getRet() == 0) { //返回成功
                    //提取每个返回结果，组装成内部对象
                    for (TxOcrDataItemList dataItemList : txOcrResponse.getData().getItem_list()) {
                        for (TxOcrDataItemWord word : dataItemList.getWords()) {
                            VerifyCode verifyCode = new VerifyCode();
                            verifyCode.setWord(word.getCharacter());
                            verifyCode.setConfidence(word.getConfidence());
                            rs.add(verifyCode);
                        }
                    }
                } else {
                    logger.error("tx ocr get verify code error {}", resp);
                }
            }

        } catch (Exception e) {
            logger.error("tx ocr exception", e);
        }

        return rs;
    }

    /**
     * 构造签名
     *
     * @param appId 应用id
     * @param timeStamp 时间戳
     * @param nonceString 随机码
     * @param imgString 图像
     * @return
     */
    private String buildSign(String appId, String timeStamp, String nonceString, String imgString) throws Exception {
        //排序
        Map<String,String> signMap = new TreeMap<>();
        signMap.put("app_id",appId );
        signMap.put("image",imgString );
        signMap.put("time_stamp",timeStamp );
        signMap.put("nonce_str",nonceString);


        StringBuilder sbuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : signMap.entrySet()) {
            sbuilder.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), Charset.forName("utf-8").name())).append("&");
        }
        sbuilder.append("app_key=").append(app_key);

        return MD5util.md5(sbuilder.toString()).toUpperCase();
    }

    /**
     * 转换成base64编码数据
     *
     * @param img
     * @return
     */
    private String buildImg(byte[] img) {
        return Base64.getEncoder().encodeToString(img);
    }
}
