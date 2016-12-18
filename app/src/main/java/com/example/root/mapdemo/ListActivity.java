package com.example.root.mapdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.root.mapdemo.entity.Model;
import com.example.root.mapdemo.entity.SearchFilter;
import com.example.root.mapdemo.requests.SearchRequest;
import com.example.root.mapdemo.utils.HttpsTrustManager;
import com.example.root.mapdemo.utils.RVAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.R.id.toolbar;

public class ListActivity extends AppCompatActivity {

    private static Context mContext;

    private android.widget.Toolbar toolbar;


    private RecyclerView rv;
    private RVAdapter adapter;

    private List<Model> lista;

    private StaggeredGridLayoutManager mStaggeredLayoutManager;


    public static Context getAppContext() {
        return mContext;
    }


    RVAdapter.OnItemClickListener onItemClickListener = new RVAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            Intent transitionIntent = new Intent(ListActivity.getAppContext(), DetailActivity.class);
            transitionIntent.putExtra(DetailActivity.EXTRA_PARAM_ID, position);
            NetworkImageView placeImage = (NetworkImageView) v.findViewById(R.id.car_photo);
            TextView urlImage = (TextView) v.findViewById(R.id.urlImage);


            LinearLayout placeNameHolder = (LinearLayout) v.findViewById(R.id.placeNameHolder);

            TextView NameHolder = (TextView) v.findViewById(R.id.model_name);
            TextView priceModel = (TextView) v.findViewById(R.id.car_base_price);

            View navigationBar = findViewById(android.R.id.navigationBarBackground);
            View statusBar = findViewById(android.R.id.statusBarBackground);

            Pair<View, String> imagePair = Pair.create((View) placeImage, "tImage");
            Pair<View, String> holderPair = Pair.create((View) NameHolder, "tNameHolder");

            Pair<View, String> navPair = Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
            Pair<View, String> statusPair = Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);
            Pair<View, String> toolbarPair = Pair.create((View)toolbar, "tActionBar");

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(ListActivity.this, imagePair, holderPair, navPair, statusPair);
            transitionIntent.putExtra("Name", NameHolder.getText());
            transitionIntent.putExtra("Price", priceModel.getText());
            transitionIntent.putExtra("urlImage", urlImage.getText());

//            BitmapDrawable drawable = (BitmapDrawable) placeImage.getDrawable();
//            Bitmap bitmap = drawable.getBitmap();
//
//            transitionIntent.putExtra("bitmap", bitmap);

            ActivityCompat.startActivity(ListActivity.this, transitionIntent, options.toBundle());
        }
    };

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        super.onBackPressed();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarList);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){
            Toast.makeText(ListActivity.this, "No se pudo cargar toolbar", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mContext = getApplicationContext();
        setToolbar();

        String beginDate = getIntent().getStringExtra("BeginDate");
        String endDate = getIntent().getStringExtra("EndDate");
        String officeId = getIntent().getStringExtra("OfficeId1");
        String officeId2 = getIntent().getStringExtra("OfficeId2");

        rv = (RecyclerView) findViewById(R.id.rv2);


        Model m = new Model();
        m.setModelName("Camaro");
        m.setImages("http://www.deautomoviles.com.ar/articulos/modelos/img/chevrolet-camaro-2010.jpg");
        m.setBassPrice(1);
        lista = new ArrayList<>();

        lista.add(m);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rv = (RecyclerView) findViewById(R.id.rv2);
        rv.setLayoutManager(mStaggeredLayoutManager);
        rv.setHasFixedSize(true);
        adapter = new RVAdapter(ListActivity.this);
        adapter = new RVAdapter(lista);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);



        HttpsTrustManager.allowAllSSL();

        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public  void onResponse(String response){
                JSONArray jsonResoponse = null;
                try {

                    jsonResoponse = new JSONArray(response);
                    int count = 0;
                    lista = new ArrayList<>();
                    while(count < jsonResoponse.length()) {
                        JSONObject childJSONObject = jsonResoponse.getJSONObject(count);

                        Model m = new Model();
                        m.setId(childJSONObject.getInt("id"));
                        m.setModelName(childJSONObject.getString("name"));
                        JSONObject fuelJson = childJSONObject.getJSONObject("fuel");
                        m.setFuelType(fuelJson.getString("fuelType"));
                        m.setFuelPrice((float) fuelJson.getDouble("fuelPrice"));
                        JSONObject categoryJson = childJSONObject.getJSONObject("category");
                        m.setCategoria(categoryJson.getString("name"));
                        m.setBassPrice((float) categoryJson.getDouble("basePrice"));
                        m.setYear(childJSONObject.getInt("year"));
                        m.setPassangers(childJSONObject.getInt("passangers"));
                        m.setLuggage(childJSONObject.getInt("luggage"));
                        m.setCylinders(childJSONObject.getInt("cylinders"));
                        m.setAirConditioner(childJSONObject.getBoolean("airConditioner"));
                        m.setTransmission(childJSONObject.getString("transmission"));
                        m.setInsurance((float) childJSONObject.getDouble("insurance"));
                        m.setFullTank(childJSONObject.getInt("fullTank"));
                        JSONArray imageJson = childJSONObject.getJSONArray("images");
                        if (! imageJson.isNull(0)) {
                            JSONObject imageSONObject = imageJson.getJSONObject(0);
                            m.setImages(imageSONObject.getString("fileLocation").replace(" ", "%20"));
                        }
                        lista.add(m);

                        count++;
                    }


//                    LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
//                    rv.setLayoutManager(llm);
//                    rv.setHasFixedSize(true);
//                    adapter = new RVAdapter(ListActivity.mContext);
//                    adapter = new RVAdapter(lista);
//                    rv.setAdapter(adapter);
//                    adapter.setOnItemClickListener(onItemClickListener);

                    mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    rv = (RecyclerView) findViewById(R.id.rv2);
                    rv.setLayoutManager(mStaggeredLayoutManager);
                    rv.setHasFixedSize(true);
                    adapter = new RVAdapter(ListActivity.this);
                    adapter = new RVAdapter(lista);
                    rv.setAdapter(adapter);
                    adapter.setOnItemClickListener(onItemClickListener);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
        };

        SearchFilter searchf = new SearchFilter();
        searchf.setPassangers(0);
        searchf.setAirConditioner(true);
        searchf.setBeginDate(beginDate);
        searchf.setEndDate(endDate);
        searchf.setLuggage(0);
        if(!officeId.equals("")) {
            searchf.setOfficeOriginId(Integer.parseInt(officeId));
        }else{
            Toast.makeText(ListActivity.this, "No se eligió sucursal", Toast.LENGTH_SHORT).show();
        }
            if(officeId2 != null && ! officeId2.equals("")) {
            searchf.setOfficeEndId(Integer.parseInt(officeId2));
        }else if(officeId.equals("")) {
                Toast.makeText(ListActivity.this, "No se eligió sucursal", Toast.LENGTH_SHORT).show();
            }else {

                searchf.setOfficeEndId(Integer.parseInt(officeId));
            }


        SearchRequest searchRequest = new SearchRequest(searchf, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ListActivity.getAppContext());
        queue.add(searchRequest);
    }
}
