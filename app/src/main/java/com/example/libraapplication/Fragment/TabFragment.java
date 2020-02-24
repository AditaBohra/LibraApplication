package com.example.libraapplication.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.libraapplication.Activity.ProfileActivity;
import com.example.libraapplication.Adapter.ViewPagerAdapter;
import com.example.libraapplication.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class TabFragment extends Fragment
{
    private ImageView profileImageView;
    private ImageView whatsappImage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab_fragment,null);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
        setUpViewPager(viewPager);

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        profileImageView = view.findViewById(R.id.profile_img);
        whatsappImage = view.findViewById(R.id.whatsapp_img);

        profileImageView.setOnClickListener(v -> Objects.requireNonNull(getActivity()).startActivity(new Intent(getActivity(), ProfileActivity.class)));

        whatsappImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWhatsApp(view);
            }
        });
        return view;
    }

    private void setUpViewPager(ViewPager viewPager) {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), 0);
        viewPager.setAdapter(viewPagerAdapter);
    }

    /*
        Method to open WhatsApp..
     */
    public void openWhatsApp(View view){
        try {
            String text = "Hello Kaleeyantey!";// Replace with your message.

            String toNumber = "919322115675"; // Replace with mobile phone number without +Sign or leading zeros, but with country code
            //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.


            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
