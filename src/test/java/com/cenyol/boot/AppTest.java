package com.cenyol.boot;

import com.cenyol.boot.utils.StringUtils;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
//    String dirPath = "/Users/cenyol/Downloads/txt/";    // 原文件位置
    String dirPath = "/Users/cenyol/Movies/XXOO/";    // 原文件位置
    String zipDir = "/Users/cenyol/tmp/zip/";           // 压缩包存放位置
    List<String> nameList = new ArrayList<>();          // new name : password : old name
    List<String> filePathList = new ArrayList<>();      // new file path:old file path
    String indexFile = "/Users/cenyol/tmp/Q758633783531982.txt";

    @Test
    public void zipFileWithPassword() {
        displayDirsOrFiles(dirPath);

//        String indexFile = dirPath + nanoTimeName() + ".txt";
        writeToTxtFile(nameList, indexFile);
        String password = StringUtils.getRandomString(32);
        String indexFileZip = zipDir + nanoTimeName() + ".zip";
        zipFile(indexFile, indexFileZip, password);
        System.out.println(password);

        recoveryFileName(filePathList);
    }

    private void recoveryFileName(List<String> filePathList) {
        filePathList.stream().forEach( item -> {
            try {
                String[] fileNameInfo = item.split(":");
                File f = new File(fileNameInfo[0]);
                if (f.isFile()) {
                    f.renameTo(new File(fileNameInfo[1]));
                }
            } catch (Exception e) {
                System.err.println("recoveryFileName Error： " + e);
            }
        });
    }

    /**
     * 得到给定路径下的目录或是文件
     * @param strPath
     * @throws Exception
     * 参考自：https://blog.csdn.net/lemon_tree12138/article/details/39452547
     */
    private void displayDirsOrFiles(String strPath) {
        try {
            File f = new File(strPath);
            if (f.isDirectory()) {
                File[] fList = f.listFiles();
                Arrays.stream(fList).parallel().filter( File::isDirectory )
                        .forEach( directory -> {
//                        System.out.println("Directory is: "	+ fList[j].getPath());
                            displayDirsOrFiles(directory.getPath()); // 对当前目录下仍是目录的路径进行遍历
                        });

                Arrays.stream(fList).parallel().filter( File::isFile )
                        .forEach( file -> {
                        String oldPath = file.getPath();

                        int positionLastSlash = oldPath.lastIndexOf('/');
                        String oldName = oldPath.substring(positionLastSlash + 1);

                        if (!isVideoFile(oldName)) {
                            return;
                        }
                        if (oldName.startsWith(".") ||      // 过滤Mac自带的一些DS_Store文件
                                oldName.startsWith("QQQQ")) {  // Q的表明该文件已经命名过
                            return;
                        }
//                        System.out.println("Filename is: " + oldName);

                        int positionLastDot = oldPath.lastIndexOf('.');
                        String suffix = oldPath.substring(positionLastDot);

                        String newName = nanoTimeName();

                        StringBuilder newFilePath = new StringBuilder(file.getParent())
                                .append("/").append(newName).append(suffix);
//                        System.out.println("File newName is: " + newFilePath);
                            file.renameTo(new File(newFilePath.toString()));

                        String password = StringUtils.getRandomString(8);
                        zipFile(newFilePath.toString(), zipDir+newName+".zip", password);

                        StringBuilder fileDigest = new StringBuilder();
                        fileDigest.append(newName).append(suffix).append(" : ");
                        fileDigest.append(password).append(" : ");
                        fileDigest.append(file.getPath());
                        nameList.add(fileDigest.toString());

                        StringBuilder filePath = new StringBuilder(newFilePath.toString());
                        filePath.append(":").append(oldPath);
                        filePathList.add(filePath.toString());
                });
            }
        } catch (Exception e) {
            System.err.println("displayDirsOrFiles Error： " + e);
        }
    }

    private boolean isVideoFile(String name) {
        if (name.endsWith(".mp4")
                || name.endsWith(".avi")
                || name.endsWith(".mkv")
                || name.endsWith(".flv")
                || name.endsWith(".mov")
                || name.endsWith(".rmvb")) {
            return true;
        }
        return false;
    }

    private String nanoTimeName() {
        return "Q" + System.nanoTime();
    }

    private void writeToTxtFile(List<String> nameList, String filePath) {
        try {
            String line = System.getProperty("line.separator");
            StringBuffer str = new StringBuffer();
            FileWriter fw = new FileWriter(filePath, true);             // 以append的方式，在文件尾部写入
            nameList.stream().forEach(item -> str.append(item).append(line));
            fw.write(str.toString());
            fw.close();
        } catch (IOException e) {
            System.err.println("writeToTxtFile Error： " + e);
        }
    }

    private void zipFile(String srcFile, String dest, String passwd) {
        File srcfile = new File(srcFile);

        //创建目标文件
        String destname = buildDestFileName(srcfile, dest);
        ZipParameters par = new ZipParameters();
        par.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        par.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

        if (passwd != null) {
            par.setEncryptFiles(true);
            par.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            par.setPassword(passwd.toCharArray());
        }

        try {
            ZipFile zipfile = new ZipFile(destname);
            if (srcfile.isDirectory()) {
                zipfile.addFolder(srcfile, par);
            } else {
                zipfile.addFile(srcfile, par);
            }
        } catch (ZipException e) {
            System.err.println("zipFile Error： " + e);
        }
    }
    private String buildDestFileName(File srcfile, String dest) {
        if (dest == null) {
            if (srcfile.isDirectory()) {
                dest = srcfile.getParent() + File.separator + srcfile.getName() + ".zip";
            } else {
                String filename = srcfile.getName().substring(0, srcfile.getName().lastIndexOf("."));
                dest = srcfile.getParent() + File.separator + filename + ".zip";
            }
        } else {
            createPath(dest);//路径的创建
            if (dest.endsWith(File.separator)) {
                String filename = "";
                if (srcfile.isDirectory()) {
                    filename = srcfile.getName();
                } else {
                    filename = srcfile.getName().substring(0, srcfile.getName().lastIndexOf("."));
                }
                dest += filename + ".zip";
            }
        }
        return dest;
    }
    private void createPath(String dest) {
        File destDir = null;
        if (dest.endsWith(File.separator)) {
            destDir = new File(dest);//给出的是路径时
        } else {
            destDir = new File(dest.substring(0, dest.lastIndexOf(File.separator)));
        }

        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

}
