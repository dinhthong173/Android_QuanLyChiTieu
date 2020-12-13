package com.example.asm.recycleview;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.R;
import com.example.asm.dao.DaoGiaoDich;
import com.example.asm.dao.DaoThuChi;
import com.example.asm.model.GiaoDich;
import com.example.asm.model.ThuChi;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RCVKChi extends RecyclerView.Adapter<RCVKChi.ViewHolder> {
    private Context context;
    public static ArrayList<GiaoDich> list;
    private DaoGiaoDich daoGiaoDich;
    private ArrayList<ThuChi> listTC = new ArrayList<>();
    private DaoThuChi daoThuChi;
    private DatePickerDialog datePickerDialog;
    SimpleDateFormat dfm = new SimpleDateFormat("dd/MM/yyyy");

    public RCVKChi() {
    }

    public RCVKChi(Context context, ArrayList<GiaoDich> list) {
        this.context = context;
        this.list = list;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView text;
        private ImageView imgSua, imgXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textList);
            imgSua = itemView.findViewById(R.id.imgSua);
            imgXoa = itemView.findViewById(R.id.imgXoa);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            GiaoDich gd = list.get(position);
            //Format dạng tiền
            NumberFormat fm = new DecimalFormat("#,###");
            //Hiện thông tin giao dịch khi click vào item
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.thong_tin_gd);
            dialog.getWindow().getAttributes().windowAnimations = R.style.up_down;
            TextView title, mota, ngay, tien, loai;
            mota = dialog.findViewById(R.id.mota_gd);
            ngay = dialog.findViewById(R.id.ngay_gd);
            tien = dialog.findViewById(R.id.tien_gd);
            loai = dialog.findViewById(R.id.loai_gd);
            title = dialog.findViewById(R.id.thongtinGD);
            title.setText("THÔNG TIN CHI");
            mota.setText(gd.getMoTaGd());
            ngay.setText(dfm.format(gd.getNgayGd()));
            tien.setText(fm.format(gd.getSoTien()) + " VND");
            daoThuChi = new DaoThuChi(context);
            loai.setText(daoThuChi.getTen(gd.getMaKhoan()));
            dialog.show();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.one_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(list.get(position).getMoTaGd());
        daoGiaoDich = new DaoGiaoDich(context);
        final GiaoDich gd = list.get(position);

        //Khi nhấn nút sửa
        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
//                dialog.setCancelable(false);
                dialog.setContentView(R.layout.them_khoan);
                dialog.getWindow().getAttributes().windowAnimations = R.style.up_down;
                final TextView moTaGd = dialog.findViewById(R.id.them_mota_gd);
                final TextView ngayGd = dialog.findViewById(R.id.them_ngay_gd);
                final TextView tienGd = dialog.findViewById(R.id.them_tien_gd);
                final Spinner spLoaiGd = dialog.findViewById(R.id.spLoaiGd);
                final TextView title = dialog.findViewById(R.id.titleThemKhoan);
                final Button xoa = dialog.findViewById(R.id.xoaTextGD);
                final Button them = dialog.findViewById(R.id.btnThemGD);
                daoThuChi = new DaoThuChi(context);
                listTC = daoThuChi.getThuChi(1);
                //Set tiêu đề, text
                title.setText("SỬA KHOẢN CHI");
                them.setText("SỬA");
                moTaGd.setText(gd.getMoTaGd());
                ngayGd.setText(dfm.format(gd.getNgayGd()));
                tienGd.setText(String.valueOf(gd.getSoTien()));
                final ArrayAdapter sp = new ArrayAdapter(context, R.layout.my_spinner, listTC);
                spLoaiGd.setAdapter(sp);
                int vitri = -1;
                for (int i = 0; i < listTC.size(); i++) {
                    if (listTC.get(i).getTenKhoan().equalsIgnoreCase(daoThuChi.getTen(gd.getMaKhoan()))) {
                        vitri = i;
                        break;
                    }
                }
                spLoaiGd.setSelection(vitri);


                //Khi nhấn ngày hiện lên lựa chọ ngày
                ngayGd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = Calendar.getInstance();
                        int d = calendar.get(Calendar.DAY_OF_MONTH);
                        int m = calendar.get(Calendar.MONTH);
                        int y = calendar.get(Calendar.YEAR);
                        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                final String NgayGD = dayOfMonth + "/" + month + "/" + year;
                                ngayGd.setText(NgayGD);
                            }
                        }, y, m, d);
                        datePickerDialog.show();
                    }
                });


                //Khi nhấn nút xóa
                xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(getActivity(), "Xóa",Toast.LENGTH_SHORT).show();
                        moTaGd.setText("");
                        ngayGd.setText("");
                        tienGd.setText("");
                        spLoaiGd.setAdapter(sp);
                    }
                });

                //Khi nhấn nút sửa
                them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mota = moTaGd.getText().toString();
                        String ngay = ngayGd.getText().toString();
                        String tien = tienGd.getText().toString();
                        ThuChi tc = (ThuChi) spLoaiGd.getSelectedItem();
                        int ma = tc.getMaKhoan();
                        //Check lỗi
                        if (mota.trim().isEmpty() && ngay.trim().isEmpty() && tien.trim().isEmpty()) {
                            Toast.makeText(context, "Các trường không được để trống!", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                GiaoDich giaoDich = new GiaoDich(gd.getMaGd(), mota, dfm.parse(ngay), Integer.parseInt(tien), ma);
                                if (daoGiaoDich.suaGD(giaoDich) == true) {
                                    list.clear();
                                    list.addAll(daoGiaoDich.getGDtheoTC(1));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(context, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                    }
                });

                dialog.show();


            }
        });

        //Khi nhấn nút xóa
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.xoa);
                builder.setMessage("Bạn có chắc chắn muốn xóa khoản chi?")
                        .setCancelable(false)
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (daoGiaoDich.xoaGD(gd) == true) {
                                    list.clear();
                                    list.addAll(daoGiaoDich.getGDtheoTC(1));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
//                Toast.makeText(context, gd.getMaGd()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}