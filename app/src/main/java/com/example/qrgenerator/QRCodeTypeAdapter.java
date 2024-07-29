package com.example.qrgenerator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// QRCodeTypeAdapter.java
// QRCodeTypeAdapter.java
public class QRCodeTypeAdapter extends RecyclerView.Adapter<QRCodeTypeAdapter.ViewHolder> {

    private String[] qrTypes;
    private Context context;

    public QRCodeTypeAdapter(Context context, String[] qrTypes) {
        this.context = context;
        this.qrTypes = qrTypes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qr_type_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.qrTypeTextView.setText(qrTypes[position]);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, QRDetailsActivity.class);
            intent.putExtra("QR_TYPE", qrTypes[position]);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return qrTypes.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView qrTypeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            qrTypeTextView = itemView.findViewById(R.id.qrTypeTextView);
        }
    }
}
