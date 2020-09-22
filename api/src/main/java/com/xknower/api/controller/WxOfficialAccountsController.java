package com.xknower.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xknower.api.request.WxXmlRequest;
import com.xknower.common.wx.WxOfficialAccountsService;
import com.xknower.common.wx.response.WxContent;
import com.xknower.common.wx.module.WxOfficialAccountsProperties;
import com.xknower.common.utils.XmlUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信公众号相关
 *
 * @author xknower
 * @date 2020/09/21
 */
@Log4j
@RestController
@RequestMapping("/wx")
public class WxOfficialAccountsController {

    @Autowired
    private WxOfficialAccountsProperties wxProperties;

    @Autowired
    private WxOfficialAccountsService wxOfficialAccountsService;

    @RequestMapping(value = "/msg", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String get_msg(@RequestBody WxXmlRequest person) {
        //
        return "<xml><ToUserName><![CDATA[or-3Q1Md_457YgDb8CmTocADkhts]]></ToUserName><FromUserName><![CDATA[gh_35d8658a936d]]></FromUserName><CreateTime>1600732309</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[success]]></Content></xml>";
    }

    @RequestMapping(value = "/we-chat/msg", method = RequestMethod.GET,
            produces = {"application/xml;charset=UTF-8"})
    public String get_wechat_msg(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = getAllRequestParam(request);
        String content = getContent(request);
        WxContent wxContent;
        //
        try {
            if (content != null && (content.startsWith("<xml") || content.startsWith("<XML"))) {
                String data = XmlUtil.xmlToJson(content);
                wxContent = JSON.parseObject(data, WxContent.class);
            } else {
                wxContent = new WxContent();
            }
        } catch (Exception e) {
            log.warn(String.format("微信消息解析异常 => %s", content));
            wxContent = new WxContent();
        }

        //
        wxContent.setData(params.get("signature"), params.get("timestamp"), params.get("nonce"), params.get("echostr"), params.get("openid"), content);

        log.info(String.format("微信验证接口接收数据 => %s ", wxContent));

        return wxContent.getEchostr();
    }

    @RequestMapping(value = "/we-chat/msg", method = RequestMethod.POST,
            produces = {"application/xml;charset=UTF-8"})
    public String post_wechat_msg(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = getAllRequestParam(request);
        String content = getContent(request);
        WxContent wxContent;
        //
        try {
            if (content != null && (content.startsWith("<xml") || content.startsWith("<XML"))) {
                wxContent = JSON.parseObject(XmlUtil.xmlToJson(content), WxContent.class);
            } else {
                wxContent = new WxContent();
            }
        } catch (Exception e) {
            log.error(String.format("微信消息解析异常 => %s", content));
            wxContent = new WxContent();
        }

        //
        wxContent.setData(params.get("signature"), params.get("timestamp"), params.get("nonce"), params.get("echostr"), params.get("openid"), content);


        log.info(String.format("接收消息 => %s", wxContent));

        // 获取用户信息
//        try {
//            JSONObject d = wxOfficialAccountsService.getUserInfo(wxContent.getFromUserName());
//            log.info(String.format("发送用户 => %s", d));
//
//            return WxContent
//                    .builder()
//                    .toUserName(wxContent.getFromUserName())
//                    .fromUserName(wxContent.getToUserName())
//                    .createTime(wxContent.getCreateTime())
//                    .msgType("text")
//                    .content("welcome")
//                    .build().responseXmlMsg();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String res = WxContent
                .builder()
                .toUserName(wxContent.getFromUserName())
                .fromUserName(wxContent.getToUserName())
                .timestamp(wxContent.getTimestamp())
                .msgType("text")
                .content("success")
                .build().responseXmlMsg();

        log.info(String.format(" 响应 => %s", res));
        return res;
    }

    @RequestMapping(value = "/we-chat/info", method = RequestMethod.GET)
    public JSONObject get_wechat_msg(@RequestParam("openId") String openId) {
        try {
            JSONObject d = wxOfficialAccountsService.getUserInfo(openId);
            log.info(String.format("发送用户 => %s", d));
            return d;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    private Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 如果字段的值为空，判断若值为空，则删除这个字段
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }

    private String getContent(HttpServletRequest request) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuilder stb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                stb.append(line);
            }
            return stb.toString();
        } catch (IOException ieo) {
            ieo.printStackTrace();
        }
        return null;
    }
}

