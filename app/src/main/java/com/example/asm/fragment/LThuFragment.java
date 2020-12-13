package com.example.asm.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm.R;
import com.example.asm.dao.DaoThuChi;
import com.example.asm.model.ThuChi;
import com.example.asm.recycleview.RCVLThu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class LThuFragment extends Fragment {
    private RecyclerView rcv;
    private FloatingActionButton them;
    private ArrayList<ThuChi> list = new ArrayList<>();
    private DaoThuChi daoThuChi;

    public LThuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_l_thu, container, false);

        rcv = view.findViewById(R.id.rcvLThu);
        them = view.findViewById(R.id.themLThu);
        daoThuChi = new DaoThuChi(getActivity());

        list = daoThuChi.getThuChi(0);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(layoutManager);
        final RCVLThu adapter = new RCVLThu(getActivity(), list);
        rcv.setAdapter(adapter);


        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
//                dialog.setCancelable(false);
                dialog.setContentView(R.layout.them_loai);
                dialog.getWindow().getAttributes().windowAnimations = R.style.up_down;
                final TextView text = dialog.findViewById(R.id.them_loai_thu);
                Button xoa = dialog.findViewById(R.id.xoaTextLT);
                final Button them = dialog.findViewById(R.id.btnThemLT);


                them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String themText = text.getText().toString();
                        if (themText.trim().isEmpty()) {
                            Toast.makeText(getActivity(), "Không được để trống!", Toast.LENGTH_SHORT).show();
                        } else {
                            ThuChi tc = new ThuChi(0, themText, 0);
                            if (daoThuChi.themTC(tc) == true) {
                                list.clear();
                                list.addAll(daoThuChi.getThuChi(0));
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        text.setText("");
                    }
                });
                dialog.show();
            }
        });

        //Check khi nhấn vào item


        // Inflate the layout for this fragment
        return view;
    }

}
