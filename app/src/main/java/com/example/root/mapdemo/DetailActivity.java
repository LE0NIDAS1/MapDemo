package com.example.root.mapdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.root.mapdemo.R;
import com.example.root.mapdemo.entity.BookingModel;
import com.example.root.mapdemo.entity.Extra;
import com.example.root.mapdemo.entity.Model;
import com.example.root.mapdemo.entity.Office;
import com.example.root.mapdemo.entity.PromotionCode;
import com.example.root.mapdemo.entity.Reservation;
import com.example.root.mapdemo.requests.ExtraRequest;
import com.example.root.mapdemo.requests.PayPalRequest;
import com.example.root.mapdemo.requests.PromocionRequest;
import com.example.root.mapdemo.requests.SearchRequest;
import com.example.root.mapdemo.utils.HttpsTrustManager;
import com.example.root.mapdemo.utils.TransitionAdapter;
import com.example.root.mapdemo.utils.VolleySingleton;
import com.google.android.gms.analytics.ExceptionParser;
import com.google.android.gms.drive.internal.StringListResponse;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.example.root.mapdemo.R.id.BValidate;
import static com.example.root.mapdemo.R.id.end;
import static com.example.root.mapdemo.R.id.standard;
import static com.example.root.mapdemo.R.id.textTotal;
import static java.lang.Thread.sleep;

/**
 * Created by megha on 15-03-10.
 */
public class DetailActivity extends Activity implements View.OnClickListener {

    public static final String EXTRA_PARAM_ID = "place_id";
    public static final String NAV_BAR_VIEW_NAME = Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME;
    private ListView mList;
    private NetworkImageView mImageView;
    private TextView mTitle;
    private LinearLayout mTitleHolder;
    private Palette mPalette;
    private ImageButton mAddButton;
    private Animatable mAnimatable;
    private LinearLayout mRevealView;
    private EditText mEditTextTodo;
    private boolean isEditTextVisible;
    private InputMethodManager mInputManager;
    private Model model;
    private TextView mPrice;
    private TextView total;
    private ArrayList<String> mTodoList;
    private ArrayAdapter mToDoAdapter;
    int defaultColorForRipple;
    private ArrayList<String> data = new ArrayList<String>();
    private ArrayList<String> prices = new ArrayList<String>();

    private static final String TAG = "paymentExample";
    BigDecimal monto_pago=new BigDecimal(1);

    List<Extra> lista;


    PayPalConfiguration m_configuration;
    String m_paypalId = "AdUaqgVlRq0R2Jb-Nvl34MFfDS7w5FaxOHK84ATPy5QTYgcN7ALGZKgF7-Fg1eJ7mAmZEkPlxVY9nhJ6";
    int m_paypalRequestCode = 999;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        Bitmap mBitmapFile = (File) getIntent().getSerializableExtra("Image");
        String name = getIntent().getStringExtra("Name");
//        Bitmap bitmap = getIntent().getParcelableExtra("bitmap");
        String price = getIntent().getStringExtra("Price");
        String urlImage = getIntent().getStringExtra("urlImage");

        String beginDate = getIntent().getStringExtra("BeginDate");
        String endDate = getIntent().getStringExtra("EndDate");
        String officeId = getIntent().getStringExtra("OfficeId1");
        String officeId2 = getIntent().getStringExtra("OfficeId2");
        String idModel = getIntent().getStringExtra("idModel");
        String insurance = getIntent().getStringExtra("insurance");
        String fullTank = getIntent().getStringExtra("fullTank");



        model = new Model();//PlaceData.placeList().get(getIntent().getIntExtra(EXTRA_PARAM_ID, 0));
        model.setModelName(name);
        mList = (ListView) findViewById(R.id.list);
        mImageView = (NetworkImageView) findViewById(R.id.placeImage);

        mPrice = (TextView) findViewById(R.id.textPrice);
        total = (TextView) findViewById(R.id.total);

