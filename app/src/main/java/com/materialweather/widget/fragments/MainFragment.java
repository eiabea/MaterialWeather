package com.materialweather.widget.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.materialweather.widget.R;
import com.materialweather.widget.model.OpenWeatherData;
import com.materialweather.widget.service.UpdateService;

public class MainFragment extends Fragment implements UpdateService.UpdateInterface {

    private View rootView;

    private Button btnReload;
    private TextView txtData;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        UpdateService.addUpdateInterface(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        initUi();

        setListener();

        return rootView;
    }

    private void initUi(){
        btnReload = (Button) rootView.findViewById(R.id.btn_main_reload);
        txtData = (TextView) rootView.findViewById(R.id.txt_main_data);
    }

    private void setListener(){
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startService(new Intent(getActivity(), UpdateService.class));
            }
        });

    }

    @Override
    public void onData(OpenWeatherData data) {
        txtData.setText(data.dump());
    }
}
