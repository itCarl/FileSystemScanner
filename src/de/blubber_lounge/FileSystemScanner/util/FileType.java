package de.blubber_lounge.FileSystemScanner.util;

import java.util.List;
import java.util.ArrayList;

public enum FileType
{
    //UNKOWN("Test"),
    UNKOWN,
    JPG,
    JPEG,
    PNG,
    GIF,
    TXT;

    public static List<FileType> imageTypes()
    {
        List<FileType> types = new ArrayList<FileType>();
        
        types.add(JPG);
        types.add(JPEG);
        types.add(PNG);
        types.add(GIF);

        return types;
    }
    
    // public final String label;

    // private FileType(String label)
    // {
    //     this.label = label;
    // }
}