        mTitle = (TextView) findViewById(R.id.textView);
        mTitleHolder = (LinearLayout) findViewById(R.id.placeNameHolder);
        mAddButton = (ImageButton) findViewById(R.id.btn_add);
        mRevealView = (LinearLayout) findViewById(R.id.llEditTextHolder);
        mEditTextTodo = (EditText) findViewById(R.id.etTodo);

        mAddButton.setImageResource(R.drawable.icn_morph_reverse);
        mAddButton.setOnClickListener(this);

        mAddButton.setImageResource(R.drawable.icn_morp);
        mAnimatable = (Animatable) (mAddButton).getDrawable();
        mAnimatable.start();
        applyRippleColor(getResources().getColor(R.color.light_green), getResources().getColor(R.color.dark_green));


        defaultColorForRipple = getResources().getColor(R.color.cardview_dark_background);
        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mRevealView.setVisibility(View.INVISIBLE);
        isEditTextVisible = false;

        mTodoList = new ArrayList<>();
       // mToDoAdapter = new ArrayAdapter(this, R.layout.row_todo, mTodoList);
        mList.setAdapter(mToDoAdapter);

        //loadPlace();
        mTitle.setText(model.getModelName());
        mPrice.setText(price);

        String a = beginDate.substring(0,2);
        String b = beginDate.substring(3,5);
        String c = beginDate.substring(6,10);

        Calendar calendar1 = new GregorianCalendar(Integer.parseInt(c),Integer.parseInt(b),Integer.parseInt(a));
        java.sql.Date fechaInicio = new java.sql.Date(calendar1.getTimeInMillis());
        a = endDate.substring(0,2);
        b = endDate.substring(3,5);
        c = endDate.substring(6,10);
        Calendar calendar = new GregorianCalendar(Integer.parseInt(c),Integer.parseInt(b),Integer.parseInt(a));
        java.sql.Date fechaFin = new java.sql.Date(calendar.getTimeInMillis());

        long diferencia = ( fechaFin.getTime() - fechaInicio.getTime() )/(24 * 60 * 60 * 1000);

        String sTotal = price.replace("$","");
        Float fTotal = Float.parseFloat(sTotal) * diferencia;
        total.setText(String.valueOf(fTotal));



//        mImageView.setImageBitmap(bitmap);
        mImageView.setImageUrl(urlImage, VolleySingleton.getInstance().getImageLoader());
        windowTransition();


        Bitmap bitmap = BitmapFactory.decodeResource(ListActivity.getAppContext().getResources(), R.drawable.switzerland);

//        getPhoto();
       colorize(bitmap);

        //Validacion de Promocion
        final Button BValidate = (Button) findViewById(R.id.BValidate);
        BValidate.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           HttpsTrustManager.allowAllSSL();

                                           Response.Listener<String> responseListener = new Response.Listener<String>(){
                                               @Override
                                               public  void onResponse(String response){
                                                   JSONObject jsonResoponse = null;
                                                   try {
                                                       jsonResoponse = new JSONObject(response);
                                                       PromotionCode promotionCode = new PromotionCode();
                                                       promotionCode.setValid(jsonResoponse.getBoolean("valid"));
                                                       promotionCode.setValidationMessage(jsonResoponse.getString("validationMessage"));
                                                       if(promotionCode.getValid()) {
                                                           promotionCode.setId(jsonResoponse.getString("promotionId"));
                                                           promotionCode.setPercentage((float) jsonResoponse.getDouble("percentage"));
                                                           promotionCode.setValidationMessage(jsonResoponse.getString("validationMessage"));
                                                           promotionCode.setPromotionCode(jsonResoponse.getString("promotionCode"));
                                                           Float precio = Float.parseFloat(total.getText().toString().replace("$",""));
                                                           precio = precio - (precio * promotionCode.getPercentage() / 100);
                                                           total.setText(String.valueOf(precio));
                                                           Toast.makeText(
                                                                   getApplicationContext(),
                                                                   "promocion valida", Toast.LENGTH_LONG)
                                                                   .show();
                                                       }else{
                                                           Toast.makeText(
                                                                   getApplicationContext(),
                                                                   promotionCode.getValidationMessage(), Toast.LENGTH_LONG)
                                                                   .show();
                                                       }
                                                   } catch (JSONException e) {
                                                       e.printStackTrace();
                                                   }
                                               };
                                           };
                                           PromotionCode promotionCode = new PromotionCode();
                                           String idModel = getIntent().getStringExtra("idModel");
                                           String beginDate = getIntent().getStringExtra("BeginDate");
                                           String origin = getIntent().getStringExtra("OfficeId1");
                                           String detiny = getIntent().getStringExtra("OfficeId2");

