package com.merlin.inquery.service;

import com.alibaba.fastjson.JSONObject;
import com.merlin.inquery.model.Result;
import com.merlin.inquery.model.WfQueryModel;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-14 16:00
 */
@Service
public class SendService {
    private static Logger logger = LoggerFactory.getLogger(SendService.class);

    private String indexUrl = "http://hb.122.gov.cn/views/inquiry.html";
    private String codeUrl = "http://hb.122.gov.cn/captcha";
    private String queryUrl = "http://hb.122.gov.cn/m/publicquery/vio";

    @Qualifier("httpClient")
    @Autowired
    CloseableHttpClient httpClient;
    @Autowired
    VerifyCodeFactory verifyCodeFactory;
    @Autowired
    ImgService imgService;



    /**
     * 打开首页，获取cookie
     */
    public void getIndexCookie(WfQueryModel wfQueryModel) {
        HttpGet getReq = new HttpGet(indexUrl);
        setIndexHttpGetHeaders(getReq);

        try (CloseableHttpResponse response = httpClient.execute(getReq)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                updateHeadersCookie(wfQueryModel, response.getAllHeaders());
            } else {
                logger.info("getIndexCookie return status code error ", JSONObject.toJSONString(response.getStatusLine()));
            }
        } catch (Exception e) {
            logger.error("getIndexCookie exception", e);
        }
    }

    /**
     * 获取验证码
     *
     * @throws InterruptedException
     */
    public void getVerifyCode(WfQueryModel wfQueryModel) throws Exception {
        HttpGet getReq = new HttpGet(buildVerifyCodeReqUrl());
        setVerifyCodeHttpGetHeaders(getReq, wfQueryModel);

        ByteArrayOutputStream baot = new ByteArrayOutputStream();
        try (CloseableHttpResponse response = httpClient.execute(getReq)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                InputStream instream = response.getEntity().getContent();
                byte[] bytes = new byte[1024];
                int n;
                while ((n = instream.read(bytes)) != -1) {
                    baot.write(bytes, 0, n); //将读取的字节流写入字节输出流
                }
                // 更新cookie
                updateHeadersCookie(wfQueryModel, response.getAllHeaders());
            } else {
                logger.info("getVerifyCode return status code error ", JSONObject.toJSONString(response.getStatusLine()));
            }
        } catch (Exception e) {
            logger.error("getVerifyCode exception", e);
        }

        //测试 图片输出到文件，方便对比
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/lq/Downloads/inquerypic.png");
        byte[] bytes = baot.toByteArray();
        fileOutputStream.write(bytes);
        fileOutputStream.flush();

        //图片优化处理
        byte[] afterDeal = imgService.dealImg(bytes); //待完善

        //调用验证码解析服务，获取验证码
        String verifyCode = verifyCodeFactory.getCode(afterDeal); //待完善
        if (verifyCode != null) {
            wfQueryModel.setVerifyCode(verifyCode); //设置验证码
        } else {
            throw new Exception("can not analyze verify code");
        }
    }



    /**
     * 真正执行查询
     *
     * @param wfQueryModel
     */
    public Result getQueryResult(WfQueryModel wfQueryModel) {
        HttpPost post = new HttpPost(queryUrl);
        buildQueryParam(post, wfQueryModel);

        try (CloseableHttpResponse response = httpClient.execute(post)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
                return JSONObject.parseObject(result, Result.class);
            } else {
                logger.info("getQueryResult return status code error ", JSONObject.toJSONString(response.getStatusLine()));
            }
        } catch (Exception e) {
            logger.error("get query result exception", e);
        }
        return null;
    }


    private void updateHeadersCookie(WfQueryModel wfQueryModel, Header[] allHeaders) {
        for (Header header : allHeaders) {
            String k = header.getName();
            if ("Set-Cookie".equals(k)) {
                String[] v = header.getValue().split(";")[0].split("=");
                wfQueryModel.getCookies().put(v[0], v[1]);
            }
        }
    }


    private void buildQueryParam(HttpPost post, WfQueryModel wfQueryModel) {
        //set header
        BasicCookieStore cookieStore = new BasicCookieStore();
        Map<String, String> ckMap = wfQueryModel.getCookies();
        for (Map.Entry<String, String> entry : ckMap.entrySet()) {
            cookieStore.addCookie(new BasicClientCookie(entry.getKey(), entry.getValue()));
        }
        post.setHeader("Cookie", cookieStore.toString());
        post.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        post.setHeader("Accept-Encoding", "gzip, deflate");
        post.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        post.setHeader("Host", "hb.122.gov.cn");
        post.setHeader("Origin", "http://hb.122.gov.cn/");
        post.setHeader("Referer", "http://hb.122.gov.cn/views/inquiry.html");
        post.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36");
        post.setHeader("X-Requested-With", "XMLHttpRequest");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        //set param
        Map<String, String> map = new HashMap<>();
        map.put("hphm1b", wfQueryModel.getCode());
        map.put("hphm", wfQueryModel.getDy()+wfQueryModel.getCode());
        map.put("fdjh", wfQueryModel.getFdjh6w());
        map.put("hpzl", wfQueryModel.getHplex());
        map.put("captcha", wfQueryModel.getVerifyCode());
        map.put("qm", "wf");
        map.put("page", "1");

        List<BasicNameValuePair> paramList = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        post.setEntity(new UrlEncodedFormEntity(paramList, Charset.forName("utf-8")));
    }

    private void setVerifyCodeHttpGetHeaders(HttpGet getReq, WfQueryModel wfQueryModel) {
        BasicCookieStore cookieStore = new BasicCookieStore();
        Map<String, String> ckMap = wfQueryModel.getCookies();
        for (Map.Entry<String, String> entry : ckMap.entrySet()) {
            cookieStore.addCookie(new BasicClientCookie(entry.getKey(), entry.getValue()));
        }
        getReq.setHeader("Cookie", cookieStore.toString());
        getReq.setHeader("Accept", "image/webp,image/apng,image/*,*/*;q=0.8");
        getReq.setHeader("Accept-Encoding", "gzip, deflate");
        getReq.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        getReq.setHeader("Host", "hb.122.gov.cn");
        getReq.setHeader("Referer", "http://hb.122.gov.cn/views/inquiry.html");
        getReq.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36");
    }

    private void setIndexHttpGetHeaders(HttpGet getReq) {
        getReq.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        getReq.setHeader("Accept-Encoding", "gzip, deflate");
        getReq.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        getReq.setHeader("Cache-Control", "max-age=0");
        getReq.setHeader("Host", "hb.122.gov.cn");
        getReq.setHeader("Referer", "http://hb.122.gov.cn/");
        getReq.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36");
    }


    private String buildVerifyCodeReqUrl() {
        String nowTimeParam = String.valueOf(System.currentTimeMillis());
        return codeUrl + "?nocache=" + nowTimeParam;
    }

    private byte[] dealOriginImg(byte[] bytes) throws IOException {

        return imgService.dealImg(bytes);
    }
}
