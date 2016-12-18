package com.example.root.mapdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.root.mapdemo.requests.SearchRequest;
import com.example.root.mapdemo.utils.DatePickerFragment;
import com.example.root.mapdemo.utils.DatePickerFragment2;
import com.example.root.mapdemo.utils.HttpsTrustManager;
import com.example.root.mapdemo.entity.Model;
import com.example.root.mapdemo.utils.PlaceholderFragment;
import com.example.root.mapdemo.utils.RVAdapter;
import com.example.root.mapdemo.entity.SearchFilter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import static com.example.root.mapdemo.R.layout.activity_search;

public class SearchActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static Context mContext;

    protected static final int REQUEST_CODE = 10;


    private List<Model> lista;
    private RecyclerView rv;
    private DrawerLayout drawerLayout;
    private String drawerTitle;
    private RVAdapter adapter;

    TextView textView;

    private ProgressDialog progressDialog;


    public static Context getAppContext() {
        return mContext;
    }

//    RVAdapter.OnItemClickListener onItemClickListener = new RVAdapter.OnItemClickListener() {
//        @Override
//        public void onItemClick(View v, int position) {
//            Intent transitionIntent = new Intent(SearchActivity.getAppContext(), DetailActivity.class);
//            transitionIntent.putExtra(DetailActivity.EXTRA_PARAM_ID, position);
//            ImageView placeImage = (ImageView) v.findViewById(R.id.car_photo);
//            TextView placeNameHolder = (TextView) v.findViewById(R.id.model_name);
//            TextView priceModel = (TextView) v.findViewById(R.id.car_base_price);
//
//            View navigationBar = findViewById(android.R.id.navigationBarBackground);
//            View statusBar = findViewById(android.R.id.statusBarBackground);
//
//            Pair<View, String> imagePair = Pair.create((View) placeImage, "tImage");
//            Pair<View, String> holderPair = Pair.create((View) placeNameHolder, "tNameHolder");
//
////            Pair<View, String> navPair = Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
////            Pair<View, String> statusPair = Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);
//            //Pair<View, String> toolbarPair = Pair.create((View)toolbar, "tActionBar");
//
//            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SearchActivity.this, imagePair, holderPair/*, navPair, statusPair*/);
//            transitionIntent.putExtra("Name", placeNameHolder.getText());
//            transitionIntent.putExtra("Price", priceModel.getText());
//
//            BitmapDrawable drawable = (BitmapDrawable) placeImage.getDrawable();
//            Bitmap bitmap = drawable.getBitmap();
//
//            transitionIntent.putExtra("bitmap", bitmap);
//            transitionIntent.putExtra("Image", ((BitmapDrawable)placeImage.getDrawable()).getBitmap());
//
//            ActivityCompat.startActivity(SearchActivity.this, transitionIntent, options.toBundle());
//        }
//    };


    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    //Aparece o desaperece lugar de devolucion
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final TextView sucursal2 = (TextView) findViewById(R.id.textSucursal2);
        boolean i = buttonView.isChecked();
        if (i == false) {
//            sucursal2.setVisibility(View.VISIBLE);
            sucursal2.animate()
                    .translationX(0)
                    .alpha(1.0f)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            sucursal2.setVisibility(View.VISIBLE);
                        }
                    });


        } else if (i == true) {
//            sucursal2.setVisibility(View.GONE);
            sucursal2.animate()
                    .translationX(sucursal2.getWidth())
                    .alpha(0.0f)
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            sucursal2.setVisibility(View.GONE);
                        }
                    });

        }

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Marcar item presionado
                        menuItem.setChecked(true);
                        // Crear nuevo fragmento
                        String title = menuItem.getTitle().toString();
                        selectItem(title);
                        return true;
                    }
                }
        );
    }

    private void selectItem(String title) {
        // Enviar título como arguemento del fragmento
        Bundle args = new Bundle();
        args.putString(PlaceholderFragment.ARG_SECTION_TITLE, title);

//        Toast.makeText(this, "ParametrosActivity devolvió: " + "hola", Toast.LENGTH_LONG).show();

        drawerLayout.closeDrawers(); // Cerrar drawer

        setTitle(title); // Setear título actual

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(View v) {
        android.support.v4.app.DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showDatePickerDialog2(View v) {
        android.support.v4.app.DialogFragment newFragment = new DatePickerFragment2();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void getSucursal(View v) {
        progressDialog = new ProgressDialog(SearchActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        Intent intent = new Intent(SearchActivity.this, MapsActivity.class);
        intent.putExtra("sucursal", "1");
        SearchActivity.this.startActivityForResult(intent, REQUEST_CODE);
    }

    public void getSucursal2(View v) {
        progressDialog = new ProgressDialog(SearchActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        Intent intent = new Intent(SearchActivity.this, MapsActivity.class);
        intent.putExtra("sucursal", "2");
        SearchActivity.this.startActivityForResult(intent, REQUEST_CODE);
    }
    public void clickLogin(View v){
        Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
        SearchActivity.this.startActivityForResult(intent, REQUEST_CODE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // agarramos el valor devuelto por la otra actividad
            progressDialog.dismiss();
            try {
                String officeName = data.getStringExtra("officeName");
                String officeId = data.getStringExtra("officeId");
                String s = data.getStringExtra("suc");

                // enseñamos al usuario el resultado
                if (s.equals("1")) {
                    final TextView oName = (TextView) findViewById(R.id.textSucursal);
                    final TextView oId = (TextView) findViewById(R.id.hiddenSucursal);
                    oName.setText(officeName);
                    oId.setText(officeId);
                } else if (s.equals("2")) {
                    final TextView oName = (TextView) findViewById(R.id.textSucursal2);
                    final TextView oId = (TextView) findViewById(R.id.hiddenSucursal2);
                    oName.setText(officeName);
                    oId.setText(officeId);
                }
            }catch (Exception e){

            }

            //Toast.makeText(this, "ParametrosActivity devolvió: " + result, Toast.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_search);


        SwitchCompat switchCompat = (SwitchCompat) findViewById(R.id.compatSwitch);
        switchCompat.setOnCheckedChangeListener(this);


        //final EditText searchText = (EditText) findViewById(R.id.searchText);
        final Button BSearch = (Button) findViewById(R.id.BSearch);
        final TextView beginDateTV= (TextView) findViewById(R.id.textview1);
        final TextView endDateTV= (TextView) findViewById(R.id.textview2);
        final TextView hiddenIdTV = (TextView) findViewById(R.id.hiddenSucursal);
        final TextView hiddenIdTV2 = (TextView) findViewById(R.id.hiddenSucursal2);


        rv = (RecyclerView) findViewById(R.id.rv);

        SharedPreferences sp=getSharedPreferences("key", Context.MODE_PRIVATE);
        String sName = sp.getString("Name", "");

        if(sName != ""){
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);
            String sEmail = sp.getString("Email", "");

            final TextView navName = (TextView) headerView.findViewById(R.id.username);
            final TextView navEmail = (TextView) headerView.findViewById(R.id.email);

            navName.setText(sName);
            navEmail.setText(sEmail);
        }

        setToolbar(); // Setear Toolbar como action bar

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        drawerTitle = "";
        if (savedInstanceState == null) {
            selectItem(drawerTitle);
        };

//        Model m = new Model();
//        m.setModelName("Camaro");
//        m.setImages("http://www.deautomoviles.com.ar/articulos/modelos/img/chevrolet-camaro-2010.jpg");
//        m.setBassPrice(1);
//        lista = new ArrayList<>();
//
//        lista.add(m);
//        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
//        rv.setLayoutManager(llm);
//        rv.setHasFixedSize(true);
//        adapter = new RVAdapter(SearchActivity.mContext);
//        adapter = new RVAdapter(lista);
//        rv.setAdapter(adapter);
//        adapter.setOnItemClickListener(onItemClickListener);



        BSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v) {
                final String beginDate = beginDateTV.getText().toString();
                final String endDate = endDateTV.getText().toString();
                final String officeId = hiddenIdTV.getText().toString();
                final String officeId2 = hiddenIdTV2.getText().toString();

                Intent intent = new Intent(SearchActivity.this, ListActivity.class);
                intent.putExtra("BeginDate", beginDate);
                intent.putExtra("EndDate", endDate);
                intent.putExtra("OfficeId1", officeId);
                intent.putExtra("OfficeId2", officeId2);

                SearchActivity.this.startActivity(intent);


                HttpsTrustManager.allowAllSSL();

//                Response.Listener<String> responseListener = new Response.Listener<String>(){
//                    @Override
//                    public  void onResponse(String response){
//                        JSONArray jsonResoponse = null;
//                        try {
//
//                            jsonResoponse = new JSONArray(response);
//                            int count = 0;
//                            lista = new ArrayList<>();
//                            while(count < jsonResoponse.length()) {
//                                JSONObject childJSONObject = jsonResoponse.getJSONObject(count);
//
//                                Model m = new Model();
//                                m.setId(childJSONObject.getInt("id"));
//                                m.setModelName(childJSONObject.getString("name"));
//                                JSONObject fuelJson = childJSONObject.getJSONObject("fuel");
//                                m.setFuelType(fuelJson.getString("fuelType"));
//                                m.setFuelPrice((float) fuelJson.getDouble("fuelPrice"));
//                                JSONObject categoryJson = childJSONObject.getJSONObject("category");
//                                m.setCategoria(categoryJson.getString("name"));
//                                m.setBassPrice((float) categoryJson.getDouble("basePrice"));
//                                m.setYear(childJSONObject.getInt("year"));
//                                m.setPassangers(childJSONObject.getInt("passangers"));
//                                m.setLuggage(childJSONObject.getInt("luggage"));
//                                m.setCylinders(childJSONObject.getInt("cylinders"));
//                                m.setAirConditioner(childJSONObject.getBoolean("airConditioner"));
//                                m.setTransmission(childJSONObject.getString("transmission"));
//                                m.setInsurance((float) childJSONObject.getDouble("insurance"));
//                                m.setFullTank(childJSONObject.getInt("fullTank"));
//                                JSONArray imageJson = childJSONObject.getJSONArray("images");
//                                if (! imageJson.isNull(0)) {
//                                    JSONObject imageSONObject = imageJson.getJSONObject(0);
//                                    m.setImages(imageSONObject.getString("fileLocation").replace(" ", "%20"));
//                                }
//                                lista.add(m);
//
//                                count++;
//                            }
//
//
//                            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
//                            rv.setLayoutManager(llm);
//                            rv.setHasFixedSize(true);
//                            adapter = new RVAdapter(SearchActivity.mContext);
//                            adapter = new RVAdapter(lista);
//                            rv.setAdapter(adapter);
//                            adapter.setOnItemClickListener(onItemClickListener);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    };
//                };
//                JSONArray model = new JSONArray();
//
//                JSONObject car = new JSONObject();
//
////                try {
////                    car.put("Model", search);
////                    car.put("GPS", true);
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
//
//                SearchFilter searchf = new SearchFilter();
//                searchf.setPassangers(0);
//                searchf.setAirConditioner(true);
//                searchf.setBeginDate(beginDate);
//                searchf.setEndDate(endDate);
//                searchf.setLuggage(0);
//                searchf.setOfficeOriginId(Integer.parseInt(officeId));
//                if(officeId2 != null && officeId2 != "") {
//                    searchf.setOfficeEndId(Integer.parseInt(officeId2));
//                }else{
//                    searchf.setOfficeEndId(Integer.parseInt(officeId));
//                }
//                //gson.toJson(searchf);
//
//                SearchRequest searchRequest = new SearchRequest(searchf, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(SearchActivity.getAppContext());
//                queue.add(searchRequest);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main, menu);
//
//        final MenuItem searchItem = menu.findItem(R.id.action_search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        //permite modificar el hint que el EditText muestra por defecto
//        searchView.setQueryHint(getText(R.string.action_search));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(SearchActivity.this, "enter", Toast.LENGTH_SHORT).show();
//                //se oculta el EditText
//                searchView.setQuery("", false);
//                searchView.setIconified(true);
//                return true;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                textView.setText(newText);
//                return true;
//            }
//        });
//
//
//        return super.onCreateOptionsMenu(menu);
//    }
}