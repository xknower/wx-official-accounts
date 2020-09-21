package com.xknower.common.wx.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信发送内容
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WxContent {

    /**
     * signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * <p>
     * 6becb16d1fc52d5c10a6e1ea8cca289f4065b355
     */
    private String signature;
    /**
     * timestamp 时间戳 1588157533
     */
    private String timestamp;
    /**
     * nonce	 随机数 301912571
     */
    private String nonce;
    /**
     * 接口验证字符串, 随机字符串
     */
    private String echostr;
    /**
     * 接收消息用户标识  or-3Q1Md_457YgDb8CmTocADkhts
     */
    private String openid;
    /**
     * 原始数据
     */
    private String data;

    public void setData(String signature, String timestamp, String nonce, String echostr, String openid, String content) {
        this.signature = signature;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.echostr = echostr;
        this.openid = openid;
        this.data = content;
    }

    /**
     * 开发者 微信号
     */
    private String toUserName;
    /**
     * 发送方帐号（一个OpenID）
     */
    private String fromUserName;

    private long createTime;

    /**
     * 消息类
     */
    private String msgType;

    /**
     * 消息内容 (MsgType = text)
     */
    private String content;
    /**
     * 消息编号 (MsgType = text)
     * 22736968561998991
     */
    private String msgId;

    /**
     * 事件名 (MsgType = event)
     * subscribe 订阅事件
     * unsubscribe 取消关注
     */
    private String event;
    /**
     * 事件 KEY []
     */
    private String eventKey;

    public String responseXmlMsg() {
        StringBuffer data = new StringBuffer();
        data.append("<xml>")
                // 接收方帐号（收到的OpenID）
                .append("<ToUserName>").append("<![CDATA[").append(toUserName).append("]]>").append("</ToUserName>")
                // 开发者微信号
                .append("<FromUserName>").append("<![CDATA[").append(fromUserName).append("]]>").append("</FromUserName>")
                .append("<CreateTime>").append(createTime).append("</CreateTime>")
                .append("<MsgType>").append("<![CDATA[").append(msgType).append("]]>").append("</MsgType>")
                .append("<Content>").append("<![CDATA[").append(content).append("]]>").append("</Content>");
        data.append("</xml>");
        return data.toString();
    }
}
