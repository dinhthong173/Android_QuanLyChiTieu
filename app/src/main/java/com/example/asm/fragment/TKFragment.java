package com.example.asm.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm.R;
import com.example.asm.dao.DaoGiaoDich;
import com.example.asm.model.GiaoDich;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;


public class TKFragment extends Fragment {
    private TextView tungay, denngay, thu, chi, conlai;
    private Button btnShow;
    private DatePickerDialog datePickerDialog;
    private DaoGiaoDich daoGiaoDich;
    SimpleDateFormat dfm = new SimpleDateFormat("dd/MM/yyyy");

    public TKFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_t_k, container, false);
        tungay = view.findViewById(R.id.tungay);
        denngay = view.findViewById(R.id.denngay);
        thu = view.findViewById(R.id.tienThu);
        chi = view.findViewById(R.id.tienChi);
        conlai = view.findViewById(R.id.tienConLai);
        btnShow = view.findViewById(R.id.btnShow);
        daoGiaoDich = new DaoGiaoDich(getActivity());

        /*format shape money
        * tongThu= count(listThu)
        * tongChi= count(ListChi) */
        final NumberFormat fm = new DecimalFormat("#,###");
        final ArrayList<GiaoDich> listThu = daoGiaoDich.getGDtheoTC(0);
        final ArrayList<GiaoDich> listChi = daoGiaoDich.getGDtheoTC(1);
        int tongThu = 0, tongChi = 0;
        for (int i = 0; i < listThu.size(); i++) {
            tongThu += listThu.get(i).getSoTien();
        }
        for (int i = 0; i < listChi.size(); i++) {
            tongChi += Math.abs(listChi.get(i).getSoTien());
        }
        thu.setText(fm.format(tongThu) + " VND");
        chi.setText(fm.format(tongChi) + " VND");
        conlai.setText(fm.format(tongThu - tongChi) + " VND");


        /* Select date*/
        tungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                int m = calendar.get(Calendar.MONTH);
                int y = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String NgayDau = dayOfMonth + "/" + (month + 1) + "/" + year;
                        tungay.setText(NgayDau);
                    }
                }, y, m, d);
                datePickerDialog.show();

            }
        });

        denngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                int m = calendar.get(Calendar.MONTH);
                int y = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String NgayCuoi = dayOfMonth + "/" + (month + 1) + "/" + year;
                        denngay.setText(NgayCuoi);
                    }
                }, y, m, d);
                datePickerDialog.show();

                //Khi nhấn nút show lọc thu chi theo ngày
                btnShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int Thu = 0, Chi = 0;
                        String bd = tungay.getText().toString();
                        String kt = denngay.getText().toString();

                        //Tính tiền theo ngày
                        for (int i = 0; i < listThu.size(); i++) {
                            try {
                                if (listThu.get(i).getNgayGd().compareTo(dfm.parse(bd)) >= 0 && listThu.get(i).getNgayGd().compareTo(dfm.parse(kt)) <= 0) {
                                    Thu += listThu.get(i).getSoTien();
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        for (int i = 0; i < listChi.size(); i++) {
                            try {
                                if (listChi.get(i).getNgayGd().compareTo(dfm.parse(bd)) >= 0 && listChi.get(i).getNgayGd().compareTo(dfm.parse(kt)) <= 0) {
                                    Chi += listChi.get(i).getSoTien();
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                        thu.setText(fm.format(Thu) + " VND");
                        chi.setText(fm.format(Chi) + " VND");
                        conlai.setText(fm.format(Thu - Chi) + " VND");
                    }
                });
            }
        });
        return view;
    }
}
