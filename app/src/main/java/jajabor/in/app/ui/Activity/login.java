package jajabor.in.app.ui.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.nikartm.button.FitButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.shobhitpuri.custombuttons.GoogleSignInButton;
import com.skydoves.elasticviews.ElasticButton;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import jajabor.in.app.Helper.user;
import jajabor.in.app.Helper.PhoneAuthDialog;
import jajabor.in.app.R;

public class login extends AppCompatActivity {
    private static final int RC_SIGN_IN = 2;

    GoogleSignInButton mSignInButton;
    EditText txtEmail, txtPassword;
    ElasticButton btn_login,btn_signup;
    FirebaseAuth mAuth,firebaseAuth;
    GoogleApiClient mGoogleApiClient;
    DatabaseReference root;
    TextView forgot;
    FirebaseAuth.AuthStateListener mAuthListener;
    String personName;
    String personEmail;
    FirebaseFirestore db ;
    AlertDialog mDialog;
    FitButton mOTP;
    private String mVerificationId;
    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity2.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFE110")));
        bar.setTitle(Html.fromHtml("<font color='#000000'>LOGIN </font>"));
        bar.setHomeAsUpIndicator(R.drawable.ic_back_vector);
        mSignInButton = findViewById(R.id.googlebtn);
        txtEmail = (EditText)findViewById(R.id.emailtext);
        txtPassword = (EditText)findViewById(R.id.passwordtext);
        btn_login =  findViewById(R.id.mailbtn);
        btn_signup = findViewById(R.id.signupbtn);
        db = FirebaseFirestore.getInstance();
        mOTP = findViewById(R.id.OTPbtn);
        //Instance
        forgot = findViewById(R.id.forgot);
        root = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //onClickListebers
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,signup.class));

            }
        });
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                mDialog =  new SpotsDialog.Builder()
                        .setContext(login.this)
                        .setTheme(R.style.Custom)
                        .setMessage("Please Wait")
                        .setCancelable(false)
                       .build();
               mDialog.show();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(login.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(login.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(login.this, "Password too short", Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mDialog.dismiss();
                                    startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                                } else {
                                    mDialog.dismiss();
                                    Toast.makeText(login.this, "Login failed or User not Available", Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });


            }
        });
        mOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthDialog md = new PhoneAuthDialog();
                md.show(getSupportFragmentManager(),"adad");
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new com.afollestad.materialdialogs.MaterialDialog().Builder(login.this)
//                        .title("Forgot Password ?")
//                        .content("Enter your Email")
//                        .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
//                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
//                            @Override
//                            public void onInput(MaterialDialog dialog, CharSequence input) {
//                                // Do something
//                            }
//                        }).show();
//                new MaterialDialog.Builder(this)
//                        .title(R.string.input)
//                        .content(R.string.input_content)
//                        .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
//                        .input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
//                            @Override
//                            public void onInput(MaterialDialog dialog, CharSequence input) {
//                                // Do something
//                            }
//                        }).show();
                new MaterialDialog.Builder(login.this)
                        .title("Forgot Password ?")
                        .content("Enter your Email")
                        .inputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                        .positiveText("Recover Email")
                        .input("Email", "", false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                FirebaseAuth.getInstance().sendPasswordResetEmail(input.toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    FancyToast.makeText(login.this,"Mail Sent",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                                }
                                            }
                                        });
                            }
                        })
                        .show();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(login.this,MainActivity2.class));
                }
            }
        };
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(login.this,"Something wemt or wrong",Toast.LENGTH_SHORT).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                GoogleSignInAccount acct = result.getSignInAccount();
                //TO USE
                 personName = acct.getDisplayName();
                 personEmail = acct.getEmail();
                String personId = acct.getId();
//                Uri personPhoto = acct.getPhotoUrl();
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                // ...
            }
        }
        else {
            Toast.makeText(login.this,"Auth went or wrong",Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().getAdditionalUserInfo().isNewUser()){
                                Map<String, Object> note = new HashMap<>();
                                note.put("Email",personEmail);
                                note.put("Username",personName);
                                note.put("Firstname","firstName");
                                note.put("Lastname","lastName");
                                note.put("Address",null);
                                note.put("Phone",null);
                                note.put("Pin",null);

                                db.collection("UserProfile").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(login.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(login.this,"Successful",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                });
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                jajabor.in.app.Helper.user muser = new user(personName,personEmail);
                                FirebaseDatabase.getInstance().getReference("user")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(muser);
                                FirebaseDatabase.getInstance().getReference("User")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(muser);
                            }
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(login.this,"Authentication failed",Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });

    }



    //the callback to detect the verification status



}
