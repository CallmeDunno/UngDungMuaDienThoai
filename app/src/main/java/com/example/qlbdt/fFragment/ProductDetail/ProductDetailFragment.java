package com.example.qlbdt.fFragment.ProductDetail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qlbdt.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

/***
 * Hoàng
 * MVVM
 * Thiết kế giao diện
 * */

public class ProductDetailFragment extends Fragment {
    FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        getdata();

        return inflater.inflate(R.layout.fragment_product_detail, container, false);

    }

    private void getdata() {
       db.collection("Product").orderBy("name", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
           public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
               if(error != null){
                   Log.d( "onEvent: ",error.getMessage());
                   return;
               }
               for (DocumentChange dc : value.getDocumentChanges()){
                   if (dc.getType() == DocumentChange.Type.ADDED){


                   }

               }
           }
       });
    }
}