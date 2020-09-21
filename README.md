# WeChat Official Accounts

> 此项目对接微信相关平台, 相关服务接口实现

## 概述

> 微信公众号(订阅号)

- [xknower](https://mp.weixin.qq.com/cgi-bin/loginpage)


## [微信公帐号平台接口测试](https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login)

> appId : wx29061222625f9541

> appSecret: b984f976d38e7c03d41b9790ba3e5619

> 域名 : api.xknower.com

> Token : wx29061222625f9541

> URL: http://api.xknower.com/wx/we-chat/msg


## [微信公共号](https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Access_Overview.html)

> OpenId, UnionId

> 公众号消息会话
> - 群发消息
> - 被动回复消息
> - 客服消息
> - 模板消息

> [接口权限](https://developers.weixin.qq.com/doc/offiaccount/Getting_Started/Explanation_of_interface_privileges.html)

> [状态码](https://developers.weixin.qq.com/doc/offiaccount/Getting_Started/Global_Return_Code.html)

> 


### 开发前必读

### 开始开发

### 自定义菜单

### 消息管理

### 微信网页开发

### 素材管理

### 图文消息留言管理

### 用户管理

### 帐号管理

### 数据统计

### 微信卡券

### 微信门店

### 微信小店

### 智能接口

### 微信设备功能

### 新版客服功能

### 对话能力（原导购助手）

### 微信“一物一码”

### 微信发票

### 微信非税缴费


## [开放平台](https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Resource_Center_Homepage.html)

### [移动应用](https://developers.weixin.qq.com/doc/oplatform/Mobile_App/Resource_Center_Homepage.html) 

#### 资源中心首页

#### 接入指南

#### 分享与收藏功能

#### 微信支付功能

#### 微信登录功能

#### 微信智能接口

#### 一次性订阅消息开发指南

#### APP拉起小程序功能

#### 微信内网页跳转APP功能

#### 常见问题


### [网站应用](https://developers.weixin.qq.com/doc/oplatform/Website_App/WeChat_Login/Wechat_Login.html)

#### 微信登录功能

##### 网站应用微信登陆开发指南

基于OAuth2.0协议标准构建的微信OAuth2.0授权登录系统

接入条件, AppID + AppSecret

> 1、请求Code

- https://open.weixin.qq.com/connect/qrconnect?appid={APPID}&redirect_uri={REDIRECT_URI}&response_type=code&scope={SCOPE}&state={STATE}


> 2、通过code获取access_token

- https://api.weixin.qq.com/sns/oauth2/access_token?appid={APPID}&secret={SECRET}&code=CODE&grant_type=authorization_code

> 3、通过access_token调用接口

##### 授权后调用接口(uninoid)

> 1、通过code获取access_token

- https://api.weixin.qq.com/sns/oauth2/access_token?appid={APPID}&secret={SECRET}&code=CODE&grant_type=authorization_code


> 2、获取用户个人信息 (UnionID机制)

获取用户个人信息。开发者可通过OpenID来获取用户基本信息。

- https://api.weixin.qq.com/sns/userinfo?access_token={ACCESS_TOKEN}&openid={OPENID}



#### 微信智能接口

#### 开发者反馈

### 小程序硬件框架

### 第三方平台

### [返回码说明](https://developers.weixin.qq.com/doc/oplatform/Return_codes/Return_code_descriptions_new.html)

### [资料下载](https://developers.weixin.qq.com/doc/oplatform/Downloads/iOS_Resource.html)