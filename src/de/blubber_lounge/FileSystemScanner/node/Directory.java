package de.blubber_lounge.FileSystemScanner.node;

import de.blubber_lounge.FileSystemScanner.util.FancyOutput;

public class Directory extends Node
{
    public Directory()
    {
        this.canHaveSubNodes(true);
    }

    public String getInfo()
    {
        // return this.getObjectType() +" "+ this.getName();
        return this.getName() + FancyOutput.arrow 
        +"Size:"+ this.getSize();
    }
}
