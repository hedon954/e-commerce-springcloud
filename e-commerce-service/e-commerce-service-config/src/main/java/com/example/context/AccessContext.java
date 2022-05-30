package com.example.context;

import cn.hutool.log.Log;
import com.example.vo.LoginUserInfo;

/**
 * <h1>使用 ThreadLocal 去单独存储每一个线程携带的 LoginUserInfo 信息</h1>
 *
 * 为了避免线程泄露，要及时清理 ThreadLocal
 * - 1. 保证没有资源泄露
 * - 2. 保证线程重用时，不会出现数据泄露
 *
 * @author Hedon Wang
 * @create 2022-05-30 5:34 PM
 */
public class AccessContext {

    private static final ThreadLocal<LoginUserInfo> loginUserInfo = new ThreadLocal<>();

    public static LoginUserInfo getLoginUserInfo() {
        return loginUserInfo.get();
    }

    public static void setLoginUserInfo(LoginUserInfo loginUserInfo_) {
        loginUserInfo.set(loginUserInfo_);
    }

    public static void clearLoginUserInfo(){
        loginUserInfo.remove();
    }

}
