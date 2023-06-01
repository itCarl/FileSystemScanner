package de.blubber_lounge.FileSystemScanner.node;

import de.blubber_lounge.FileSystemScanner.util.FancyOutput;
import de.blubber_lounge.FileSystemScanner.util.FileType;

public class File extends Node
{
    private long size;
    private FileType type;

    private int height;
    private int width;
    private int bitDepth;
    private long compression;

    public File()
    {
        this.setSize(-1);
        this.setType(FileType.UNKOWN);
        this.setHeight(-1);
        this.setWidth(-1);
        this.setBitDepth(-1);
        this.setCompression(-1);
    }

    public String getInfo()
    {
        // return this.getObjectType() +" "+ this.getName();
        return this.getName() + FancyOutput.arrow 
            +"FileType:"+ this.getType() + FancyOutput.seperator
            +"Size:"+ this.getSize() + FancyOutput.seperator
            +"WxH:"+ this.getWidth() +"x"+ this.getHeight() + FancyOutput.seperator
            +"Bit Depth:"+ this.getBitDepth() + FancyOutput.seperator
            +"Compression:"+ this.getCompression();
    }

    public long getSize()
    {
        return this.size;
    }

    public void setSize(long size)
    {
        this.size = size;
    }

    public String getType()
    {
        return this.type.toString();
    }

    public void setType(FileType type)
    {
        this.type = type;
    }

    public int getHeight()
    {
        return this.height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getWidth()
    {
        return this.width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getBitDepth()
    {
        return this.bitDepth;
    }

    public void setBitDepth(int bitDepth)
    {
        this.bitDepth = bitDepth;
    }
    
    public long getCompression()
    {
        return this.compression;
    }

    public void setCompression(long compression)
    {
        this.compression = compression;
    }
}
