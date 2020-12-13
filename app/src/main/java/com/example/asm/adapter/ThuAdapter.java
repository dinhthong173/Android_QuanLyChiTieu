package com.example.asm.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.asm.fragment.KThuFragment;
import com.example.asm.fragment.LThuFragment;

public class ThuAdapter extends FragmentStatePagerAdapter {
    int numberTab = 2;

    public ThuAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                KThuFragment kthu = new KThuFragment();
                return kthu;
            case 1:
                LThuFragment lthu = new LThuFragment();
                return lthu;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numberTab;
    }
}
