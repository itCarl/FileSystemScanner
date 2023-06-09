package de.blubber_lounge.FileSystemScanner;

import java.util.*;

import de.blubber_lounge.FileSystemScanner.node.Node;
import de.blubber_lounge.FileSystemScanner.util.FancyOutput;
import de.blubber_lounge.FileSystemScanner.util.FileType;
import de.blubber_lounge.FileSystemScanner.node.Directory;
import de.blubber_lounge.FileSystemScanner.node.File;

public class FileSystemScanner
{

    public FileSystemScanner()
    {
        System.out.println("~~~~~~~~~\n"+Character.toString(169) + " 2023 BlubberLounge. Some rights reserved.\nCreating projects you never knew you needed :]\n~~~~~~~~~\n");
    }

    public void scan(String rootPath)
    {
        java.io.File root = new java.io.File(rootPath);
        if (!root.exists() || !root.isDirectory()) {
            System.out.println("Invalid directory path: " + rootPath);
            return;
        }

        Directory rootDir = new Directory();
        rootDir.setName(root.getName());
        rootDir.setSize(root.length());

        System.out.println("Scan starting... \n");
        System.out.println("=== ROOT ===\n" + root.getName() +" (FullPath:"+ root.getAbsolutePath() + ")\n============");

        // MAIN PART
        this.readFileSystem(rootDir, root, rootDir);

        // OUTPUT        
        this.ListSubNodesOfNode(rootDir);
        
        System.out.println("\nScan Completed.");

        // this.percentageBar();
    }

    protected void readFileSystem(Node currentNode, java.io.File currentDirectory, Node latestDir)
    {
        java.io.File[] dirs = currentDirectory.listFiles();
        
        if(dirs == null || dirs.length <= 0 )
            return;

        for(java.io.File f : dirs)
        {
            if (f.isDirectory()) {
                Directory dir = new Directory();
                dir.setName(f.getName());
                currentNode.addSubNode(dir);
                readFileSystem(dir, f, dir);
            } else {
                FileType fileType = getFileType(f);
                if(getImageTypes().contains(fileType.toString())) {
                    // IMAGE FILES
                    if(FileType.PNG == fileType) {
                        PNG imageFile = new PNG();
                        imageFile.setName(f.getName());
                        imageFile.setType(fileType);
                        imageFile.setSize(f.length());
                        currentNode.addSubNode(imageFile);
                        readFileSystem(imageFile, f, latestDir);
                    }

                } else if(FileType.TXT == fileType) {
                    // TEXT FILE

                } else {
                    // UNKOWN FILES
                }

                if(FileType.PNG != fileType) {
                    File file = new File();

                    file.setName(f.getName());
                    file.setType(fileType);
                    file.setSize(f.length());

                    currentNode.addSubNode(file);
                    readFileSystem(file, f, latestDir);
                }

                latestDir.setSize(latestDir.getSize()+f.length());
            }
        }
    }

    protected void ListSubNodesOfNode(Node node, int depth)
    {
        for(Node n : node.getSubNodes())
        {
            System.out.println(
                FancyOutput.getIndentLines(depth) +
                n.getInfo()
            );

            if(n.getSubNodes().size() > 0)
                this.ListSubNodesOfNode(n, depth+1);            
        }
    }

    protected void ListSubNodesOfNode(Node node)
    {
        this.ListSubNodesOfNode(node, 0);
    }

    private FileType getFileType(java.io.File file)
    {
        int pos = file.getName().lastIndexOf(".");       //Get the last name of the path, then get the index of the last occurence of ".", this is to find the file extension type
        if (pos == -1)
            return FileType.UNKOWN;

        String stringFileType = file.getName().substring(pos+1).toUpperCase();

        if(!getEnumValues().contains(stringFileType))
            return FileType.UNKOWN;

        return FileType.valueOf(stringFileType);
    }

    private HashSet<String> getEnumValues()
    {
        HashSet<String> values = new HashSet<String>();
        for (FileType c : FileType.values())
            values.add(c.name());

        return values;
    }

