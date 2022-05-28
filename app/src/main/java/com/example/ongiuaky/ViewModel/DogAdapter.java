package com.example.ongiuaky.ViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ongiuaky.R;
import com.example.ongiuaky.model.DogBreed;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.ViewHolder> {
    private List<DogBreed>dogsList;
    private OnDogListener onDogListener;
    public DogAdapter(List<DogBreed> dogsList,OnDogListener onDogListener){
        this.dogsList=dogsList;
        this.onDogListener=onDogListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dog_item, parent, false);

        return new ViewHolder(view,onDogListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DogBreed dogBreed = dogsList.get(position);
        holder.tvDogName.setText(dogBreed.getName());
        holder.tvDogBred.setText(dogBreed.getBreed_for());
        Picasso.get().load(dogBreed.getUrl()).into(holder.ivDogImage);


    }

    @Override
    public int getItemCount() {
        return dogsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivDogImage;
        private TextView tvDogName, tvDogBred;

        OnDogListener onDogListener;
        public ViewHolder(View view,OnDogListener onDogListener) {
            super(view);
            // Define click listener for the ViewHolder's View
            ivDogImage = view.findViewById(R.id.iv_dogImage);
            tvDogName = view.findViewById(R.id.tv_dogName);
            tvDogBred = view.findViewById(R.id.tv_dogBred);
            this.onDogListener=onDogListener;
            view.setOnClickListener(this);
            // textView = (TextView) view.findViewById(R.id.textView);
        }

        @Override
        public void onClick(View view) {
            onDogListener.onDogClick(getAdapterPosition());

        }


//        public TextView getTextView() {
//            return textView;
//        }
    }
    public interface OnDogListener{
        void onDogClick(int position);
    }
}
