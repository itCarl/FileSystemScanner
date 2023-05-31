package de.blubber_lounge.FileSystemScanner;

import java.util.Random;

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
        Directory rootDir = new Directory();
        rootDir.setName(root.getName());

        System.out.println("Scan starting... \n");
        System.out.println("=== ROOT ===\n" + root.getName() +" (FullPath:"+ root.getAbsolutePath() + ")\n============");

        // MAIN PART
        this.readFileSystem(rootDir, root);

        // OUTPUT        
        this.ListSubNodesOfNode(rootDir);
        
        System.out.println("\nScan Completed.");
    }

    protected void readFileSystem(Node currentNode, java.io.File currentDirectory)
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
                readFileSystem(dir, f);
            } else {
                File file = new File();
                file.setName(f.getName());
                currentNode.addSubNode(file);
                readFileSystem(file, f);
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
