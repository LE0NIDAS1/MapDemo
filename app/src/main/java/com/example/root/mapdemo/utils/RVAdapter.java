package com.example.root.mapdemo.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.StringRes;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.root.mapdemo.ListActivity;
import com.example.root.mapdemo.R;
import com.example.root.mapdemo.entity.Model;
import com.google.android.gms.location.places.Place;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.root.mapdemo.utils.Constant.URL;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ModelViewHolder> {

    Context mContext;
    OnItemClickListener mItemClickListener;

    public RVAdapter(Context context) {
        this.mContext = context;
    }


    public class ModelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        LinearLayout carNameHolder;
        CardView cv;
        TextView modelName;
        TextView carBasePrice;
        NetworkImageView carPhoto;
        TextView urlImage;
        TextView idModel;
        TextView insurance;
        TextView fullTank;

        public ModelViewHolder(View itemView){
            super(itemView);
            carNameHolder = (LinearLayout) itemView.findViewById(R.id.carNameHolder);
            cv = (CardView)itemView.findViewById(R.id.cv);
            modelName = (TextView)itemView.findViewById(R.id.model_name);
            carBasePrice = (TextView)itemView.findViewById(R.id.car_base_price);
            carPhoto = (NetworkImageView)itemView.findViewById(R.id.car_photo);
            urlImage = (TextView) itemView.findViewById(R.id.urlImage);
            idModel = (TextView) itemView.findViewById(R.id.model_id);
            insurance = (TextView) itemView.findViewById(R.id.insurance);
            fullTank = (TextView) itemView.findViewById(R.id.fullTank);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getAdapterPosition());
            }
        }
    }

    List<Model> lista;

    public RVAdapter(List<Model> lista){
        this.lista = lista;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ModelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        final ModelViewHolder pvh = new ModelViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SearchActivity.getAppContext().startActivity(new Intent(Intent.ACTION_VIEW,
//                        ItemsContract.Items.buildItemUri(getItemId(pvh.getAdapterPosition()))));
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(pvh.itemView, pvh.getAdapterPosition());
                }
            }
        });
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ModelViewHolder ModelViewHolder, int i) {
        final Model model = new Model();

        ModelViewHolder.modelName.setText(lista.get(i).getModelName());
        ModelViewHolder.idModel.setText(String.valueOf(lista.get(i).getId()));
        ModelViewHolder.carBasePrice.setText("$" + String.valueOf(lista.get(i).getBassPrice()));
        ModelViewHolder.urlImage.setText(URL + "/images/" +lista.get(i).getImages());
        ModelViewHolder.carPhoto.setImageUrl(URL + "/images/" +lista.get(i).getImages(), VolleySingleton.getInstance().getImageLoader());
//        ModelViewHolder.carPhoto.setImageUrl("http://www.deautomoviles.com.ar/articulos/modelos/img/chevrolet-camaro-2010.jpg", VolleySingleton.getInstance().getImageLoader());

        ModelViewHolder.insurance.setText(String.valueOf(lista.get(i).getInsurance()));
        ModelViewHolder.fullTank.setText(String.valueOf(lista.get(i).getFullTank()));

        ImageView ima = new ImageView(ListActivity.getAppContext());
        ima.setImageDrawable(ModelViewHolder.carPhoto.getDrawable());

        Bitmap bitmap = BitmapFactory.decodeResource(ListActivity.getAppContext().getResources(), R.drawable.switzerland);


        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                int mutedLight = palette.getMutedColor(ListActivity.getAppContext().getResources().getColor(android.R.color.black));
                ModelViewHolder.carNameHolder.setBackgroundColor(mutedLight);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }



    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}