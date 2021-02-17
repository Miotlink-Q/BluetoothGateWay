/*
 * 
 */
package com.miot.android.data.d.file;

import android.content.Context;
import android.text.TextUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * 反射工具类
 * 通过反射获得对应函数功能.
 *
 * @ClassName: ReflectionUtil
 * @Description:
 * @author  作者 E-mail <a href="mailto:yubo@51box.cn">qiaozhuang</a>
 * @version 创建时间：2013-12-25 14:11:40
 * Reflection util.
 */
public class ReflectionUtil {

    /**
     * 通过类名，运行指定方法.
     *
     * @param cName         类名
     * @param methodName    方法名
     * @param params        参数值
     * @return 失败返回null
     */
    public static Object invokeByClassName(String cName, String methodName, Object[] params) {
        
    	if(TextUtils.isEmpty(cName)
        		|| TextUtils.isEmpty(methodName)){
        		return null;
        }
    	
        Object retObject = null;
        
        try {
            // 加载指定的类
            Class cls = Class.forName(cName);
            // 利用newInstance()方法，获取构造方法的实例
            Constructor ct = cls.getConstructor();
            Object obj = ct.newInstance();
            // 根据方法名获取指定方法的参数类型列表
            Class paramTypes[] = _getParamTypes(cls, methodName);
            
            // 获取指定方法
            Method meth = cls.getMethod(methodName, paramTypes);
            meth.setAccessible(true);
            
            // 调用指定的方法并获取返回值为Object类型
            if(params == null){
            	retObject = meth.invoke(obj);
            } else {
            	retObject = meth.invoke(obj, params);
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        return retObject;
    }
    
    /**
     * 通过类对象，运行指定方法.
     *
     * @param obj 类对象
     * @param methodName 方法名
     * @param params 参数值
     * @return 失败返回null
     */
    public static Object invokeByObject(Object obj, String methodName, Object... params) {
    	if(obj == null
    		|| TextUtils.isEmpty(methodName)){
    		return null;
    	}
//		StorageManager storageManager=
        Class cls = obj.getClass();
        Object retObject = null;
        try {
			Method meth=null;
			if(params==null||params[0]==null){
				meth=cls.getMethod(methodName);
			}else{
				meth=cls.getMethod(methodName,_getParamTypes(cls,methodName));
			}
			// 根据方法名获取指定方法的参数类型列表
            Class paramTypes[] = _getParamTypes(cls, methodName);
            // 获取指定方法
//            Method meth = cls.getMethod(methodName, null);
            meth.setAccessible(true);

            if(params == null||params[0]==null){
            	retObject = meth.invoke(obj);
            } else {
				meth.setAccessible(true);
				retObject = meth.invoke(obj, (Object)params);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        return retObject;
    }
    
    /**
     * 获取参数类型，返回值保存在Class[]中.
     *
     * @param cls 类
     * @param mName 方法名字
     * @return 返回产生类型列表
     */
    private static Class[] _getParamTypes(Class cls, String mName) {
        Class[] cs = null;
        
        /*
         * Note: 由于我们一般通过反射机制调用的方法，是非public方法
         * 所以在此处使用了getDeclaredMethods()方法
         */
        Method[] mtd = cls.getDeclaredMethods();
        for (int i = 0; i < mtd.length; i++) {
            if (!mtd[i].getName().equals(mName)) {    // 不是我们需要的参数，则进入下一次循环
                continue;
            }
            
            cs = mtd[i].getParameterTypes();
        }
        return cs;
    }
    
    /**
     * 获取内置SD卡路径.
     *
     * @param context the context
     * @return the internal sd card path
     */
    public static List<String> getInternalSDCardPath(Context context){
    	List<String> pathes = new ArrayList<String>();
		Object sm = context.getSystemService(Context.STORAGE_SERVICE);
		Object[] paramClasses = {};
		Object[] ob = (Object[])invokeByObject(sm, "getVolumeList", (Object)null);

    	if(ob != null) {
			for (int i=0;i<ob.length;i++){
				Object o=ob[i];
				final String PATH = "mPath=";
				final String REMOVABLE = "mRemovable=";
				String path = null;
				String removable = null;
				try {
					path = o.toString();
					if(path.indexOf(PATH)!=-1){
					    String storage="";
					    String [] paths=path.split("\\n");
					    if (paths.length>0){
                            for (int p=0;p<paths.length;p++){
                                if (paths[p].contains(PATH)){
                                    storage=paths[p].replaceAll(" ","");
                                    storage = storage.substring(storage.indexOf(PATH)+PATH.length(),storage.length());
                                    break;
                                }
                            }
                        }
                        if (TextUtils.isEmpty(storage)){
					        path=null;
                        }
                    }else {
					    path=null;
                    }
					removable = o.toString();
					if(removable.indexOf(REMOVABLE)!=-1){
                        removable = removable.substring(removable.indexOf(REMOVABLE));
                    }else {
					    removable=null;
                    }
				} catch (Exception e) {
					e.printStackTrace();
					path = null;
				}

				if(!TextUtils.isEmpty(path) && !TextUtils.isEmpty(removable) ){

					pathes.add(path);
				}
			}

    	}
    	return pathes;
    }
}
