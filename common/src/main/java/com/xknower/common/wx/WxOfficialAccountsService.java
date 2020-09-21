package com.xknower.common.wx;

import com.alibaba.fastjson.JSONObject;
import com.xknower.common.utils.HttpUtils;
import com.xknower.common.wx.module.WeChatURLType;
import com.xknower.common.wx.module.WxOfficialAccountsProperties;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 微信公众号
 *
 * @author xknower
 * @date 2020/09/21
 */
@Log4j
public class WxOfficialAccountsService {

    private WxOfficialAccountsProperties wxOfficialAccountsProperties;

    private WxOfficialAccountsService() {
    }

    public WxOfficialAccountsService(WxOfficialAccountsProperties wxOfficialAccountsProperties) {
        this.wxOfficialAccountsProperties = wxOfficialAccountsProperties;
    }

    /**
     * 微信 js_sdk 签名 / 公众号
     */
    public Map<String, Object> config(String url) throws Exception {
        // 1、获取AccessToken 2、获取Ticket 3、获取签名
        String jsapiTicket = getTicket(getAccessToken(wxOfficialAccountsProperties.getAppId(), wxOfficialAccountsProperties.getSecret()));

        Map<String, Object> ret = new ConcurrentHashMap<>();
        //
        String nonceStr = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        // 注意这里参数名必须全部小写, 且必须有序
        String string1 = "jsapi_ticket=" + jsapiTicket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(string1.getBytes("UTF-8"));
        String signature = byteToHex(crypt.digest());

        //
        ret.put("url", url);
        ret.put("appId", wxOfficialAccountsProperties.getAppId());
        ret.put("jsapi_ticket", jsapiTicket);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
    }

    private static String getTicket(String accessToken) throws Exception {
        String url = WeChatURLType.URL_CGI_TICKET_GETTICKET.value() + "?access_token=" + accessToken + "&type=jsapi";
        JSONObject ticketJson = JSONObject.parseObject(HttpUtils.toGet(url));
        if (ticketJson.get("ticket") != null) {
            return ticketJson.getString("ticket");
        }
        return "";
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 公众号 AccessToken
     */
    public String getAccessToken() throws Exception {
        return getAccessToken(wxOfficialAccountsProperties.getAppId(), wxOfficialAccountsProperties.getSecret());
    }

    /**
     * 获取 accessToken 根据 appId 和 secret
     *
     * @param appId
     * @param secret
     * @return {"access_token":"xxxx,"expires_in":7200}
     */
    private static String getAccessToken(String appId, String secret) throws IOException {
        String url = WeChatURLType.URL_CGI_BIN_TOKEN.value() + "?grant_type=client_credential" + "&appid=" + appId + "&secret=" + secret;
        JSONObject accessTokenJson = JSONObject.parseObject(HttpUtils.toGet(url));
        log.info(String.format("获取 accessToken 根据 appId 和 secret => %s", accessTokenJson));
        if (accessTokenJson.get("access_token") != null) {
            return accessTokenJson.getString("access_token");
        }

        throw new RuntimeException("获取 accessToken 异常, 请重试");
    }

    /**
     * 根据公众号OpenId获取用户基本信息
     *
     * @param openId
     * @return { "subscribe": 1, "openid": "", "nickname": "", "sex": 1, "language": "zh_CN", "city": "广州", "province": "广东", "country": "中国", "headimgurl":"xx", "subscribe_time": 1382694957, "unionid": "" "remark": "", "groupid": 0, "tagid_list":[128,2], "subscribe_scene": "ADD_SCENE_QR_CODE", "qr_scene": 98765, "qr_scene_str": "" }
     */
    public JSONObject getUserInfo(String openId) throws Exception {
        String url = WeChatURLType.URL_GZH_USER_INFO.value() + "?access_token=" + getAccessToken() + "&openid=" + openId;
        try {
            JSONObject response = HttpUtils.toGetJSONObject(url);
            log.info(String.format("根据openId获取GZH用户信息 => %s", response));
            if (response != null) {
                if (StringUtils.isNotBlank(response.getString("errmsg"))) {
                    throw new RuntimeException(response.getString("errmsg"));
                }
                return response;
            }
        } catch (IOException e) {
            throw new RuntimeException("请求异常");
        }
        return null;
    }

}
