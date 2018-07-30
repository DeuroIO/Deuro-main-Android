package com.deuro.android.Fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deuro.android.Models.CenterZoomLayoutManager;
import com.deuro.android.Models.HomeModel;
import com.deuro.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragement_test extends Fragment implements ScreenShotable {


    private Context mContext;
    private ArrayList<HomeModel> homeModelArrayList;
    private ListView listView;
    private View firstChildInList;

    public Home_Fragement_test() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.home_fragement_test, container, false);
        mContext = getActivity();
        homeModelArrayList = new ArrayList<>();
        final HomeListView homeRecyclerView = new HomeListView(mContext, homeModelArrayList);
        listView=view.findViewById(R.id.listView);
        listView.setAdapter(homeRecyclerView);
        JSONArray array = loadJSONFromAsset(mContext);
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject jsonObject = new JSONObject(String.valueOf(array.get(i)));
                HomeModel homeModel = new HomeModel(jsonObject);
                homeModelArrayList.add(homeModel);
                homeRecyclerView.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        return view;
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

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    private class HomeListView extends BaseAdapter{
        Context mContext;
        ArrayList<HomeModel> adapterArrayList;
        public RelativeLayout image_RL;
        public RelativeLayout image_RL1;
        public TextView textTitle_TV, textDecription_TV, textTitle_TV1;
        public HomeListView(Context mContext, ArrayList<HomeModel> homeModelArrayList) {
            this.mContext = mContext;
            this.adapterArrayList = homeModelArrayList;
        }

        @Override
        public int getCount() {
            return homeModelArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return homeModelArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.raw_item, parent, false);
            textTitle_TV = itemView.findViewById(R.id.textTitle_TV);
            image_RL = itemView.findViewById(R.id.image_RL);
            textDecription_TV = itemView.findViewById(R.id.textDecription_TV);
            image_RL1 = itemView.findViewById(R.id.image_RL1);
            textTitle_TV1 = itemView.findViewById(R.id.textTitle_TV1);
            textTitle_TV.setText(adapterArrayList.get(position).getTitle());
            textTitle_TV1.setText(adapterArrayList.get(position).getTitle());
            textDecription_TV.setText(adapterArrayList.get(position).getDecription());
            image_RL.setBackground(GetImage(mContext, adapterArrayList.get(position).getImage()));
            image_RL1.setBackground(GetImage(mContext, adapterArrayList.get(position).getImage()));
            return itemView;
        }
    }
}
