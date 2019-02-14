package com.merlin.inquery.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-14 16:02
 */
@Component
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);


    @Bean(name = "httpClient")
    public CloseableHttpClient init() throws IOReactorException {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(5);
        connManager.setDefaultMaxPerRoute(5);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000) // 请求超时时间
                .setSocketTimeout(5000) // 等待数据超时时间
                .setConnectionRequestTimeout(5000) // 连接不够用时等待超时时间
                .build();
        return HttpClients.custom()
                .setConnectionManager(connManager)// 设置连接池
                .setDefaultRequestConfig(requestConfig) // 设置超时时间
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy() { // 设置连接存活时间
                    @Override
                    public long getKeepAliveDuration(final HttpResponse response, final HttpContext context) {
                        long keepAlive = super.getKeepAliveDuration(response, context);
                        if (keepAlive == -1) {
                            keepAlive = 5000;
                        }
                        return keepAlive;
                    }
                })
                .setConnectionTimeToLive(5000L, TimeUnit.MILLISECONDS) // 设置连接存活时间
                .evictIdleConnections(5L, TimeUnit.SECONDS) // 关闭无效和空闲的连接
                .build();
    }


}