    private HashSet<String> getImageTypes()
    {
        HashSet<String> values = new HashSet<String>();
        for (FileType c : FileType.imageTypes())
            values.add(c.name());

        return values;
    }
    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_BLACK = "\u001B[30m";
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_GREEN = "\u001B[32m";
    public static final String TEXT_YELLOW = "\u001B[33m";
    public static final String TEXT_BLUE = "\u001B[34m";
    public static final String TEXT_PURPLE = "\u001B[35m";
    public static final String TEXT_CYAN = "\u001B[36m";
    public static final String TEXT_WHITE = "\u001B[37m";

    private void percentageBar()
    {
        Random rnd = new Random();
        int t = 0;
        int[] sizes = new int[]{50, 150, 100, 50, 100, 200, 500, 25, 60, 3};
        String[] colors = new String[]{"\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[36m"};
        int total = 0;
        for (int i : sizes)
            total += i;
        System.out.println("\n");
        for(int i = 0; i < sizes.length; i++) {
            String clr = colors[(i%(colors.length-1))];//colors[rnd.nextInt(colors.length-1)];
            float ans = map((sizes[i]*100) / total, 0, 100, 1, 10);
            // System.out.println(ans);
            for(int j = 0; j <= ans; j++ ) {
                if(ans < 2) {
                    System.out.print(clr+"~"); 
                }else {
                    System.out.print(clr+"=");
                }               
            }
            t++;
        }
        System.out.println(t);
        System.out.println("\n");
    }

    float map(float x, float in_min, float in_max, float out_min, float out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    private void generateManuelFileSystem()
    { 
        Random rnd = new Random();

        // // Define Root
        // Directory root = new Directory();
        // root.setName("DirectoryRoot");

        // // Define directory structure
        // Directory dir10 = new Directory();
        // dir10.setName("Directory-1-0");

        // Directory dir11 = new Directory();
        // dir11.setName("Directory-1-1-0");
        // dir10.addSubNode(dir11);

        // File file110 = new File();
        // file110.setName("File-1-1-0");
        // dir11.addSubNode(file110);

        // File file111 = new File();
        // file111.setName("File-1-1-1");
        // dir11.addSubNode(file111);

        // Directory dir12 = new Directory();
        // dir12.setName("Directory-1-2");
        // dir10.addSubNode(dir12);

        // Directory dir20 = new Directory();
        // dir20.setName("Directory-2-0");

        // File file20 = new File();
        // file20.setName("File-2-0-0");
        // dir20.addSubNode(file20);

        // Directory dir30 = new Directory();
        // dir30.setName("Directory-3-0");

        // Directory dir310 = new Directory();
        // dir310.setName("Directory-3-1-0");
        // dir30.addSubNode(dir310);

        // Directory dir3110 = new Directory();
        // dir3110.setName("Directory-3-1-1-0");
        // dir310.addSubNode(dir3110);

        // Directory dir31110 = new Directory();
        // dir31110.setName("Directory-3-1-1-1-0");
        // dir3110.addSubNode(dir31110);

        // Directory dir311110 = new Directory();
        // dir311110.setName("Directory-3-1-1-1-1-0");
        // dir31110.addSubNode(dir311110);

        // File file311110 = new File();
        // file311110.setName("File-3-1-1-1-1-0");
        // file311110.setSize(Integer.MAX_VALUE / 4);
        // file311110.setType(FileType.TXT);
        // file311110.setWidth(rnd.nextInt(Integer.MAX_VALUE / 16));
        // file311110.setHeight(rnd.nextInt(Integer.MAX_VALUE / 18));
        // file311110.setBitDepth(24);
        // file311110.setCompression(88);
        // dir31110.addSubNode(file311110);

        // Directory dir3111110 = new Directory();
        // dir3111110.setName("Directory-3-1-1-1-1-1-0");
        // dir31110.addSubNode(dir3111110);

        // root.addSubNode(dir10);
        // root.addSubNode(dir20);
        // root.addSubNode(dir30);
    }
}
