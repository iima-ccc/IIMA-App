package com.ccc.iima_app.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ccc.navigationdrawer.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ccc.iima_app.utils.MessMenuConstants.BREAKFAST;
import static com.ccc.iima_app.utils.MessMenuConstants.DINNER;
import static com.ccc.iima_app.utils.MessMenuConstants.LUNCH;
import static com.ccc.iima_app.utils.MessMenuConstants.REST_API_URL;

public class MessMenuDAO {

    MessMenuUtils messMenuUtils = new MessMenuUtils();
    String[] listOfCards = {BREAKFAST, LUNCH, DINNER};

    public Map<String, List<String>> getMenu(final Context context, Date date, final ListView messMenuListView) {
        final Map<String, List<String>> messMenu = new HashMap<>();
        List<String> listOfValues = Arrays.asList(new String[]{"Poha", "Bread Butter", "Sandwich", "Pani Puri", "Idli fry", "Burger"});
        messMenu.put(BREAKFAST, listOfValues);
        messMenu.put(LUNCH, listOfValues);
        messMenu.put(DINNER, listOfValues);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject("{\"date\": " + date.getTime() + " }");
            Log.e("json print", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                REST_API_URL,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response", response.toString());
                        try {
                            String breakfastMenu = response.get("breakfast").toString();
                            String lunchMenu = response.get("lunch").toString();
                            String dinnerMenu = response.get("dinner").toString();
                            Log.e("breakfastMenu ", "Breakfast menu is " + breakfastMenu);
                            Log.e("lunchMenu ", "lunch menu is " + lunchMenu);
                            Log.e("dinnerMenu ", "dinner menu is " + dinnerMenu);
                            List<String> breakfastMenuList = Arrays.asList(breakfastMenu.split(","));
                            List<String> lunchMenuList = Arrays.asList(lunchMenu.split(","));
                            List<String> dinnerMenuList = Arrays.asList(dinnerMenu.split(","));
                            messMenu.put(BREAKFAST, breakfastMenuList);
                            messMenu.put(LUNCH, lunchMenuList);
                            messMenu.put(DINNER, dinnerMenuList);

                            for (int i = 0; i <= messMenuListView.getLastVisiblePosition() - messMenuListView.getFirstVisiblePosition(); i++) {
                                System.out.println("last visible is " + messMenuListView.getLastVisiblePosition());
                                System.out.println("first visible is " + messMenuListView.getFirstVisiblePosition());
                                Log.e("ERROR", "last visible is " + messMenuListView.getLastVisiblePosition());
                                Log.e("ERROR", "first visible is " + messMenuListView.getFirstVisiblePosition());

                                View currView = messMenuListView.getChildAt(i);
                                TextView viewContainingMenuValue = currView.findViewById(R.id.textView2);
                                List<String> currentMenu = messMenu.get(listOfCards[i]);
                                Log.e("abc", "current menu is " + currentMenu.toString());
                                String currentMenuString = messMenuUtils.getListAsString(currentMenu);
                                viewContainingMenuValue.setText(currentMenuString);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response error", error.toString());
                    }
                }
        );

        requestQueue.add(objectRequest);

        return messMenu;
    }
}
