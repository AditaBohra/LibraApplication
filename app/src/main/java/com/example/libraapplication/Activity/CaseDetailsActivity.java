package com.example.libraapplication.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.libraapplication.Fragment.CaseDetailsFragment;
import com.example.libraapplication.Model.HearingModel;
import com.example.libraapplication.R;

import java.util.Objects;

public class CaseDetailsActivity extends AppCompatActivity {

    TextView court_party_name;
    TextView courtname;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_details);

        court_party_name = findViewById(R.id.case_details_name);
        courtname = findViewById(R.id.case_details_court_name);
        mToolbar = findViewById(R.id.my_toolbar);
        mToolbar.setTitle("Case Details");
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));

        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        HearingModel hearingModel = (HearingModel) getIntent().getSerializableExtra("bundle");
        CaseDetailsFragment caseDetailsFragment = new CaseDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bundle", hearingModel);
        caseDetailsFragment.setArguments(bundle);

        court_party_name.setText(String.format("%s\nVS %s", hearingModel.getParty1(), hearingModel.getParty2()));
        courtname.setText(hearingModel.getParty2());

        loadFragment(caseDetailsFragment);


    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }

}
