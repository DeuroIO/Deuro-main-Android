package com.deuro.android.Fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deuro.android.Models.HomeModel;
import com.deuro.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import yalantis.com.sidemenu.interfaces.ScreenShotable;


public class Home_Fragment extends Fragment implements ScreenShotable {
    private RecyclerView recyclerView;
    private Context mContext;
    ArrayList<HomeModel> arrayList;
    ArrayList<HomeModel> homeModelArrayList;
    private int scrollPosition;

    public Home_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewList);
        mContext = getActivity();
        arrayList = new ArrayList<>();
        homeModelArrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());


        return view;
    }





    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }


    public JSONArray loadJSONFromAsset(Context context) {
        JSONArray jsonArray = null;
        try {
            InputStream is = context.getAssets().open("gallery.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            jsonArray = new JSONArray(json);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
