package com.example.asm.recycleview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.R;
import com.example.asm.dao.DaoThuChi;
import com.example.asm.model.ThuChi;

import java.util.ArrayList;

public class RCVLThu extends RecyclerView.Adapter<RCVLThu.ViewHolder> {
    private Context context;
    private ArrayList<ThuChi> list;
    private DaoThuChi daoThuChi;

    public RCVLThu() {
    }

    public RCVLThu(Context context, ArrayList<ThuChi> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private ImageView imgSua, imgXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textList);
            imgSua = itemView.findViewById(R.id.imgSua);
            imgXoa = itemView.findViewById(R.id.imgXoa);

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
        holder.text.setText(list.get(position).getTenKhoan());
        holder.text.setText(list.get(position).getTenKhoan());
        daoThuChi = new DaoThuChi(context);
        final ThuChi tc = list.get(position);
        //Khi nhấn nút sửa
        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
//                dialog.setCancelable(false);
                dialog.setContentView(R.layout.them_loai);
                dialog.getWindow().getAttributes().windowAnimations = R.style.up_down;
                final TextView text = dialog.findViewById(R.id.them_loai_thu);
                Button xoa = dialog.findViewById(R.id.xoaTextLT);
                final Button them = dialog.findViewById(R.id.btnThemLT);
                final TextView title = dialog.findViewById(R.id.titleThemLoai);
                title.setText("SỬA LOẠI THU");
                text.setText(tc.getTenKhoan());
                them.setText("SỬA");

                them.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String themText = text.getText().toString();
                        ThuChi thuchi = new ThuChi(tc.getMaKhoan(), themText, 0);
                        if(themText.trim().isEmpty()){
                            Toast.makeText(context, "Không được để trống!",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (daoThuChi.suaTC(thuchi) == true) {
                                list.clear();
                                list.addAll(daoThuChi.getThuChi(0));
                                notifyDataSetChanged();
                                Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
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


        //Khi nhấn nút xóa
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.xoa);
                builder.setMessage("Khi bạn xóa loại thu thì các khoản thu thuộc loại thu cũng sẽ bị xóa")
                        .setCancelable(false)
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (daoThuChi.xoaTC(tc)) {
                                    list.clear();
                                    list.addAll(daoThuChi.getThuChi(0));
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
