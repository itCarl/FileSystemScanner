package de.blubber_lounge.FileSystemScanner.node;

import java.util.ArrayList;
import java.util.List;

public abstract class Node implements INode
{
    private boolean canHaveSubNodes = false;
    private List<Node> subNodes = new ArrayList<Node>();

    private String name;
    private int id;

    public boolean getCanHaveSubNodes()
    {
        return this.canHaveSubNodes;
    }

    public void canHaveSubNodes(boolean canHaveSubNodes)
    {
        this.canHaveSubNodes = canHaveSubNodes;
    }

    public List<Node> getSubNodes()
    {
        return this.subNodes;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void addSubNode(Node node)
    {
        if(this.canHaveSubNodes)
            this.subNodes.add(node);
    }

    protected String getObjectType()
    {
        return this.getClass().getName();
    }
}
