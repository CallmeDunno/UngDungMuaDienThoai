package com.example.qlbdt.fFragment.Search;

import android.content.ClipData;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlbdt.R;
import com.example.qlbdt.fActivity.HomeActivity;
import com.example.qlbdt.fAdapter.SmartPhoneSearchAdapter2;
import com.example.qlbdt.fAdapter.SmartphoneSearchAdapter;
import com.example.qlbdt.fObject.Smartphone;
import com.example.qlbdt.fObject.User2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.remote.WatchChange;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tiến Dũng
 * MVVM + Firebase
 * Thiết kế giao diện
 * */

public class SearchFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<User2> user2ArrayList;
    SmartphoneSearchAdapter searchAdapter;
    SmartPhoneSearchAdapter2 searchAdapter2;
    FirebaseFirestore db;
    Spinner sp_sort, sp_brand;
    ArrayList<String> sort, brand;
    ArrayAdapter adapterSort, adapterBrand;
    private FirebaseFirestore firestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.rcv_fragment_search);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(searchAdapter2);
        db =FirebaseFirestore.getInstance();
        user2ArrayList= new ArrayList<User2>();
        searchAdapter2 = new SmartPhoneSearchAdapter2(user2ArrayList);
        LoadData();



        sp_sort = view.findViewById(R.id.sp_sort_fragment_search);
        sort = new ArrayList<>();
        sort.add("Sắp xếp theo tên sản phẩm");
        sort.add("Sắp xếp theo giá tăng dần");
        sort.add("Sắp xếp theo giá giảm dần");
        adapterSort = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sort);
        sp_sort.setAdapter(adapterSort);

        sp_brand = view.findViewById(R.id.sp_brand_fragment_search);
        brand = new ArrayList<>();
        brand.add("Hãng");
        adapterBrand = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, brand);
        sp_brand.setAdapter(adapterBrand);

        return view;
    }
    private void LoadData(){
        db.collection("Products").orderBy("name", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.d( "onEvent: ",error.getMessage());
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){
                        user2ArrayList.add(dc.getDocument().toObject(User2.class));

                    }
                    searchAdapter2.notifyDataSetChanged();
                }
            }
        });
    }

}