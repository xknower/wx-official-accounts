package com.xknower.api.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
public class WxXmlRequest {

    @XmlElement(name = "ToUserName")
    private String toUserName;

    @XmlElement(name = "FromUserName")
    private String fromUserName;

    @XmlElement(name = "CreateTime")
    private String createTime;

    @XmlElement(name = "MsgType")
    private String msgType;

    @XmlElement(name = "Content")
    private String content;

    @XmlElement(name = "MsgId")
    private String msgId;
}


// <xml>
//    <ToUserName>
//        <![CDATA[gh_35d8658a936d]]>
//    </ToUserName>
//    <FromUserName>
//        <![CDATA[or-3Q1Md_457YgDb8CmTocADkhts]]>
//    </FromUserName>
//    <CreateTime>1600732306</CreateTime>
//    <MsgType>
//        <![CDATA[text]]>
//    </MsgType>
//    <Content>
//        <![CDATA[你好]]>
//    </Content>
//    <MsgId>22916976861558260</MsgId>
//</xml>