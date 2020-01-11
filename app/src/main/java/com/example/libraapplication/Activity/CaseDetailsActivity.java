package com.example.libraapplication.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.libraapplication.Fragment.CaseDetailsFragment;
import com.example.libraapplication.Model.CaseModel;
import com.example.libraapplication.R;

public class CaseDetailsActivity extends AppCompatActivity {

    TextView court_party_name;
    TextView courtname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_details);

        court_party_name = findViewById(R.id.case_details_name);
        courtname = findViewById(R.id.case_details_court_name);

        CaseModel caseModel = (CaseModel) getIntent().getSerializableExtra("bundle");
        CaseDetailsFragment caseDetailsFragment = new CaseDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bundle",caseModel);
        caseDetailsFragment.setArguments(bundle);

        court_party_name.setText(String.format("%s\nVS %s", caseModel.getParty1(), caseModel.getParty2()));
        courtname.setText(caseModel.getParty2());




        loadFragment(caseDetailsFragment);


    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}
