package com.example.qrgenerator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView qrTypeRecyclerView;
    private QRCodeTypeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        qrTypeRecyclerView = view.findViewById(R.id.qrTypeRecyclerView);
        qrTypeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<QRCodeType> qrTypes = Arrays.asList(
                new QRCodeType("URL", R.drawable.ic_url),
                new QRCodeType("Text", R.drawable.ic_text),
                new QRCodeType("Email", R.drawable.ic_email),
                new QRCodeType("Phone", R.drawable.ic_phone),
                new QRCodeType("SMS", R.drawable.ic_sms),
                new QRCodeType("VCard", R.drawable.ic_vcard),
                new QRCodeType("Location", R.drawable.ic_location),
                new QRCodeType("WiFi", R.drawable.ic_wifi),
                new QRCodeType("Event", R.drawable.ic_event)
        );

        adapter = new QRCodeTypeAdapter(qrTypes, getActivity());
        qrTypeRecyclerView.setAdapter(adapter);

        return view;
    }
}
