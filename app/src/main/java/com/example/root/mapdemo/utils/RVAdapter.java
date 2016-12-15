package com.example.root.mapdemo.utils;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.example.root.mapdemo.R;
import com.example.root.mapdemo.entity.Model;

import java.util.List;

import static com.example.root.mapdemo.utils.Constant.URL;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ModelViewHolder> {
    private RequestQueue requestQueue;

    Context mContext;
    OnItemClickListener mItemClickListener;

    public RVAdapter(Context context) {
        this.mContext = context;
    }


    public class ModelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView cv;
        TextView modelName;
        TextView carBasePrice;
        NetworkImageView carPhoto;

        public ModelViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            modelName = (TextView)itemView.findViewById(R.id.model_name);
            carBasePrice = (TextView)itemView.findViewById(R.id.car_base_price);
            carPhoto = (NetworkImageView)itemView.findViewById(R.id.car_photo);

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

        ModelViewHolder.modelName.setText(lista.get(i).getModelName());
        ModelViewHolder.carBasePrice.setText("$"+String.valueOf(lista.get(i).getBassPrice()));

        //ImageLoader imageLoader= CustomVolleyRequestQueue.getInstance(this).getImageLoader();
        // Petición
//        ModelViewHolder.carPhoto.setImageUrl(URL + "/images/" +lista.get(i).getImages(), VolleySingleton.getInstance().getImageLoader());
        ModelViewHolder.carPhoto.setImageUrl("http://www.deautomoviles.com.ar/articulos/modelos/img/chevrolet-camaro-2010.jpg", VolleySingleton.getInstance().getImageLoader());
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