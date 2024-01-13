package com.example.shoppe.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shoppe.Fragments.HelloCardOnBaordFrag;
import com.example.shoppe.Fragments.ReadyCardOnBoardFrag;
import com.example.shoppe.Fragments.ShoppingCardOnBoardFrag;

public class OnBoardAdapter extends FragmentStateAdapter {
    public OnBoardAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       if(position == 0)
           return  new ShoppingCardOnBoardFrag();
       else if (position == 1)
           return new HelloCardOnBaordFrag();
       else
           return  new ReadyCardOnBoardFrag();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
