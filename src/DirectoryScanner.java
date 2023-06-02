import java.io.File;
import java.text.DecimalFormat;

public class DirectoryScanner {
    
    public static void main(String[] args) {
        String directoryPath = "scanMeDirectory";
        scanDirectory(directoryPath);
    }
    
    public static void scanDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory path: " + directoryPath);
            return;
        }
        
        long totalSize = 0;
        File[] files = directory.listFiles();
        
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    long fileSize = file.length();
                    totalSize += fileSize;
                    System.out.println(file.getName() + " - " + fileSize + " bytes (" + getFileSizePercentage(fileSize, totalSize) + "%)");
                }
            }
            
            for (File file : files) {
                if (file.isDirectory()) {
                    long subTotalSize = scanSubDirectory(file);
                    totalSize += subTotalSize;
                    System.out.println("Subdirectory: " + file.getName() + " - Total Size: " + subTotalSize + " bytes");
                }
            }
        }
        
        System.out.println("Total size: " + totalSize + " bytes");
    }
    
    public static long scanSubDirectory(File subDirectory) {
        long subTotalSize = 0;
        File[] files = subDirectory.listFiles();
        
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    subTotalSize += file.length();
                }
            }
        }
        
        return subTotalSize;
    }
    
    public static String getFileSizePercentage(long fileSize, long totalSize) {
        double percentage = (double) fileSize / totalSize * 100;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(percentage);
    }
}