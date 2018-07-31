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

import com.deuro.android.Adapter.CustomAdapter;
import com.deuro.android.Models.HomeModel;
import com.deuro.android.R;
import com.example.library.FocusResizeScrollListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.interfaces.ScreenShotable;


public class Home_Fragment extends Fragment implements ScreenShotable {
    private RecyclerView recyclerView;
    private Context mContext;
    ArrayList<HomeModel> arrayList;
    ArrayList<HomeModel> homeModelArrayList;
    private int scrollPosition;
    private CustomAdapter customAdapter;

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
        createCustomAdapter(recyclerView, linearLayoutManager);

        return view;
    }



    private void createCustomAdapter(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        customAdapter = new CustomAdapter(getActivity(), (int) getResources().getDimension(R.dimen.custom_item_height));
        customAdapter.addItems(addItems());
        if (recyclerView != null) {
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(customAdapter);
            recyclerView.addOnScrollListener(new FocusResizeScrollListener<>(customAdapter, linearLayoutManager));
        }
    }
    public ArrayList<HomeModel> addItems()
    {
        JSONArray array = loadJSONFromAsset(mContext);
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject jsonObject = new JSONObject(String.valueOf(array.get(i)));
                HomeModel homeModel = new HomeModel(jsonObject);
                homeModel.setPosition(i);
                homeModelArrayList.add(homeModel);
                customAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return homeModelArrayList;
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

    public static Drawable GetImage(Context c, String ImageName) {
        ImageName = ImageName.replace(".png", "");
        return c.getResources().getDrawable(c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()));
    }

}
