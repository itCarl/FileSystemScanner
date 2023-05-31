package de.blubber_lounge.mrT;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class Main {
    
    /** 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        HashMap<String, String> supported_filetypes = new HashMap<String, String>();

        supported_filetypes.put("jpg", "JPEG-Image");
        supported_filetypes.put("png", "PNG-Image");
        supported_filetypes.put("gif", "GIF-Image");
        supported_filetypes.put("txt", "Text File");

        File root_File = new File(System.getProperty("user.dir"));
        ArrayList<TreeNode> tree = new ArrayList<TreeNode>();
        TreeNode root_Node = new TreeNode(root_File.getAbsolutePath(), "Home Directory");
        tree.add(root_Node);

        recurse_Directory(root_File, root_Node, tree, supported_filetypes);

        for (TreeNode t : tree) {
            System.out.println("Path: " + t.path);
            System.out.println("Size: " + t.size);
            System.out.println("Type: " + t.file_type);
            System.out.println("Width: " + t.width);
            System.out.println("Height: " + t.height);
            System.out.println("Bit Depth: " + t.bit_depth);
            System.out.println("Compression: " + t.compression);
            System.out.println("Children: " + t.children);
            System.out.println();
        }
    }
    
    /** 
     * Recursively scan the main directory and add every file and folder to a Tree Model
     * Check each File or Folder for file type and add a suitable identifier
     * @param currFile The current "parent" Java File object of the file data tree object
     * @param curr_Node The current "parent" node of the file data tree object
     * @param tree The Tree storage container
     * @param supported_filetypes A hashmap of currently supported file types
     * @throws IOException
     */
    public static void recurse_Directory(File currFile, TreeNode curr_Node, ArrayList<TreeNode> tree, HashMap<String, String> supported_filetypes) throws IOException{
            File[] files = currFile.listFiles();
            long folder_size = 0;
            if (files != null){
                for (File f : files) {
                    if (f.isDirectory()){
                        TreeNode child_node = curr_Node.addChild(f.getAbsolutePath());
                        child_node.set_Type("Directory");
                        tree.add(child_node);
                        recurse_Directory(f, child_node, tree, supported_filetypes);
                        folder_size += child_node.size;
                    }
                    else {
                        TreeNode child_node = curr_Node.addChild(f.getAbsolutePath());
                        child_node.path = f.getAbsolutePath();
                        child_node.set_Size(f.length());
                        String suffix = get_File_Suffix(f);
                        if (supported_filetypes.get(suffix) != null){
                            String filetype = supported_filetypes.get(suffix);
                            if (filetype.contains("Image")){
                                child_node.set_Type(filetype);
                                read_PNG_Data(f, child_node);
                                read_JPEG_Data(f, child_node);
                                read_GIF_Data(f, child_node);
                                setImageCompression(child_node);
                            }
                            if(filetype == "Text File"){
                                child_node.set_Type("Text File");
                            }
                        }
                        tree.add(child_node);
                        folder_size += child_node.size;
                    }
                }
            }
            curr_Node.set_Size(folder_size);
        }

    
    /** 
     * Getter for returning the file extension suffix from input File objects
     * @param file Java File Object that represents the currently examined file
     * @return String The files suffix as a String
     */
    private static String get_File_Suffix(File file) {
        int pos = file.getName().lastIndexOf(".");       //Get the last name of the path, then get the index of the last occurence of ".", this is to find the file extension type
        if (pos == -1)
            return "Unknown file extension";
        String suffix = file.getName().substring(pos+1);     //get the image name extension
        return suffix;
    }

    
    /** 
     * Setter calculating image compression based on Image dimensions and bit depth
     * @param child_node The current node of the file data tree object
     */
    static void setImageCompression(TreeNode child_node){
        long size = child_node.size * 100;
        long compression = size / (child_node.height * child_node.width * (24/child_node.bit_depth));
        child_node.set_Compression((compression));
    }

    
    /** 
     * Setter for reading data from input file stream as datastream and attempt to read GIF Format dimension data
     * <p>References: https://en.wikipedia.org/wiki/GIF</p>
     * @param imgFile Java File Object that represents the currently examined file
     * @param node The current node of the file data tree object
     */
    static void read_GIF_Data(File imgFile, TreeNode node){
        try (FileInputStream fis = new FileInputStream(imgFile)) {
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);

            //Magic number for GIF Files is 47:49:46:38:39:61
            if (dis.readUnsignedByte() != 0x47) return;
            if (dis.readUnsignedByte() != 0x49) return;
            if (dis.readUnsignedByte() != 0x46) return;
            if (dis.readUnsignedByte() != 0x38) return;
            if (dis.readUnsignedByte() != 0x39) return;
            if (dis.readUnsignedByte() != 0x61) return;

            int width = 0;
            byte b = (byte)dis.readUnsignedByte();
            byte c = (byte)dis.readUnsignedByte();
            width = (width << 8) + (c & 0xFF);
            width = (b & 0xFF);

            int height = 0;
            byte d = (byte)dis.readUnsignedByte();
            byte e = (byte)dis.readUnsignedByte();
            height = (height << 8) + (e & 0xFF);
            height = (d & 0xFF);

            if (dis != null) dis.close ();
            if (bis != null) bis.close ();
            if (fis != null) fis.close ();

            node.set_bitDepth(8);
            node.set_Height(height);
            node.set_Width(width);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * Setter for reading data from input file stream as datastream and attempt to read JPEG Format dimension data
     * <p>References: https://en.wikipedia.org/wiki/JPEG, https://en.wikipedia.org/wiki/JPEG_File_Interchange_Format,
     * https://wiki.tcl-lang.org/page/Reading+JPEG+image+dimensions</p>
     * @param imgFile Java File Object that represents the currently examined file
     * @param node The current node of the file data tree object
     */
    static void read_JPEG_Data(File imgFile, TreeNode node){
        try (FileInputStream fis = new FileInputStream(imgFile)) {
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);

            //SOI for JPEG Files is ff:d8:ff
            if (dis.readUnsignedByte() != 0xff) return;
            if (dis.readUnsignedByte() != 0xd8) return;

            boolean found = false;

            while(!found){
                while (dis.readUnsignedByte() != 0xff) {}
                int sof = dis.readUnsignedByte();
                
                Integer[] SOF_markers = {0xc0,0xc1,0xc2,0xc3,0xc4,0xc5,0xc6,0xc7,0xc8,0xc9,0xca,0xcb,0xcd,0xce,0xcf};
                if (Arrays.asList(SOF_markers).contains(sof)){
                    found = true;
                    dis.skip(3);

                    int width = 0;
                    for (int i = 0; i < 2; i++){
                        byte b = (byte)dis.readUnsignedByte();
                        width = (width << 8) + (b & 0xFF);
                    }

                    int height = 0;
                    for (int i = 0; i < 2; i++){
                        byte b = (byte)dis.readUnsignedByte();
                        height = (height << 8) + (b & 0xFF);
                    }

                    node.set_bitDepth(8);
                    node.set_Height(height);
                    node.set_Width(width);

                    if (dis != null) dis.close ();
                    if (bis != null) bis.close ();
                    if (fis != null) fis.close ();

                    return;

                } else {
                    int s1 = dis.readUnsignedByte();
                    int s2 = dis.readUnsignedByte();
                    int offset = 0;
                    offset = (offset << 8) + (s1 & 0xFF);
                    offset = (offset << 8) + (s2 & 0xFF);
                    dis.skip(offset-2);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    
    /** 
     * Setter for reading data from input file stream as datastream and attempt to read PNG Format dimension data
     * <p>References: https://en.wikipedia.org/wiki/PNG</p>
     * @param imgFile Java File Object that represents the currently examined file
     * @param node The current node of the file data tree object
     */
    static void read_PNG_Data(File imgFile, TreeNode node){
        try (FileInputStream fis = new FileInputStream(imgFile)) {

            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);

            //Magic number for PNG Files is 89:50:4e:47:0d:0a:1a:0a
            if (dis.readUnsignedByte() != 0x89) return;
            if (dis.readUnsignedByte() != 0x50) return;
            if (dis.readUnsignedByte() != 0x4e) return;
            if (dis.readUnsignedByte() != 0x47) return;
            if (dis.readUnsignedByte() != 0x0d) return;
            if (dis.readUnsignedByte() != 0x0a) return;
            if (dis.readUnsignedByte() != 0x1a) return;
            if (dis.readUnsignedByte() != 0x0a) return;

            dis.skip(8);

            //Get PNG width bytes and convert them to a readable int
            int width = 0;
                for (int i = 0; i < 4; i++){
                    byte b = (byte)dis.readUnsignedByte();
                    width = (width << 8) + (b & 0xFF);
                }
            
            //Get PNG height bytes and convert them to a readable int
            int height = 0;
            for (int i = 0; i < 4; i++){
                byte b = (byte)dis.readUnsignedByte();
                height = (height << 8) + (b & 0xFF);
            }

            //Get the bit depth of the PNG to be used in compression calculation
            int bit_depth = dis.readUnsignedByte();

            if (dis != null) dis.close ();
            if (bis != null) bis.close ();
            if (fis != null) fis.close ();

            node.set_bitDepth(bit_depth);
            node.set_Height(height);
            node.set_Width(width);
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}