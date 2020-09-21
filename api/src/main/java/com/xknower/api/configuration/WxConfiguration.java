package com.xknower.api.configuration;

import com.xknower.common.wx.WxOfficialAccountsService;
import com.xknower.common.wx.module.WxOfficialAccountsProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConfiguration {

    @Bean
    @ConfigurationProperties("wx.official-accounts")
    WxOfficialAccountsProperties wxOfficialAccountsProperties() {
        return new WxOfficialAccountsProperties();
    }

    @Bean
    WxOfficialAccountsService wxOfficialAccountsService(WxOfficialAccountsProperties wxOfficialAccountsProperties) {
        return new WxOfficialAccountsService(wxOfficialAccountsProperties);
    }

}