                                           promotionCode.setPromotionCode(mEditTextTodo.getText().toString());
                                           promotionCode.setModelId(idModel);
                                           promotionCode.setOrigin(origin);
                                           promotionCode.setDestiny(detiny);
                                           promotionCode.setOriginDate(beginDate);


                                           PromocionRequest promocionRequest = new PromocionRequest(promotionCode, responseListener);
                                           RequestQueue queue = Volley.newRequestQueue(DetailActivity.this);
                                           queue.add(promocionRequest);

                                       }
        });

//        generateListContent();

        HttpsTrustManager.allowAllSSL();
        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public  void onResponse(String response){
                JSONObject jsonResoponse = null;
                lista = new ArrayList<>();

                try {
                    jsonResoponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResoponse.getJSONArray("extras");
                    int count = 0;
                    while(count < jsonArray.length()) {
                        JSONObject childJSONObject = jsonArray.getJSONObject(count);
                        Extra extra = new Extra();
                        extra.setId(childJSONObject.getInt("id"));
                        extra.setName(childJSONObject.getString("name"));
                        extra.setPrice(childJSONObject.getLong("price"));
                        data.add(extra.getName());
                        prices.add("$"+extra.getPrice());
                        lista.add(extra);
                        count++;
                    }

                    mList.setAdapter(new MyListAdaper(getApplicationContext(), R.layout.list_extra, data, prices));
                    mList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            Toast.makeText(DetailActivity.this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };
        };
            //ingresar datos precargado
            data.add("Seguro");
            data.add("Devolver con Tanque lleno");
            prices.add(insurance);
            prices.add(fullTank);
            mList.setAdapter(new MyListAdaper(getApplicationContext(), R.layout.list_extra, data, prices));

            mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                                 Toast.makeText(DetailActivity.this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
                                             }
                                         });

            //


        ExtraRequest extraRequest = new ExtraRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(DetailActivity.this);
        queue.add(extraRequest);
    }

//    private void loadPlace() {
//        mTitle.setText(mPlace.getModelName());
//        mImageView.setImageResource(R.drawable.audi);
//    }

    private void windowTransition() {
        getWindow().setEnterTransition(makeEnterTransition());
        getWindow().getEnterTransition().addListener(new TransitionAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                mAddButton.animate().alpha(1.0f);
                getWindow().getEnterTransition().removeListener(this);
            }
        });
    }

    public static Transition makeEnterTransition() {
        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        return fade;
    }

    private void addToDo(String todo) {
        mTodoList.add(todo);
    }

