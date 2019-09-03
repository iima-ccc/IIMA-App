package com.ccc.iima_app.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ccc.iima_app.utils.MessMenuDAO;
import com.ccc.iima_app.utils.MessMenuUtils;
import com.ccc.navigationdrawer.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ccc.iima_app.utils.MessMenuConstants.BREAKFAST;
import static com.ccc.iima_app.utils.MessMenuConstants.DINNER;
import static com.ccc.iima_app.utils.MessMenuConstants.LUNCH;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private OnFragmentInteractionListener mListener;
    String[] listOfCards = {BREAKFAST, LUNCH, DINNER};
    WebView myWebView;
    MessMenuDAO messMenuDAO;
    MessMenuUtils messMenuUtils = new MessMenuUtils();

    ListView simpleListView;

    public MessFragment() {
        // Required empty public constructor
    }

    public static MessFragment newInstance(String param1, String param2) {
        MessFragment fragment = new MessFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rView = (View) inflater.inflate(R.layout.fragment_mess, container, false);
        createMenuView(rView);
        return rView;
    }

    private void createMenuView(View rView) {

        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        simpleListView = (ListView) rView.findViewById(R.id.simpleListView);


        messMenuDAO = new MessMenuDAO();
        Map<String, List<String>> messMenu = new HashMap<>();
        List<String> listOfValues = Arrays.asList(new String[]{"Poha", "Bread Butter", "Sandwich", "Pani Puri", "Idli fry", "Burger"});//fruit names array
        messMenu.put(BREAKFAST, listOfValues);
        messMenu.put(LUNCH, listOfValues);
        messMenu.put(DINNER, listOfValues);

        for (int i = 0; i < listOfCards.length; i++) {
            HashMap<String, String> hashMap = new HashMap<>(); //create a hashmap to store the data in key value pair
            hashMap.put("name", listOfCards[i]);
            List<String> currentMenu = messMenu.get(listOfCards[i]);
            String currentMenuString = messMenuUtils.getListAsString(currentMenu);
            hashMap.put("values", currentMenuString);
            arrayList.add(hashMap); //add the hashmap into arrayList
        }
        String[] from = {"name", "values"}; //string array
        int[] to = {R.id.textView, R.id.textView2}; //int array of views id's
        SimpleAdapter simpleAdapter = new SimpleAdapter(this.getContext(), arrayList, R.layout.list_view_items, from, to); //Create object and set the parameters for simpleAdapter

        simpleListView.setAdapter(simpleAdapter);//sets the adapter for listView

        messMenuDAO.getMenu(getContext(), Calendar.getInstance().getTime(), simpleListView);
        //perform listView item click event
        simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(view.getContext().getApplicationContext(), listOfCards[i], Toast.LENGTH_LONG).show(); //show the selected image in toast according to position
            }
        });

        Button dateButton = (Button) rView.findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("yes", "no");
                showPopup(getActivity(), simpleListView, view);
            }
        });

    }


    public void showPopup(Activity fragmentActivity, final ListView simpleListView, final View buttonView) {

        // Inflate the popup_layout.xml
        LayoutInflater layoutInflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.calender_layout, null, false);
        // Creating the PopupWindow
        final PopupWindow popupWindow = new PopupWindow(
                layout, 900, 900);

        popupWindow.setContentView(layout);
//        popupWindow.setHeight(500);
        popupWindow.setOutsideTouchable(false);

        CalendarView cv = (CalendarView) layout.findViewById(R.id.calendarView);

        cv.setDate(Calendar.getInstance().getTime().getTime(), true, true);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                messMenuDAO = new MessMenuDAO();
                try {
                    Date chosenDate = null;
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    int actualMonth = month + 1;
                    chosenDate = formatter.parse(dayOfMonth + "/" + actualMonth + "/" + year);

                    messMenuDAO.getMenu(getContext(), chosenDate, simpleListView);

                    Button dateButton = (Button) buttonView.findViewById(R.id.dateButton);
                    Date today = Calendar.getInstance().getTime();

                    String chosenDateFormattedText = formatter.format(chosenDate);
                    String todayDateFormattedText = formatter.format(today);
                    String dateText = todayDateFormattedText.equals(chosenDateFormattedText) ? "Today" : chosenDateFormattedText;
                    dateButton.setText(dateText);
                    Log.d("date selected", "date selected " + year + " " + month + " " + dayOfMonth);
                } catch (ParseException e) {
                    e.printStackTrace();
                } finally {
                    popupWindow.dismiss();
                }

            }
        });
        popupWindow.showAtLocation(layout, Gravity.TOP, 5, 170);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}


