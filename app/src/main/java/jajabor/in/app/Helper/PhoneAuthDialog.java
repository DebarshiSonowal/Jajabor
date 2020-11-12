package jajabor.in.app.Helper;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.skydoves.elasticviews.ElasticButton;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import jajabor.in.app.R;
import jajabor.in.app.ui.Activity.MainActivity2;

public class PhoneAuthDialog extends AppCompatDialogFragment {
    EditText phone;
    ElasticButton otpbtn,verifybtn;
    PhoneDialogListener listener;
    FirebaseAuth mAuth;
    OtpTextView otpTextView;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String codeSent,code;
FirebaseFirestore db;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_phone_auth_dialog, null);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        phone = view.findViewById(R.id.edit_phone);
//        code = view.findViewById(R.id.edit_verificationcode);
        otpbtn = view.findViewById(R.id.sendOTPbtn);
        verifybtn = view.findViewById(R.id.verifyCODE);
        otpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String phoneno = phone.getText().toString();

                    if(phoneno.isEmpty()){
                        phone.setError("Phone number is required");
                        phone.requestFocus();
                        return;
                    }

                    if(phoneno.length() < 10 ){
                        phone.setError("Please enter a valid phone");
                        phone.requestFocus();
                        return;
                    }


                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91"+phoneno,        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        getActivity(),               // Activity (for callback binding)
                        mCallbacks);        // OnVerificationStateChangedCallbacks      // OnVerificationStateChangedCallbacks
                    otpTextView.requestFocus();
                }


        });
        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code != null) {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
                    signInWithPhoneAuthCredential(credential);
                } else {
                    FancyToast.makeText(getContext(),"Enter a valid otp",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                }
            }
        });
        otpTextView = view.findViewById(R.id.otp_view);
        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // fired when user types something in the Otpbox
            }
            @Override
            public void onOTPComplete(String otp) {
                // fired when user has entered the OTP fully.
                code = otp;
                Toast.makeText(getContext(), "The OTP is " + otp,  Toast.LENGTH_SHORT).show();
            }
        });
        builder.setView(view);
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(getContext(),"Sent",Toast.LENGTH_SHORT).show();
                codeSent = s;
            }
        };
        return builder.create();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//            listener = (PhoneDialogListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() +
//                    "must implement ExampleDialogListener");
//        }
//    }
    public interface PhoneDialogListener {
        void applyTexts(String username, String password);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().getAdditionalUserInfo().isNewUser()){
                                Map<String, Object> note = new HashMap<>();
                                note.put("Phone",phone.getText().toString());
                                db.collection("UserProfile").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent i = new Intent(getContext(), MainActivity2.class);
                                        i.putExtra("How","Phone");
                                        startActivity(i);
                                    }
                                });
                                Toast.makeText(getContext(),
                                        "Login Successfull", Toast.LENGTH_LONG).show();
                            }else
                            {
                                Intent i = new Intent(getContext(),MainActivity2.class);
                                i.putExtra("How","Phone");
                                startActivity(i);
                            }



                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getContext(),
                                        "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

}