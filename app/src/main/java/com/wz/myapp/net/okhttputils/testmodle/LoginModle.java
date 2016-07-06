package com.wz.myapp.net.okhttputils.testmodle;

/**
 * Created by dell on 2016/7/6.
 */
public class LoginModle {

    /**
     * scode : 0
     * needCode : 0
     * sessionID :
     * head : {"token":"TmnV7MjKiQjk3kU62AdQCSTB42lqphhB","uid":101,"tid":101,"isex":false,"company":"大米科技"}
     * body : {"name":"刘志强","passport":"liuzq@9.cn","passwordFlag":0,"icon":"https://xsybucket.s3.cn-north-1.amazonaws.com.cn/101/2016/05/26/s_c6adf50e-0c22-4760-ab0e-609ecc348de5.jpg","serverTime":1467783046206,"language":1,"isTrialTenant":true,"tryTimes":"","noLoginDays":"","validateRuleStatus":"","isPartnerUser":0,"prmViewUserId":"-1"}
     */

    public String scode;
    public int needCode;
    public String sessionID;
    /**
     * token : TmnV7MjKiQjk3kU62AdQCSTB42lqphhB
     * uid : 101
     * tid : 101
     * isex : false
     * company : 大米科技
     */

    public HeadBean head;
    /**
     * name : 刘志强
     * passport : liuzq@9.cn
     * passwordFlag : 0
     * icon : https://xsybucket.s3.cn-north-1.amazonaws.com.cn/101/2016/05/26/s_c6adf50e-0c22-4760-ab0e-609ecc348de5.jpg
     * serverTime : 1467783046206
     * language : 1
     * isTrialTenant : true
     * tryTimes :
     * noLoginDays :
     * validateRuleStatus :
     * isPartnerUser : 0
     * prmViewUserId : -1
     */

    public BodyBean body;
    public static class HeadBean {
        public String token;
        public int uid;
        public int tid;
        public boolean isex;
        public String company;
    }

    public static class BodyBean {
        public String name;
        public String passport;
        public int passwordFlag;
        public String icon;
        public long serverTime;
        public int language;
        public boolean isTrialTenant;
        public String tryTimes;
        public String noLoginDays;
        public String validateRuleStatus;
        public int isPartnerUser;
        public String prmViewUserId;
    }
}
