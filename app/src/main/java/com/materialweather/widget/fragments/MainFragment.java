package com.materialweather.widget.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.materialweather.widget.R;
import com.materialweather.widget.adapter.CityListAdapter;
import com.materialweather.widget.data.DataProvider;
import com.materialweather.widget.model.City;
import com.materialweather.widget.model.OpenWeatherData;
import com.materialweather.widget.service.UpdateService;

public class MainFragment extends Fragment implements UpdateService.UpdateInterface, LoaderManager.LoaderCallbacks<Cursor> {

    private View rootView;

    private Button btnReload;
    private Button btnAddCity;
    private TextView txtData;
    private EditText edtCity;
    private ListView lvCities;
    private CityListAdapter adapter;

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

        fillUp();

        getLoaderManager().initLoader(1, null, this);

        return rootView;
    }

    private void initUi(){
        edtCity = (EditText) rootView.findViewById(R.id.edt_main_city);
        btnAddCity = (Button) rootView.findViewById(R.id.btn_main_add_city);
        btnReload = (Button) rootView.findViewById(R.id.btn_main_reload);
//        txtData = (TextView) rootView.findViewById(R.id.txt_main_data);

        lvCities = (ListView) rootView.findViewById(R.id.lv_main_cities);
    }

    private void setListener(){
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(getActivity(), UpdateService.class);
                updateIntent.putExtra(UpdateService.PARAM_ACTION, UpdateService.Action.RELOAD_ALL_CITIES);

                getActivity().startService(updateIntent);
            }
        });

        btnAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(getActivity(), UpdateService.class);
                searchIntent.putExtra(UpdateService.PARAM_ACTION, UpdateService.Action.SEARCH_CITY);
                searchIntent.putExtra(UpdateService.PARAM_SEARCH_STRING, edtCity.getText().toString());

                getActivity().startService(searchIntent);
            }
        });

        lvCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog(id, view);
            }
        });

    }

    // TODO remove
    // just for debugging
    private void showDialog(final long id, final View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("[DEBUG] What do you want??");
        builder.setItems(new CharSequence[] {"Delete City", "Update City Weather"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                                DataProvider.deleteCity(getActivity(), id);

                                break;

                            case 1:

                                CityListAdapter.CityViewHolder viewHolder = (CityListAdapter.CityViewHolder) view.getTag();

                                long cityId = viewHolder.getCityId();

                                Intent updateIntent = new Intent(getActivity(), UpdateService.class);
                                updateIntent.putExtra(UpdateService.PARAM_ACTION, UpdateService.Action.RELOAD_ONE_CITY);
                                updateIntent.putExtra(UpdateService.PARAM_CITY_ID, cityId);

                                getActivity().startService(updateIntent);

                                break;

                            default:
                                break;
                        }
                    }
                });

        builder.show();
    }

    private void fillUp() {
        adapter = new CityListAdapter(getActivity(), null);
        lvCities.setAdapter(adapter);
    }

    @Override
    public void onData(OpenWeatherData data) {
//        txtData.setText(data.dump());
    }

    @Override
    public void onError(String message) {
//        txtData.setText(data.dump());
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int which, Bundle arg1) {


        String selection = null;
        String[] selectionArgs = null;

        return new CursorLoader(getActivity(), City.CONTENT_URI, null, selection, selectionArgs, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor c) {

        if(adapter != null && c.getCount() > 0){
            DatabaseUtils.dumpCursor(c);

            adapter.changeCursor(c);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
    }
}
