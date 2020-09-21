package com.xknower.api.controller;

import com.alibaba.fastjson.JSON;
import com.xknower.common.wx.WxOfficialAccountsService;
import com.xknower.common.wx.response.WxContent;
import com.xknower.common.wx.module.WxOfficialAccountsProperties;
import com.xknower.common.utils.XmlUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Controller
@RequestMapping("/wx")
public class WxOfficialAccountsController {

    @Autowired
    private WxOfficialAccountsProperties wxProperties;

    @Autowired
    private WxOfficialAccountsService wxOfficialAccountsService;

    @RequestMapping(value = "/we-chat/msg", method = RequestMethod.GET)
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

    @RequestMapping(value = "/we-chat/msg", method = RequestMethod.POST)
    @ResponseBody
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
                .content("")
                .build().responseXmlMsg();

        log.info(String.format(" 响应 => %s", res));
        return res;
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

