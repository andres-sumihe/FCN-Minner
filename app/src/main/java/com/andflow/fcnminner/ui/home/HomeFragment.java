package com.andflow.fcnminner.ui.home;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.andflow.fcnminner.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView txtFullname, txtEmail, currentHash, hashPerSecond;
    Random rand = new Random();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("https://currencyapi-net.p.rapidapi.com/currencies?output=JSON")
//                .get()
//                .addHeader("x-rapidapi-key", "3581c2a659msha5d31bc133fd9e4p11e2f9jsnb8fca9a1567e")
//                .addHeader("x-rapidapi-host", "currencyapi-net.p.rapidapi.com")
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                if(response.isSuccessful()){
//                    String myResponse = response.body().string();
//                    TextView txt = (TextView) root.findViewById(R.id.bitcoinRate);
////                    txt.setText(myResponse);
//                }
//            }
//
//        });


        Button start = (Button) root.findViewById(R.id.startHash);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long maxCounter = 10000000;
                long diff = 1000;

                new CountDownTimer(maxCounter , diff ) {

                    public void onTick(long millisUntilFinished) {
                        currentHash = root.findViewById(R.id.currentHash);
                        hashPerSecond = root.findViewById(R.id.hashPerSecond);
                        long diff = maxCounter - millisUntilFinished;
                        currentHash.setText(String.format("%.4f", Float.parseFloat(diff  / 32132 +"" ))+"");
                        hashPerSecond.setText( String.format("%.2f", Float.parseFloat(1+rand.nextDouble()+""))+ " GH/s");
                    }

                    public void onFinish() {
                        currentHash.setText(" ");
                    }

                }.start();
            }
        });
        return root;
    }
}