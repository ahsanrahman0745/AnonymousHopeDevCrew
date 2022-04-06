package dev.devcrew.anonymoushopedevcrew.manifests_framents;

import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_NGO_NAME;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_NGO_OR_USERS_IMAGE;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_NGO_OR_USER_PHONE_NO;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_NGO_SECOND_NAME;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_DOB;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_EMAIL;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.KEY_USERS_NAME;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.USERS;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.USERS_TYPE;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import dev.devcrew.anonymoushopedevcrew.R;
import dev.devcrew.anonymoushopedevcrew.isngo.NGOMainFragment;
import dev.devcrew.anonymoushopedevcrew.isuser.UsersMainFragment;

public class CreateUserAccount extends Fragment {

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    RadioGroup radioGroup;
    RadioButton radioBtnNGO,radioBtnDonnerOrNeedy;

    String radioBtnAccount="isUser";

    AppCompatButton createDonnerOrNeedyAccount;

    TextInputLayout nameOfUser,emailOfUser,addreeOfUser,secondNameOfUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.create_user_account, container, false);
        inIt(root);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch(checkedId) {
                    case R.id.radioBtnNGO:
                        secondNameOfUser.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Select NGO", Toast.LENGTH_SHORT).show();
                        radioBtnNGO.setChecked(true);
                        radioBtnAccount = "isNGO";
                        break;
                    case R.id.radioBtnDonnerOrNeedy:
                        secondNameOfUser.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Select Donner/Needy", Toast.LENGTH_SHORT).show();
                        radioBtnDonnerOrNeedy.setChecked(true);
                        radioBtnAccount = "isUser";
                        break;
                }
            }
        });

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("TAG", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };


        createDonnerOrNeedyAccount.setOnClickListener(View->{
            if (emailOfUser.getEditText().getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();
            }else if (nameOfUser.getEditText().getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Enter Needy/Donner/NGO Name", Toast.LENGTH_SHORT).show();
            }else if(mDisplayDate.getText().toString().equals("Select DOB")){
                Toast.makeText(getActivity(), "Select DOB", Toast.LENGTH_SHORT).show();
            }else {
                Map<Object,Object> map = new HashMap<>();
                if (radioBtnAccount.equals("isUser")){
                    map.put(USERS_TYPE,radioBtnAccount);
                    map.put(KEY_NGO_SECOND_NAME,secondNameOfUser.getEditText().getText().toString());
                    map.put(KEY_USERS_NAME,nameOfUser.getEditText().getText().toString());
                    map.put(KEY_USERS_DOB,mDisplayDate.getText().toString());
                    map.put(KEY_USERS_EMAIL,emailOfUser.getEditText().getText().toString());
                    map.put(KEY_NGO_OR_USERS_IMAGE,null);
                    map.put(KEY_NGO_OR_USER_PHONE_NO,FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                    sendUPLOAD(map);
                }else {
                    map.put(USERS_TYPE,radioBtnAccount);
                    map.put(KEY_NGO_OR_USER_PHONE_NO,FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                    map.put(KEY_NGO_OR_USERS_IMAGE,null);
                    map.put(KEY_NGO_NAME,nameOfUser.getEditText().getText().toString());
                    map.put(KEY_USERS_DOB,mDisplayDate.getText().toString());
                    map.put(KEY_USERS_EMAIL,emailOfUser.getEditText().getText().toString());
                    sendUPLOAD(map);
                }

            }


        });



        return root;
    }

    private void sendUPLOAD(Map<Object, Object> map) {
        FirebaseFirestore.getInstance().collection(USERS)
                .document(FirebaseAuth.getInstance().getUid())
                .set(map);

        if (radioBtnAccount.equals("isUser")){
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentMainLayout,new UsersMainFragment() ,"ListOfFriends");
            fragmentTransaction.commit();
        }else {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentMainLayout,new NGOMainFragment() ,"ListOfFriends");
            fragmentTransaction.commit();
        }
    }


    private void inIt(View root) {
        createDonnerOrNeedyAccount = root.findViewById(R.id.createDonnerOrNeedyAccount);
        secondNameOfUser = root.findViewById(R.id.secondNameOfUser);
        addreeOfUser = root.findViewById(R.id.addreeOfUser);
        nameOfUser = root.findViewById(R.id.nameOfUser);
        emailOfUser = root.findViewById(R.id.emailOfUser);
        mDisplayDate = (TextView) root.findViewById(R.id.tvDate);
        radioGroup =  root.findViewById(R.id.radioGroup);
        radioBtnNGO =  root.findViewById(R.id.radioBtnNGO);
        radioBtnDonnerOrNeedy =  root.findViewById(R.id.radioBtnDonnerOrNeedy);
        radioBtnDonnerOrNeedy.setChecked(true);
    }
}