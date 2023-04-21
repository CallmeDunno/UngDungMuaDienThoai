package com.example.qlbdt.fFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlbdt.R;
import com.example.qlbdt.fAdapter.BasketAdapter;
import com.example.qlbdt.fInterface.IRecyclerViewOnClickBasketItem;
import com.example.qlbdt.fInterface.IRecyclerViewOnClickDelete;
import com.example.qlbdt.fInterface.IRecyclerViewOnClickDetail;
import com.example.qlbdt.fObject.Basket;
import com.example.qlbdt.fObject.Basketdatabase;
import com.example.qlbdt.fOther.Notification;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BasketFragment extends Fragment {


}