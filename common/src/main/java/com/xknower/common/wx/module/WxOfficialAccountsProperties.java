package com.xknower.common.wx.module;

import lombok.Data;

/**
 * 微信公帐号配置
 *
 * @author xknower
 * @date 2020/09/21
 */
@Data
public class WxOfficialAccountsProperties {

    private String appId;

    private String secret;

    private String token;

    private String aesKey;
}
