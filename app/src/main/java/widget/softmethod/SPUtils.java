package widget.softmethod;

import android.content.Context;
import android.content.SharedPreferences;


import com.wz.myapp.AppApplication;

/**
 * Created by wz on 2015/12/4.
 */
public class SPUtils {

    public static String CONVERSATION_RECORD = "sms_record";
    public static String SMS_RECORD = "sms_record";
    public static String PHONE_RECORD = "phone_record";
    public static String CALL_MSG = "call_msg";

    public static String SOFT_METHOD_HEIGHT = "soft_method_height";


    public static void saveSmsRecord(String phoneNum) {

        SharedPreferences sp = AppApplication.getInstance().getSharedPreferences(CONVERSATION_RECORD, Context.MODE_PRIVATE);

        sp.edit().putString(SMS_RECORD, phoneNum).commit();
    }

    public static void savePhoneRecord(String phoneNum, long objectId, String belongId, int isFromDetail) {

        SharedPreferences sp = AppApplication.getInstance().getSharedPreferences(CONVERSATION_RECORD, Context.MODE_PRIVATE);
        if (objectId == -1) {
            sp.edit().putString(PHONE_RECORD, -1 + "").commit();
        } else {
            sp.edit().putString(PHONE_RECORD, phoneNum + "," + objectId + "," + belongId + "," + isFromDetail).commit();
        }
    }


    public static String[] getSmsRecord() {
        SharedPreferences sp = AppApplication.getInstance().getSharedPreferences(CONVERSATION_RECORD, Context.MODE_PRIVATE);
        String phoneNum = sp.getString(SMS_RECORD, "-1");
        if (phoneNum.contains(",")) {
            return phoneNum.split(",");
        }
        return new String[]{"-1", "-1"};
    }

    public static String[] getPhoneRecord() {

        SharedPreferences sp = AppApplication.getInstance().getSharedPreferences(CONVERSATION_RECORD, Context.MODE_PRIVATE);
        String value = sp.getString(PHONE_RECORD, "-1");
        if (value.contains(",")) {
            return value.split(",");
        }
        return null;
    }

    public static void saveCallMsg(String phoneNum, long when) {
        SharedPreferences sp = AppApplication.getInstance().getSharedPreferences(CONVERSATION_RECORD, Context.MODE_PRIVATE);
        if (phoneNum == null) {
            sp.edit().putString(CALL_MSG, "-1");
        } else {
            sp.edit().putString(CALL_MSG, phoneNum + "," + when).commit();
        }

    }

    public static String[] getCallMsg() {
        SharedPreferences sp = AppApplication.getInstance().getSharedPreferences(CONVERSATION_RECORD, Context.MODE_PRIVATE);
        String phoneNum = sp.getString(CALL_MSG, "-1");
        if (phoneNum.contains(",")) {
            return phoneNum.split(",");
        }
        return null;
    }

    public static void saveSoftMethodHeight(int height) {
        SharedPreferences sp = AppApplication.getInstance().getSharedPreferences(SOFT_METHOD_HEIGHT, Context.MODE_PRIVATE);

        sp.edit().putInt("softMethodHeight", height).commit();
    }

    public static int getSoftMehthodHeight() {
        SharedPreferences sp = AppApplication.getInstance().getSharedPreferences(SOFT_METHOD_HEIGHT, Context.MODE_PRIVATE);
        return sp.getInt("softMethodHeight", 0);
    }
}
