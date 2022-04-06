package dev.devcrew.anonymoushopedevcrew.manifests_framents;

import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.USERS;
import static dev.devcrew.anonymoushopedevcrew.ReferenceContext.USERS_TYPE;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import dev.devcrew.anonymoushopedevcrew.MainActivity;
import dev.devcrew.anonymoushopedevcrew.R;
import dev.devcrew.anonymoushopedevcrew.isngo.NGOMainFragment;
import dev.devcrew.anonymoushopedevcrew.isuser.UsersMainFragment;

public class Login extends Fragment {
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    private MainActivity mainActivity;

    String currentVersion;
    String newVersion = null;

    ProgressBar loginProgressBar;

    AppCompatButton loginBtn;
    EditText otpTextView;
    TextInputLayout phoneNo;

    PhoneAuthCredential authCredential;

    PhoneAuthOptions phoneAuthOptions;
    String verificationsId = null;
    int time=60;
    TextView otpTime,resendAgainTextView;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.login_screen,container,false);
        inIt(root);

        loginBtn.setOnClickListener(View->{
            String login = loginBtn.getText().toString().trim();
            if (login.equals("submit")){
                String phone = phoneNo.getEditText().getText().toString().trim();
                if(phone.isEmpty()){
                    Toast.makeText(getActivity(), "Enter Phone No", Toast.LENGTH_SHORT).show();
                }else if (phone.length()==11){
                    loginProgressBar.setVisibility(android.view.View.VISIBLE);
                    phone = phone.substring(1);
                    phone = "+92"+phone;
                    requestForLogin(phone);
                }else {
                    Toast.makeText(getActivity(), "Enter Correct Phone No", Toast.LENGTH_SHORT).show();
                }
            }else if (login.equals("Verify OTP")){
               String otp = otpTextView.getText().toString().trim();
                if (otp.isEmpty()){
                    Toast.makeText(getActivity(), "Enter OTP", Toast.LENGTH_SHORT).show();
                }else if (otp.length()==6){
                    loginProgressBar.setVisibility(android.view.View.VISIBLE);
                    authCredential = PhoneAuthProvider.getCredential(verificationsId, otp); //(verificatIOnId,codeChecker);
                    loginWithPhoneAuthCredentialClient(authCredential);
                }
            }
        });





        return root;
    }

    private void loginWithPhoneAuthCredentialClient(PhoneAuthCredential authCredential) {
        mAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if (task.getResult().getCredential()!=null){
                                if(task.getResult().getAdditionalUserInfo().isNewUser()){
                                    CreateNewAccount(mAuth.getCurrentUser().getUid());
                                }else {
                                    checkUserType(mAuth.getCurrentUser().getUid());
                                }
                            }else {
                                checkUserType(mAuth.getCurrentUser().getUid());
                                Log.d("TAG", "onComplete: "+task.getException());
                            }
                        }else {
                            Log.d("TAG", "onComplete: 2"+task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: "+e.getMessage());
                    }
                });

    /*addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if (task)
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "onFailure: "+e.getMessage());
            }
        });*/
    }

    private void checkUserType(String uid) {
        FirebaseFirestore.getInstance().collection(USERS)
                .document(mAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().exists()){
                                if (task.getResult().getString(USERS_TYPE).equals("isAdmin")){
                                    Log.d("TAG", "onComplete: admin");
                                }else if (task.getResult().getString(USERS_TYPE).equals("isNGO")){
                                    Log.d("TAG", "onComplete: isNGO");
                                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragmentMainLayout,new NGOMainFragment() ,"ListOfFriends");
                                    fragmentTransaction.commit();
                                }else if (task.getResult().getString(USERS_TYPE).equals("isUser")){
                                    Log.d("TAG", "onComplete: isUser");
                                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragmentMainLayout,new UsersMainFragment() ,"ListOfFriends");
                                    fragmentTransaction.commit();
                                }
                            }else {
                                CreateNewAccount(mAuth.getCurrentUser().getUid());
                            }
                        }
                    }
                });
    }

    private void CreateNewAccount(String uid) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentMainLayout,new CreateUserAccount() ,"ListOfFriends");
        fragmentTransaction.commit();
    }

    private void requestForLogin(String phone) {
        Log.d("phone",phone);
        phoneAuthOptions = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phone)
                .setTimeout(80L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(getActivity())                 // Activity (for callback binding)
                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Log.d("TAG", "onVerificationCompleted: " + phoneAuthCredential);
            // authCredential = phoneAuthCredential;
            loginProgressBar.setVisibility(View.GONE);
        }

        @Override
        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(verificationId, forceResendingToken);
            timeCountDown();
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d("TAG", "onCodeSent: " + verificationId);
            // Save verification ID and resending token so we can use them later
            verificationsId = verificationId;
            // mResendToken = forceResendingToken;
            otpTextView.setVisibility(View.VISIBLE);
            loginProgressBar.setVisibility(View.GONE);
            loginBtn.setText("Verify OTP");
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.d("TAG", "onCodeSent: " + e.getMessage());
            if(e.getMessage().equals("We have blocked all requests from this device due to unusual activity. Try again later.")){
                Toast.makeText(getActivity(), "We have blocked all requests from this device due to unusual activity. Try again later.", Toast.LENGTH_SHORT).show();
            }
            loginProgressBar.setVisibility(View.GONE);
            loginBtn.setText("Submit");
        }
    };

    private void timeCountDown() {
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                otpTime.setText("0:"+checkDigit(time));
                time--;
            }
            public void onFinish() {
                otpTime.setText("00:00");
                resendAgainTextView.setText("Resend OTP");
            }
        }.start();
    }
    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }


    private void inIt(View root) {
        resendAgainTextView = root.findViewById(R.id.resendAgainTextView);
        otpTime = root.findViewById(R.id.otpTime);
        loginProgressBar = root.findViewById(R.id.loginProgressBar);
        loginBtn = root.findViewById(R.id.loginBtn);
        otpTextView = root.findViewById(R.id.otpTextView);
        otpTextView.setVisibility(View.GONE);
        phoneNo = root.findViewById(R.id.phoneNo);
        mAuth = FirebaseAuth.getInstance();
    }
}