//    private void getPhoto() {
//        Bitmap photo = BitmapFactory.decodeResource(getResources(), );
//        colorize(photo);
//    }

    private void colorize(Bitmap photo) {
        mPalette = Palette.generate(photo);
        applyPalette();
    }

    private void applyPalette() {
        getWindow().setBackgroundDrawable(new ColorDrawable(mPalette.getDarkMutedColor(defaultColorForRipple)));
        mTitleHolder.setBackgroundColor(mPalette.getMutedColor(defaultColorForRipple));
        applyRippleColor(mPalette.getVibrantColor(defaultColorForRipple),
                mPalette.getDarkVibrantColor(defaultColorForRipple));
        mRevealView.setBackgroundColor(mPalette.getLightVibrantColor(defaultColorForRipple));
    }

    private void applyRippleColor(int bgColor, int tintColor) {
        colorRipple(mAddButton, bgColor, tintColor);
    }

    private void colorRipple(ImageButton id, int bgColor, int tintColor) {
        View buttonView = id;
        RippleDrawable ripple = (RippleDrawable) buttonView.getBackground();
        GradientDrawable rippleBackground = (GradientDrawable) ripple.getDrawable(0);
        rippleBackground.setColor(bgColor);
        ripple.setColor(ColorStateList.valueOf(tintColor));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                if (!isEditTextVisible) {
                    revealEditText(mRevealView);
                    mEditTextTodo.requestFocus();
                    mInputManager.showSoftInput(mEditTextTodo, InputMethodManager.SHOW_IMPLICIT);
                    mAddButton.setImageResource(R.drawable.icn_morp);
                    mAnimatable = (Animatable) (mAddButton).getDrawable();
                    mAnimatable.start();
                    applyRippleColor(getResources().getColor(R.color.light_green), getResources().getColor(R.color.dark_green));
                } else {
                    addToDo(mEditTextTodo.getText().toString());
//                    mToDoAdapter.notifyDataSetChanged();

                    //paypal
                    m_configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                            .clientId(m_paypalId);
                    Intent m_service = new Intent(this, PayPalService.class);
                    m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,m_configuration);
                    startService(m_service);
                    String value = mEditTextTodo.getText().toString();
//                    String aux = mPrice.getText().toString().replace("$", "");
                    String aux = total.getText().toString();
                    monto_pago = new BigDecimal(aux);
//                    int size = prices.size();
//                    for(int x = 0; x < prices.size(); x++) {
//                        aux = prices.get(x).replace("$","");
//                        monto_pago = monto_pago.add(new BigDecimal(aux));
//                    }


                    PayPalPayment payment = new PayPalPayment(monto_pago,"USD","Test payment with paypal"
                            , PayPalPayment.PAYMENT_INTENT_SALE);
                    Intent intent = new Intent(this, PaymentActivity.class);
                    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,m_configuration);
                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);
                    startActivityForResult(intent,m_paypalRequestCode);

