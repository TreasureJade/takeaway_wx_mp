package com.cins.hobo.takeaway_wx_mp.util;

import com.cins.hobo.takeaway_wx_mp.dto.ConsumerOrderDetailDTO;
import com.cins.hobo.takeaway_wx_mp.entry.ConsumerOrderTotal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : hobo
 * @className : PrinterUtil
 * @date: 2021/3/1
 * @description: TODO
 */
@Slf4j
public class PrinterUtil {
    private static final String url = "http://api.feieyun.cn/Api/Open/";
    public static final String user = "1056024860@qq.com";
    private static final String ukey = "****";
    public static final String sn = "****";

    public static String printOrder(List<ConsumerOrderDetailDTO> detailList, ConsumerOrderTotal orderTotal) {
        String content = "";
        content = setOrderForm(detailList,orderTotal);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(30000)
                .setConnectTimeout(30000)
                .build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();

        HttpPost post = new HttpPost(url);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("user", user));
        String STIME = String.valueOf(System.currentTimeMillis() / 1000);
        nvps.add(new BasicNameValuePair("stime", STIME));
        nvps.add(new BasicNameValuePair("sig", signature(user, ukey, STIME)));
        nvps.add(new BasicNameValuePair("apiname", "Open_printMsg"));
        nvps.add(new BasicNameValuePair("sn", sn));
        nvps.add(new BasicNameValuePair("content", content));
        nvps.add(new BasicNameValuePair("time", "1"));

        CloseableHttpResponse response = null;
        String result = null;

        try {
            post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    result = EntityUtils.toString(httpEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            post.abort();
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return result;
    }


    private static String signature(String user, String uKey, String sTime) {
        String s = DigestUtils.sha1Hex(user + uKey + sTime);
        return s;
    }

    public static String setOrderForm(List<ConsumerOrderDetailDTO> detailList, ConsumerOrderTotal orderTotal) {
        StringBuilder content = new StringBuilder();
        //标签说明：
        //单标签:
        //"<BR>"为换行,"<CUT>"为切刀指令(主动切纸,仅限切刀打印机使用才有效果)
        //"<LOGO>"为打印LOGO指令(前提是预先在机器内置LOGO图片),"<PLUGIN>"为钱箱或者外置音响指令
        //成对标签：
        //"<CB></CB>"为居中放大一倍,"<B></B>"为放大一倍,"<C></C>"为居中,<L></L>字体变高一倍
        //<W></W>字体变宽一倍,"<QR></QR>"为二维码,"<BOLD></BOLD>"为字体加粗,"<RIGHT></RIGHT>"为右对齐
        //拼凑订单内容时可参考如下格式
        //根据打印纸张的宽度，自行调整内容的格式，可参考下面的样例格式

        content = new StringBuilder("<CB>食尚快客</CB><BR>");
        content.append("名称　　　　　  单价  数量  小计<BR>");
        content.append("--------------------------------<BR>");
        for (ConsumerOrderDetailDTO detail : detailList) {
            log.info("detail:{}", detail);
            content.append(nameConvert(detail.getDishName(), 14))
                    .append(convert(("" + detail.getDishesPrice()), 8))
                    .append(convert(detail.getDishesQuantity().toString(), 4))
                    .append(detail.getDishesPrice()
                                        .multiply(new BigDecimal(detail.getDishesQuantity())))
                                        .append("<BR>");
        }
        content.append("--------------------------------<BR>");
        content.append("订单用户: ").append(orderTotal.getUserName()).append("<BR>");
        content.append("收货地址: ").append(orderTotal.getUserAddress()).append("<BR>");
        content.append("联系电话: ").append(orderTotal.getUserPhoneNum()).append("<BR>");
        content.append("订餐时间: ").append(convert(orderTotal.getCreateTime())).append("<BR>");
        content.append("--------------------------------<BR>");
        content.append("<BOLD>合计: ").append(orderTotal.getOrderAmount()).append("元</BOLD><BR>");

        return content.toString();
    }

    static String nameConvert(String str,int length){

        int len = 0;
        try {
            len = str.getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("Str:{},str.len{}，len:{}", str, str.length(),len);
            if (len <= length) {
                StringBuilder strBuilder = new StringBuilder(str);
                for (int i = length-len; i >= 0; i--) {
                    strBuilder.append(" ");
                }
                str = strBuilder.toString();
                return str;
            }
        return str;
    }

    static String convert(String str,int length){
        log.info("Str:{},str.len{}", str, str.length());
        int len = str.length();
        if (len <= length) {
            StringBuilder strBuilder = new StringBuilder(str);
            for (int i = 0; i < length - len; i++) {
                strBuilder.append(" ");
            }
            str = strBuilder.toString();
            return str;
        }
        return str;
    }

    static String convert(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        try {
            Date d = format.parse(date);
            return newFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}
