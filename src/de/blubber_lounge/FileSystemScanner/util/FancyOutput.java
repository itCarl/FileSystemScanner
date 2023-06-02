package de.blubber_lounge.FileSystemScanner.util;

public class FancyOutput
{
    public static String arrow = " => ";
    public static String seperator = " - ";

    public static String getIndentLines(int depth)
    {
        return depth > 0
            ? new String(new char[depth]).replace("\0", "----") + " "
            : "";
    }    
}
