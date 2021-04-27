package com.andflow.fcnminner.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.andflow.fcnminner.Login;
import com.andflow.fcnminner.MainActivity;
import com.andflow.fcnminner.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    String userID;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        final TextView textView2 = root.findViewById(R.id.text_gallery2);
        final TextView textView3 = root.findViewById(R.id.text_gallery3);
        final TextView textView4 = root.findViewById(R.id.text_gallery4);
        final Button buttonVerify = root.findViewById(R.id.buttonVerify);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseUser fuser = mAuth.getCurrentUser();
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                textView2.setText("Fullname     : "+value.getString("fullName"));
                textView3.setText("Email        : "+value.getString("email"));
                textView4.setText("Address      : "+value.getString("address"));
            }
        });
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if(fuser.isEmailVerified()){
                    buttonVerify.setText("Verified");
                    buttonVerify.setEnabled(false);
                    textView.setText("Email has been verified");
                }else{
                    textView.setText("Your email has not been verified");
                    buttonVerify.setText("Verify");
                }
            }
        });

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast
                    }
                });
            }
        });

        return root;
    }
}