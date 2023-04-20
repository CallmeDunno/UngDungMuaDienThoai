package com.example.qlbdt.fFragment.Home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<ProductHome>> listProductHomeLiveData;

    private List<ProductHome> listProductHome;

    public HomeViewModel() {
        listProductHomeLiveData = new MutableLiveData<>();

        initData();
    }

    private void initData() {
        listProductHome = new ArrayList<>();

//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("Products").whereEqualTo("type", "Smartphone").get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()){
//                                    for (QueryDocumentSnapshot doc : task.getResult()){
//                                        ProductHome productHome = doc.toObject(ProductHome.class);
//                                        listProductHome.add(productHome);
//                                    }
//                                } else {
//                                    Log.e("HomeFragment", "connect to firebase fail");
//                                }
//                            }
//                        });

        listProductHomeLiveData.setValue(listProductHome);
    }

    public MutableLiveData<List<ProductHome>> getListProductHomeLiveData() {
        return listProductHomeLiveData;
    }
}
