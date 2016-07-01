package com.wz.myapp.net.okhttputils.helper;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Yi
 * Date: 13-1-30
 * Time: 下午2:57
 */
public class ResFileHelper {

//    public static final String SP_RES = "sp_res";

//    public static void fileSave(Context context,JsonResource resource){
////        SharedPreferences sp = context.getSharedPreferences(SP_RES, Context.MODE_PRIVATE);
////        sp.edit().putString(resource.id + "_" + resource.name, getFileName(resource)).commit();
////        sp.edit().putString(resource.url + "_" + resource.name, getFileName(resource)).commit();
//        resource.save_status = JsonResource.STATUS_SAVED;
//    }
//
//    public static String getFileName(JsonResource resource){
//        String path = com.rkhd.ingage.core.cache.CacheManager.get().getStoragePath() + "/";
//        return path + resource.id + "_" + resource.name;
//    }
//
//    public static boolean isFileSaved(Context context,JsonResource resource){
////        SharedPreferences sp = context.getSharedPreferences(SP_RES,Context.MODE_PRIVATE);
////        String filePath = sp.getString(resource.id + "_" + resource.name,null);
////        if(TextUtils.isEmpty(filePath)){
////            filePath = sp.getString(resource.url+"_" + resource.name,null);
////        }
////        if(TextUtils.isEmpty(filePath)){
////            return false;
////        }
//        String filePath = getFileName(resource);
//        File file = new File(filePath);
//        if(file.exists()){
//            return true;
//        }else{
////            sp.edit().remove(resource.id + "_" + resource.name).commit();
//            return false;
//        }
//    }
    public static void checkFilesSaved(final Context context){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                SharedPreferences sp = context.getSharedPreferences(SP_RES, Context.MODE_PRIVATE);
//                Map fileMap = sp.getAll();
//                for(Object key:fileMap.keySet()){
//                    String filePath = (String) fileMap.get(key);
//                    if(TextUtils.isEmpty(filePath)){
//                        sp.edit().remove((String) key).commit();
//                        continue;
//                    }
//                    File file = new File(filePath);
//                    if( !file.exists() ){
//                        sp.edit().remove((String) key).commit();
//                    }
//                }
//            }
//        }).start();
    }



    public static Intent openFile(String filePath){

        File file = new File(filePath);
        if(!file.exists()) return getAllIntent(filePath);
        /* 取得扩展名 */
        String end=file.getName().substring(file.getName().lastIndexOf(".") + 1,file.getName().length()).toLowerCase();
        /* 依扩展名的类型决定MimeType */
        if(end.contains("m4a")||end.contains("mp3")||end.contains("mid")||
                end.contains("xmf")||end.contains("ogg")||end.contains("wav")){
            return getAudioFileIntent(filePath);
        }else if(end.contains("3gp")||end.contains("mp4")||end.contains("avi")||end.contains("wma")||end.contains("rmvb")||end.contains("rm")||end.contains("mid")){
            return getVideoFileIntent(filePath);
        }else if(end.contains("jpg")||end.contains("gif")||end.contains("png")||
                end.contains("jpeg")||end.contains("bmp")){
            return getImageFileIntent(filePath);
        }else if(end.contains("apk")){
            return getApkFileIntent(filePath);
        }else if(end.contains("ppt")){
            return getPptFileIntent(filePath);
        }else if(end.contains("xls")){
            return getExcelFileIntent(filePath);
        }else if(end.contains("doc")){
            return getWordFileIntent(filePath);
        }else if(end.contains("pdf")){
            return getPdfFileIntent(filePath);
        }else if(end.contains("chm")){
            return getChmFileIntent(filePath);
        }else if(end.contains("txt")){
            return getTextFileIntent(filePath,false);
        }else{
            return getAllIntent(filePath);
        }
    }

    //Android获取一个用于打开未知文件的intent
    public static Intent getAllIntent( String param ) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri,"*/*");
        return intent;
    }
    //Android获取一个用于打开APK文件的intent
    public static Intent getApkFileIntent( String param ) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri,"application/vnd.android.package-archive");
        return intent;
    }

    //Android获取一个用于打开VIDEO文件的intent
    public static Intent getVideoFileIntent( String param ) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    //Android获取一个用于打开AUDIO文件的intent
    public static Intent getAudioFileIntent( String param ){

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }



    //Android获取一个用于打开Html文件的intent
    public static Intent getHtmlFileIntent( String param ){

        Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param ).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    //Android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent( String param ) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    //Android获取一个用于打开PPT文件的intent
    public static Intent getPptFileIntent( String param ){

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    //Android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent( String param ){

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    //Android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent( String param ){

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    //Android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent( String param ){

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    //Android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent( String param, boolean paramBoolean){

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean){
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        }else{
            Uri uri2 = Uri.fromFile(new File(param));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }
    //Android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent( String param ){

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }


    public static void openFile(Context context,String path){
        Intent intent = ResFileHelper.openFile(path);
        try{
            context.startActivity(intent);
        } catch (ActivityNotFoundException exception){
            intent = ResFileHelper.getAllIntent(path);
            context.startActivity(intent);
        }
    }

}
