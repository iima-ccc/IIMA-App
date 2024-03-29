package com.ccc.iima_app.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ccc.navigationdrawer.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HDMSFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private ProgressDialog mProgress;
    private OnFragmentInteractionListener mListener;
    WebView myWebView;
    public HDMSFragment() {
        // Required empty public constructor
    }

    public static HDMSFragment newInstance(String param1, String param2) {
        HDMSFragment fragment = new HDMSFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mProgress = new ProgressDialog(getContext()).show(getContext(),"","Loading. Please Wait...",true);
        // Inflate the layout for this fragment
        View rView=(View) inflater.inflate(R.layout.fragment_hdms, container, false);
        myWebView = (WebView) rView.findViewById(R.id.webview);


        myWebView.loadUrl("http://hdms.iima.ac.in/");
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url) {
                if (mProgress != null)
                    mProgress.dismiss();
            }
        });
        //return inflater.inflate(R.layout.fragment_home, container, false);
        return rView;
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
