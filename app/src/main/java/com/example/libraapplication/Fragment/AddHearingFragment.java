package com.example.libraapplication.Fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.libraapplication.AddJudgeDialog;
import com.example.libraapplication.CalenderBox;
import com.example.libraapplication.R;

import java.util.ArrayList;

public class AddHearingFragment extends Fragment implements AddJudgeDialog.UpDateJudgesListListener {

    private EditText edit_add_judges;
    private Button add_judge_button;
    private ArrayList<String> judges_list;
    private AddJudgeDialog addJudgeDialog;
    private EditText edit_hearing_date;
    CalenderBox calenderBox;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_hearing_fragment,null);
        judges_list = new ArrayList<>();
        edit_add_judges = view.findViewById(R.id.add_judges);
        add_judge_button = view.findViewById(R.id.add_judge_button);
        edit_hearing_date = view.findViewById(R.id.edit_hearing_date);
        calenderBox = new CalenderBox(getActivity());

        add_judge_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edit_add_judges.getText().toString().isEmpty()){
                    addJudgeList();
                    judges_list.add(edit_add_judges.getText().toString());
                    edit_add_judges.setText("");
                    addJudgeDialog.show();
                }
                else {
                    Toast.makeText(getActivity(), "Please Add Judge First", Toast.LENGTH_SHORT).show();
                }

            }
        });

        edit_hearing_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calenderBox.show();
                Window window = calenderBox.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                loadFragment(new TabFragment());
                return true;
            }
            return false;
        });
        return  view;
    }



    public void addJudgeList(){
        addJudgeDialog = new AddJudgeDialog(getActivity(),judges_list,this);
    }

    @Override
    public void updateJudgeList(ArrayList<String> judgesList) {
        this.judges_list = judgesList;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }


}
