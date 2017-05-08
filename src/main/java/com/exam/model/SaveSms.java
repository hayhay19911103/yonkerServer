package com.exam.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2017/2/28.
 */
public class SaveSms {
    private static  List<UserSms> userSmsList = new ArrayList<UserSms>();;

    public static List<UserSms> getUserSmsList() {
        return userSmsList;
    }

    public static void setUserSmsList(List<UserSms> userSmsList) {
        SaveSms.userSmsList = userSmsList;
    }

}
