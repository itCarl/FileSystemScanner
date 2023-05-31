package de.blubber_lounge.FileSystemScanner.node;

public class Directory extends Node
{
    public Directory()
    {
        this.canHaveSubNodes(true);
    }

    public String getInfo()
    {
        // return this.getObjectType() +" "+ this.getName();
        return this.getName();
    }
}
