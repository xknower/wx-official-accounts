package com.xknower.common.wx.module;

public enum WeChatURLType {

    //
    URL_CGI_BIN_TOKEN("https://api.weixin.qq.com/cgi-bin/token"),
    //
    URL_CGI_TICKET_GETTICKET("https://api.weixin.qq.com/cgi-bin/ticket/getticket"),
    //
    URL_GZH_USER_INFO("https://api.weixin.qq.com/cgi-bin/user/info");

    private String value;

    WeChatURLType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
