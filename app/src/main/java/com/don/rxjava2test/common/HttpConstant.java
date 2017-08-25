package com.don.rxjava2test.common;

/**
 * <p>
 * Description：Http常量
 * </p>
 */

public class HttpConstant {

    public static String BASE_URL = "http://192.168.1.208";

    public static final String VERSION = "v1_0_0";

    public static final String BASE_API_URL = "/api/" + VERSION + ".php?r=";

    //----------------------通行证------------------//

    /**
     * 检查是否为账号
     */
    public static final String IS_ACCOUNT = HttpConstant.BASE_API_URL + "passport/account";

}