//                    mInputManager.hideSoftInputFromWindow(mEditTextTodo.getWindowToken(), 0);
//                    hideEditText(mRevealView);
//                    mAddButton.setImageResource(R.drawable.icn_morph_reverse);
//                    mAnimatable = (Animatable) (mAddButton).getDrawable();
//                    mAnimatable.start();
//                    applyRippleColor(mPalette.getVibrantColor(defaultColorForRipple),
//                            mPalette.getDarkVibrantColor(defaultColorForRipple));
                }
        }
    }

    private void revealEditText(LinearLayout view) {
        int cx = view.getRight() - 30;
        int cy = view.getBottom() - 60;
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        view.setVisibility(View.VISIBLE);
        isEditTextVisible = true;
        anim.start();
    }

    private void hideEditText(final LinearLayout view) {
        int cx = view.getRight() - 30;
        int cy = view.getBottom() - 60;
        int initialRadius = view.getWidth();
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });
        isEditTextVisible = false;
        anim.start();
    }

    @Override
    public void onBackPressed() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(100);
        mAddButton.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mAddButton.setVisibility(View.GONE);
                finishAfterTransition();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private class MyListAdaper extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;
        private List<String> mPrices;
        private MyListAdaper(Context context, int resource, List<String> objects, List<String> prices) {
            super(context, resource, objects);
            mObjects = objects;
            mPrices = prices;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.price = (TextView) convertView.findViewById(R.id.list_price_text1);
                viewHolder.button = (Button) convertView.findViewById(R.id.compatSwitchExtra);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                    CompoundButton bSwitch = (CompoundButton)v;
                    if(bSwitch.isChecked()){
                        Float fTotal = Float.parseFloat(total.getText().toString()) + Float.parseFloat(mPrices.get(position).replace("$",""));
                        total.setText(String.valueOf(fTotal));

                    }else{
                        Float fTotal = Float.parseFloat(total.getText().toString()) - Float.parseFloat(mPrices.get(position).replace("$",""));
                        total.setText(String.valueOf(fTotal));
                    }
                }
            });
            mainViewholder.title.setText(mObjects.get(position));
            mainViewholder.price.setText(mPrices.get(position));

            return convertView;
        }
    }

    private void generateListContent() {
        for(int i = 0; i < 10; i++) {
            data.add("This is row number " + i);
        }
    }

    public class ViewHolder {

        ImageView thumbnail;
        TextView title;
        Button button;
        TextView price;
    }

    void pay(View view){
        PayPalPayment payment = new PayPalPayment(monto_pago,"USD","Test payment with paypal"
                , PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,m_configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);
        startActivityForResult(intent,m_paypalRequestCode);
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == m_paypalRequestCode){
            if(resultCode == Activity.RESULT_OK){
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if(confirm != null){
                    String state = confirm.getProofOfPayment().getState();
                    if(state.equals("approved")){

                        try {
                            Log.i(TAG, confirm.toJSONObject().toString(4));
                            Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));

                            String beginDate = getIntent().getStringExtra("BeginDate");
                            String endDate = getIntent().getStringExtra("EndDate");
                            String officeId = getIntent().getStringExtra("OfficeId1");
                            String officeId2 = getIntent().getStringExtra("OfficeId2");
                            String idModel = getIntent().getStringExtra("idModel");

                            Reservation reservation = new Reservation();
                            BookingModel bookingModel = new BookingModel();
                            bookingModel.setIdModel(Integer.parseInt(idModel));
                            bookingModel.setStartDate(beginDate);
                            bookingModel.setEndDate(endDate);
                            try {
                                bookingModel.setOriginBranchOfficeId(Integer.parseInt(officeId));
                                bookingModel.setEndBranchOfficeId(Integer.parseInt(officeId2));
                            }catch (Exception e){
                                Toast.makeText(
                                        getApplicationContext(),
                                        "nada", Toast.LENGTH_LONG)
                                        .show();
                            }

                            JSONObject jsonResoponse = confirm.toJSONObject();
                            JSONObject childJSONObject = jsonResoponse.getJSONObject("response");
                            reservation.setToken(childJSONObject.getString("id"));
                            childJSONObject = confirm.getPayment().toJSONObject();
                            reservation.setOrderTotal(childJSONObject.getString("amount"));
                            reservation.setPayerId("info.rentuy-buyer@gmail.com");
                            reservation.setPromotionCode("");
                            reservation.setItemTotal(String.valueOf(prices.size()));

                            SharedPreferences sp=getSharedPreferences("key", Context.MODE_PRIVATE);
                            String sId = sp.getString("id", "");

                            reservation.setClientId(Integer.parseInt(sId));

                            HttpsTrustManager.allowAllSSL();

                            Response.Listener<String> responseListener = new Response.Listener<String>(){
                                @Override
                                public  void onResponse(String response){
                                    JSONArray jsonResoponse = null;
                                    try {
                                        jsonResoponse = new JSONArray(response);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                };
                            };

                            PayPalRequest payPalRequest = new PayPalRequest(reservation, bookingModel, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(DetailActivity.this);
                            queue.add(payPalRequest);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(
                                    getApplicationContext(), "No se pudo realizar el pago", Toast.LENGTH_LONG)
                                    .show();

                        }
//                        m_response.setText("payment is approved");

//                        Boolean paso = new PagoDoRequest(this).doInBackground(monto_pago.toString(),comentario.getText().toString());
//                        String[] de;

//                        de = new PagoDoRequest(this).retornar(comentario.getText().toString(),monto_pago.toString(),value.getText().toString() );
//                        if(paso) {
//                            m_response.setText(de[0] + " " + de[1] + " " + de[2]);
//                        }else{
//                            m_response.setText("no llego");
//                        }
                        displayResultText("Confirmación de pago recibido por PayPal");
                        Intent intent = new Intent(DetailActivity.this, SearchActivity.class);
                        DetailActivity.this.startActivity(intent);
                    }
                    else{
//                        m_response.setText("error in payment");
                    }
                }
                else{

//                    m_response.setText("confirmation is null");
                }
            }
        }
    }
    protected void displayResultText(String result) {
//        ((TextView)findViewById(R.id.textPrice)).setText("Result : " + result);
        Toast.makeText(
                getApplicationContext(),
                result, Toast.LENGTH_LONG)
                .show();
    }
}
