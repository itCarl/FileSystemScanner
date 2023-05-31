package de.blubber_lounge.mrT;
import java.util.LinkedList;
import java.util.List;

public class TreeNode {
    String path;

    long size = 0;
    String file_type = "Unknown Datatype";

    int height = 0;
    int width = 0;
    int bit_depth = 0;
    long compression = 0;

    TreeNode parent;
    List<TreeNode> children;

    public TreeNode(String path) {
        this.path = path;
        this.children = new LinkedList<TreeNode>();
    }

    public TreeNode(String path, String type){
        this.path = path;
        this.children = new LinkedList<TreeNode>();
        this.file_type = "Root Directory";
    }

    public TreeNode addChild(String child) {
        TreeNode childNode = new TreeNode(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    public void set_Size(long size){
        this.size = size;
    }

    public void set_Type(String type){
        this.file_type = type;
    }

    public void set_Height(int height){
        this.height = height;
    }

    public void set_Width(int width){
        this.width = width;
    }

    public void set_Compression(long compression){
        this.compression = compression;
    }

    public void set_bitDepth(int bit_depth){
        this.bit_depth = bit_depth;
    }
}