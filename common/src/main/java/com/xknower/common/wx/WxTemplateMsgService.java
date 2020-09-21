package com.xknower.common.wx;

import com.xknower.common.wx.module.WxOfficialAccountsProperties;
import javafx.util.Callback;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.util.List;

/**
 * 微信公众号, 模板消息推送
 *
 * @author xknower
 * @date 2020/09/21
 */
public class WxTemplateMsgService {

    private WxOfficialAccountsProperties wxOfficialAccountsProperties;

    public WxTemplateMsgService(WxOfficialAccountsProperties wxOfficialAccountsProperties) {
        this.wxOfficialAccountsProperties = wxOfficialAccountsProperties;
    }

    public boolean sendWxMessage(String wxOpenid, String templateId, Callback<WxMpTemplateMessage, List<WxMpTemplateData>> callback) {
        try {
            WxMpService wxMpService = getWxMpService();

            WxMpTemplateMessage message = WxMpTemplateMessage
                    .builder()
                    .toUser(wxOpenid)
                    .templateId(templateId)
                    .build();

            //
            List<WxMpTemplateData> wxMpTemplateData = callback.call(message);
            if (wxMpTemplateData == null || wxMpTemplateData.size() < 5) {
                return false;
            }

            wxMpTemplateData.forEach(data -> message.addData(data));

//            // 2.7.0
//            message.addData(new WxMpTemplateData("first", first == null ? "" : first));
//            if (keyword != null && keyword.length > 0) {
//                for (int i = 0; i < keyword.length; i++) {
//                    // 填充消息内容
//                    message.addData(new WxMpTemplateData("keyword" + (i + 1), keyword[i] == null ? "" : keyword[i], "#0000FF"));
//                }
//            }
//            message.addData(new WxMpTemplateData("remark", remark == null ? "" : remark));

            // 发送微信消息
            wxMpService.getTemplateMsgService().sendTemplateMsg(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 公众号, 推送消息发给指定用户
     *
     * @param wxOpenid 用户针对该服务号的 OpenId
     */
    private boolean sendWxMessage(String wxOpenid, String templateId, String first, String remark, String... keyword) {
        try {
            WxMpService wxMpService = getWxMpService();

            WxMpTemplateMessage message = WxMpTemplateMessage
                    .builder()
                    .toUser(wxOpenid)
                    .templateId(templateId)
                    .build();
            // 2.7.0
            message.addData(new WxMpTemplateData("first", first == null ? "" : first));
            if (keyword != null && keyword.length > 0) {
                for (int i = 0; i < keyword.length; i++) {
                    // 填充消息内容
                    message.addData(new WxMpTemplateData("keyword" + (i + 1), keyword[i] == null ? "" : keyword[i], "#0000FF"));
                }
            }
            message.addData(new WxMpTemplateData("remark", remark == null ? "" : remark));
            // 发送微信消息
            wxMpService.getTemplateMsgService().sendTemplateMsg(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 返回WxMpService实例
     */
    private WxMpService getWxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        // 服务号
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(wxOfficialAccountsProperties.getAppId());
        configStorage.setSecret(wxOfficialAccountsProperties.getSecret());
        configStorage.setToken(wxOfficialAccountsProperties.getToken());
        configStorage.setAesKey(wxOfficialAccountsProperties.getAesKey());
        wxMpService.setWxMpConfigStorage(configStorage);
        return wxMpService;
    }

}
