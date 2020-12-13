package com.example.asm.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.asm.fragment.KChiFragment;
import com.example.asm.fragment.LChiFragment;

public class ChiAdapter extends FragmentStatePagerAdapter {
    int numberTab = 2;

    public ChiAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                KChiFragment kthu = new KChiFragment();
                return kthu;
            case 1:
                LChiFragment lthu = new LChiFragment();
                return lthu;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numberTab;
    }
}
