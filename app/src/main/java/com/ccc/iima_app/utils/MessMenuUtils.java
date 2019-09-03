package com.ccc.iima_app.utils;

import java.util.List;

public class MessMenuUtils {

    public String getListAsString(List<String> currentMenu) {
        String items = "";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(items);
        for (String value : currentMenu) {
            stringBuilder.append(value);
            stringBuilder.append(", ");
        }
        String listString = stringBuilder.toString();
        listString = listString.length() > 2 ? listString.substring(0, listString.length() - 2) : listString;
        return listString;
    }
}
