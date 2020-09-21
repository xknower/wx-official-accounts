package com.xknower.common.utils;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

/**
 * XML 工具类
 *
 * @author xknower
 * @date Created by xknower on 2017/6/19.
 */
public class XmlUtil {

    public static String transformFromTreeNodeToString(TreeNode treeNode) {
        if (treeNode == null) {
            return null;
        }

        String xmlStr = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            recursionTreeNode(document, null, treeNode, true);

            xmlStr = transformFromDocumentToString(document);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return xmlStr;
    }

    private static void recursionTreeNode(Document document, Element root, TreeNode treeNode, boolean isRoot) {
        if (treeNode != null && StringUtils.isNotEmpty(treeNode.getName())) {
            Element temp = document.createElement(treeNode.getName());
            if (StringUtils.isNotEmpty(treeNode.getText())) {
                temp.setTextContent(treeNode.getText());
            }
            if (treeNode.getChildren() != null && treeNode.getChildren().size() > 0) {
                for (TreeNode node : treeNode.getChildren()) {
                    recursionTreeNode(document, temp, node, false);
                }
            }
            if (isRoot) {
                root = temp;
            } else {
                root.appendChild(temp);
            }
        }

        if (isRoot) {
            document.appendChild(root);
        }
    }

    private static String transformFromDocumentToString(Document document) {
        String xmlStr = null;
        try {
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transFormer = transFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            //export string
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            transFormer.transform(domSource, new StreamResult(bos));
            xmlStr = bos.toString();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return xmlStr;
    }

    private static Document transformFromStringToDocument(String xmlStr) {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            StringReader sr = new StringReader(xmlStr);
            InputSource is = new InputSource(sr);
            doc = builder.parse(is);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * XML 字符串转换未 JSON 字符串
     */
    public static String xmlToJson(String xml) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        return xmlSerializer.read(xml).toString();
    }

    /**
     * JSON 字符串转换为 XML 字符串
     */
    public static String jsontoXml(String json) {
        try {
            XMLSerializer serializer = new XMLSerializer();
            JSON jsonObject = JSONSerializer.toJSON(json);
            return serializer.write(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
