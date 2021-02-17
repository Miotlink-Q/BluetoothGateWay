package com.miot.android.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import android.text.TextUtils;

import com.miot.android.data.d.file.MultiCard;
import com.miot.android.data.d.utils.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;

public class FileManager {


    private static String FILE_NAME = "mlink";

    private static FileManager instance=null;

    /*框架本地文件夹*/
    private static final String HTML_URL = "/.html/framework";


    private static final String FRAME_HTML_URL_TEST="/test.html";
    private static final String FRAME_HTML_HILDE_URL_TEST="/miot-hide.html";

    public static synchronized FileManager getInstance() {
        if (instance==null){
            synchronized (FileManager.class){
                if (instance==null){
                    instance=new FileManager();
                }
            }
        }
        return instance;
    }

    public void init(String fileName){
        FileManager.FILE_NAME=fileName;
    }

    public String getFrameHtmlPath(){
        return Environment
                .getExternalStorageDirectory()
                + "/" + FileManager.FILE_NAME + "/.html/framework";
    }

    /**
     * 框架本地文件夹
     * @return
     */
    public String getFrameHtmlUrl(){
        return "/.html/framework";
    }

    /**
     * 获取zip下载路径
     * @return
     */
    public String getZipPath(){
        return Environment
                .getExternalStorageDirectory()
                + "/" + FileManager.FILE_NAME + "/" + ".zip/framework.zip";
    }




    public static final String FRAME_HTML = FileManager.PATH_URL + "framework";

    public static final String FRAME_ZIP = Environment
            .getExternalStorageDirectory()
            + "/" + FileManager.FILE_NAME + "/" + ".zip/framework.zip";

    public static final String FRAME_ZIP_1 = Environment
            .getExternalStorageDirectory()
            + "/" + FileManager.FILE_NAME + "/" + ".zip";


    public static final String FRAME_HTML_URL = Environment
            .getExternalStorageDirectory()
            + "/" + FileManager.FILE_NAME + "/" + FileManager.HTML_URL;

    @NonNull
    public static String PATH_URL = Environment
            .getExternalStorageDirectory()
            + "/" + FileManager.FILE_NAME + "/.html/";

    @NonNull
    public static String PATH_FRAMEWOR_URL = Environment
            .getExternalStorageDirectory()
            + "/"
            + FileManager.FILE_NAME
            + HTML_URL;

    public static final String PATH_IMAGE_SCREEN = Environment
            .getExternalStorageDirectory()
            + "/"
            + FileManager.FILE_NAME
            + "/" + "image";

    public static final String PATH_VIDEO_PATH = Environment
            .getExternalStorageDirectory()
            + "/"
            + FileManager.FILE_NAME
            + "/" + "video";

    public static final String HOME_PATH = Environment
            .getExternalStorageDirectory()
            + "/" + FileManager.FILE_NAME + "/.";

    public static final String FARTMPATH=FRAME_HTML_URL+FRAME_HTML_URL_TEST;
    public static final String FARTMHIDLEPATH=FRAME_HTML_URL+FRAME_HTML_HILDE_URL_TEST;

    public static boolean isFrameHtmlExit(){
        File file=new File(FARTMPATH);
        if (file.exists()){
            return  true;
        }
        return false;
    }
    public static boolean isFrameHidleHtmlExit(){
        File file=new File(FARTMHIDLEPATH);
        if (file.exists()){
            return  true;
        }
        return false;
    }


    public static boolean isFileExit(String path) {
        String file = MultiCard.getInstance().getReadPath(StringUtils.getNameByUrl(path));
        if (file.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 创建一个新的文件夹
     *
     * @param name
     * @return
     */
    @NonNull
    public static File newFile(String name) {
        File mDirectory = new File(HOME_PATH + "." + name);
        if (!mDirectory.exists()) {
            mDirectory.mkdir();
        }
        return mDirectory;
    }

    /**
     * 判断框架H5是否存在
     *
     * @return
     */
    public static boolean isFrameWorkHtmlIsExit() {
        File file = new File(FRAME_HTML_URL + "/test.html");
        if (file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 判断H5插件是否存在
     *
     * @param modelId
     * @return
     */
    public static boolean isPluginHtmlIsExit(String modelId) {
        File file = new File(PATH_URL + modelId + "/" + modelId + "_index.html");
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static void delFrameWork() {
        File file = new File(FRAME_HTML_URL);
        if (file != null && file.exists()) {
            RecursionDeleteFile(file);
        }
    }

    public static void delFrameWorkZip() {
        File file = new File(FRAME_ZIP);
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    public static void RecursionDeleteFile(@NonNull File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }

    public static void deleteFile(@NonNull String pathZip) {
        try {
            if (!TextUtils.isEmpty(pathZip)) {
                File file = new File(pathZip);
                if (file != null && file.exists() && !file.isDirectory()) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //private String url = "http://www.51miaomiao.com/statics/docs/app/html/framework.zip";
    @NonNull
    public static String splitUrl(@NonNull String url, String suffix) {
        String[] str = url.split("\\/");
        if (str.length > 0) {
            for (String s : str) {
                if (s.endsWith("." + suffix)) {
                    return s;
                }
            }
        }
        return "";
    }

    @NonNull
    public static String splitUrl(@NonNull String url, @NonNull String[] suffixs) {
        if(TextUtils.isEmpty(url)){
            return "";
        }
        String[] str = url.split("\\/");
        if (str!=null&&str.length > 0) {
            for(int i=0;i<suffixs.length;i++){
                for (String s : str) {
                    if (s.endsWith("." + suffixs[i])) {
                        return s;
                    }
                }
            }
        }
        return "";
    }


    public static void deleteOldFile(@NonNull String url, @NonNull String suffix) {
        String fileName = splitUrl(url, suffix);
        if (!fileName.equals("")) {
            if (suffix.equals("apk")) {
                deleteFile(HOME_PATH + "apk/" + fileName);
            }
            if (suffix.equals("jar")) {
                deleteFile(HOME_PATH + "jar/" + fileName);
            }
            if (suffix.equals("zip")) {
                deleteFile(HOME_PATH + "zip/" + fileName);
            }
        }
    }

    public static boolean copyFrameworkToMiotAllHouse(@NonNull Context context, String fileName){
        BufferedInputStream is=null;
        BufferedOutputStream os=null;
        AssetManager manager=context.getAssets();
        try {
            File file=new File(HOME_PATH+"zip/");
            if(!file.exists()){
                file.mkdirs();
            }
            os=new BufferedOutputStream(new FileOutputStream(file+"/"+fileName));
            is=new BufferedInputStream(manager.open(fileName, AssetManager.ACCESS_BUFFER));
            byte[] b=new byte[10240];
            int len=0;
            while ((len=is.read(b))!=-1){
                os.write(b,0,len);
                os.flush();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @NonNull
    public static String getAppAssetsContent(@NonNull Context context, String fileName){
        String resultString="";
        try {
            InputStream inputStream=context.getResources().getAssets().open(fileName);
            byte[] buffer=new byte[inputStream.available()];
            inputStream.read(buffer);
            resultString=new String(buffer,"UTF-8");
        } catch (Exception e) {
        }
        return resultString;
    }


}
