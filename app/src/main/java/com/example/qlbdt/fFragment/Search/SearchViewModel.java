package com.example.qlbdt.fFragment.Search;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qlbdt.fFragment.Home.ProductHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<ProductSearch>> listProductSearchLiveData;

    private List<ProductSearch> listProductSearch;


    public SearchViewModel() {
        listProductSearchLiveData = new MutableLiveData<>();

        initData();
    }

    private void initData() {
        listProductSearch = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products")
                .whereEqualTo("type", "Smartphone")
                .limit(5)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot doc : task.getResult()){

                                String name = doc.toObject(ProductSearch.class).getName();
                                String price = doc.toObject(ProductSearch.class).getPrice();
                                String image = doc.toObject(ProductSearch.class).getImage();

                                listProductSearch.add(new ProductSearch(name, price, image));
                            }
                            Collections.shuffle(listProductSearch);

                            listProductSearchLiveData.postValue(listProductSearch);

                        } else {
                            Log.e("SearchFragment", "not data");
                        }
                    }
                });

        listProductSearchLiveData.setValue(listProductSearch);

    }

    public MutableLiveData<List<ProductSearch>> getListProductSearchLiveData() {
        return listProductSearchLiveData;
    }
}
