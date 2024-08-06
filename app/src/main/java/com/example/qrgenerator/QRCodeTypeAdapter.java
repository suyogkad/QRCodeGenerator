package com.example.qrgenerator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QRCodeTypeAdapter extends RecyclerView.Adapter<QRCodeTypeAdapter.QRTypeViewHolder> {

    private final List<QRCodeType> qrCodeTypes;
    private final Context context;

    public QRCodeTypeAdapter(List<QRCodeType> qrCodeTypes, Context context) {
        this.qrCodeTypes = qrCodeTypes;
        this.context = context;
    }

    @NonNull
    @Override
    public QRTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.qr_type_card, parent, false);
        return new QRTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QRTypeViewHolder holder, int position) {
        QRCodeType qrCodeType = qrCodeTypes.get(position);
        holder.qrTypeName.setText(qrCodeType.getName());
        holder.qrTypeIcon.setImageResource(qrCodeType.getIconResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, QRDetailsActivity.class);
            intent.putExtra("QR_TYPE", qrCodeType.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return qrCodeTypes.size();
    }

    public static class QRTypeViewHolder extends RecyclerView.ViewHolder {
        ImageView qrTypeIcon;
        TextView qrTypeName;

        public QRTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            qrTypeIcon = itemView.findViewById(R.id.qrTypeIcon);
            qrTypeName = itemView.findViewById(R.id.qrTypeName);
        }
    }
}
