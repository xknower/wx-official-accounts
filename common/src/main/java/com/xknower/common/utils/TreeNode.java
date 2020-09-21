package com.xknower.common.utils;

import java.util.ArrayList;
import java.util.List;

class TreeNode {

    private String text;

    private String name;

    private List<TreeNode> children;

    public void appendChild(TreeNode treeNode) {
        if (this.children == null) {
            this.children = new ArrayList<TreeNode>();
        }
        this.children.add(treeNode);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
