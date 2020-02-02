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
import com.example.libraapplication.Model.CaseModel;
import com.example.libraapplication.R;

import java.util.Objects;

public class CaseDetailsActivity extends AppCompatActivity {

    TextView court_party_name;
    TextView courtname;
    TextView lawyer;
    Toolbar mToolbar;
    private OnCaseDetailListener onCaseDetailListener;

    public interface OnCaseDetailListener{
        void setCaseDeatails(CaseModel caseModel);
    }

    public void setOnCaseDetailListener(OnCaseDetailListener listener){
        if (onCaseDetailListener == null){
            onCaseDetailListener = listener;
            CaseModel caseModel = (CaseModel) getIntent().getSerializableExtra("bundle");
            onCaseDetailListener.setCaseDeatails(caseModel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_details);
        court_party_name = findViewById(R.id.case_details_name);
        courtname = findViewById(R.id.case_details_court_name);
        lawyer = findViewById(R.id.case_details_lawyer_name);
        mToolbar = findViewById(R.id.my_toolbar);
        mToolbar.setTitle("Case Details");
        mToolbar.setBackgroundColor(getResources().getColor(R.color.white));

        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        CaseModel caseModel = (CaseModel) getIntent().getSerializableExtra("bundle");
        CaseDetailsFragment caseDetailsFragment = new CaseDetailsFragment();

        court_party_name.setText(String.format("%s\nVS %s", caseModel.getParty1(), caseModel.getParty2()));
        courtname.setText(caseModel.getCourtName());
        lawyer.setText(caseModel.getLawyer());

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
