package jajabor.in.app.ui.Activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.ArrayList;
import java.util.List;

import jajabor.in.app.ui.Cart.CartActivity;
import jajabor.in.app.ui.Cart.SpecialOrderActivity;
import jajabor.in.app.ui.Profile.ProfileViewModel;
import jajabor.in.app.R;
import jajabor.in.app.ui.SearchResult.SerarchResultFragment;
import jajabor.in.app.ui.SearchResult.SharedViewModel;

public class MainActivity2 extends AppCompatActivity {
    NavigationView navigationView;
    MaterialSearchView searchView;
    private AppBarConfiguration mAppBarConfiguration;
    TextView prof,email,name,mail;
    FirebaseFirestore db;
    List<String>pname;
    String [] suggestion;
    FirebaseUser mUser;
    DatabaseReference mDatabaseReference;
    ListView suggest;
    ArrayAdapter adapter;
    NavController navController;
    String data,address,email1,phone,pin;
    DocumentReference noteref;
    MaterialDialog mDialog,selectCART;
    DocumentReference mDocumentReference;
    private SharedViewModel viewModel;
    private ProfileViewModel mModel;
    BottomDialog mBottomDialog;
    public String getData() {
        return data;
    }
    //Vitaly Gorbachev Amusion Borad Pankit Changhyun Lee
//Flaticon freepik Ctrlastudio
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        viewModel = ViewModelProviders.of(MainActivity2.this).get(SharedViewModel.class);
        mModel = ViewModelProviders.of(MainActivity2.this).get(ProfileViewModel.class);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mUser != null){
            viewModel.setAuth(true);
            Log.d("Authstate","Auth");
        }else
            viewModel.setAuth(false);
        if(getIntent().hasExtra("How")){
            viewModel.setType(getIntent().getStringExtra("How"));
        }
        suggest = findViewById(R.id.suggest);
        prof = findViewById(R.id.profilename);
        email = findViewById(R.id.emailid);
        pname = new ArrayList<>();
        mDialog = new MaterialDialog.Builder(this)
                .setAnimation(R.raw.edited)
                .setMessage("Are you sure want to exit?")
                .setCancelable(true)
                .setPositiveButton("Exit", R.drawable.ic_delete, new MaterialDialog.OnClickListener(
                ) {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("Cancel", R.drawable.ic_baseline_cancel_24,new MaterialDialog.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();
        selectCART = new MaterialDialog.Builder(this)
                .setTitle("Choose which cart you want to see?")
                .setCancelable(true)
                .setPositiveButton("Normal Orders", new MaterialDialog.OnClickListener(
                ) {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        startActivity(new Intent(MainActivity2.this, CartActivity.class));
                        selectCART.cancel();
                    }
                })
                .setNegativeButton("Custom Orders",new MaterialDialog.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        startActivity(new Intent(MainActivity2.this, SpecialOrderActivity.class));
                        selectCART.cancel();
                    }
                })
                .build();
        db = FirebaseFirestore.getInstance();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                   for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                       if(dataSnapshot1.getKey().equals("Name")){
                            pname.add(dataSnapshot1.getValue().toString());
                            Log.d("DARA1",dataSnapshot1.getValue().toString());
                       }
                   }
                }
                suggestion = new String[pname.size()];
                for(int i=0;i<pname.size();i++){
                    suggestion[i] = pname.get(i);
                    Log.d("DARA",suggestion[i]);
                }
                start();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mModel.getAddress().observe(MainActivity2.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("Received address",s);
                address = s;
                    check();
            }
        });
        mModel.getPhone().observe(MainActivity2.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                phone = s;
                Log.d("Received phone",s);
                check();
            }
        });
        mModel.getPin().observe(MainActivity2.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                pin = s;
                Log.d("Received pin",s);
                check();
            }
        });
        mModel.getMail().observe(MainActivity2.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                email1 = s;
                Log.d("Received email",s);
                check();
            }
        });
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("general", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = token;
                        Log.d("GENERAL", msg+"33R4");

                    }
                });
        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("MyNotifications","MyNotifiaction", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Complete";
                        if (!task.isSuccessful()) {
                            msg = "Failed";

                        }

                        Toast.makeText(MainActivity2.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("general", e.toString());
            }
        });




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_men, R.id.nav_women,R.id.nav_about,R.id.nav_profile,R.id.nav_orderstatus,R.id.nav_wishlist,R.id.nav_customdesign, R.id.nav_contactus,R.id.nav_competition)
                .setDrawerLayout(drawer)
                .build();
        View linearLayout=navigationView.inflateHeaderView(R.layout.nav_header_main);
        name =  linearLayout.findViewById(R.id.profilename);
        mail =  linearLayout.findViewById(R.id.emailid);
//            db.collection("UserProfile").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                    if(documentSnapshot.exists()){
//                        name.setText(documentSnapshot.getString("Username"));
//                      mail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
//                    }
//                }
//            });
            viewModel.getAuth().observe(MainActivity2.this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    Toast.makeText(MainActivity2.this,"Changed",Toast.LENGTH_SHORT).show();
                    if(aBoolean.booleanValue()){
                        db.collection("UserProfile").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                if(documentSnapshot.exists()){
                                    name.setText(documentSnapshot.getString("Username"));
                                    mail.setText(email1);
                                }
                            }
                        });
                    }else {
                        name.setText("Guest");
                        mail.setText("LogIn or SignUp");
                    }
                }
            });

       navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setSuggestions(suggestion);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.setText(query);
                Bundle bundle = new Bundle();
                String myMessage = query;
                bundle.putString("message", myMessage );
                SerarchResultFragment fragInfo = new  SerarchResultFragment();
                fragInfo.setArguments(bundle);
                Navigation.findNavController(MainActivity2.this, R.id.nav_host_fragment).navigate(R.id.nav_searchresult,bundle);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SerarchResultFragment fragment = SerarchResultFragment.newInstance(newText);
                //Do some magic
                if(newText != null && !newText.isEmpty()){
                    data = newText;
                    List<String> lstFound = new ArrayList<String>();
                    for(String item:suggestion){
                        if(item.contains(newText))
                            lstFound.add(item);
                    }
                    adapter = new ArrayAdapter(MainActivity2.this,android.R.layout.simple_list_item_1,lstFound);
                    suggest.setAdapter(adapter);
                }else{
                    try {
                     adapter = new ArrayAdapter(MainActivity2.this,android.R.layout.simple_list_item_1,suggestion);
                        suggest.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                start();
            }
        });
    }

    private void check() {
        if(address == null || phone == null || pin == null || email1 == null){
            try {
                mBottomDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mBottomDialog = new BottomDialog.Builder(MainActivity2.this)
                    .setTitle("Welcome User!")
                    .setContent("To continue shopping please enter the required details.")
                    .setPositiveText("Enter The Details")
                    .setCancelable(false)
                    .setPositiveBackgroundColor(android.R.color.darker_gray)
                    //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                    .setPositiveTextColorResource(android.R.color.black)
                    //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                    .onPositive(new BottomDialog.ButtonCallback() {
                        @Override
                        public void onClick(BottomDialog dialog) {
                            Log.d("BottomDialogs", "Do something!");
                            Navigation.findNavController(MainActivity2.this, R.id.nav_host_fragment).navigate(R.id.nav_profile);
                        }
                    }).show();
        }else
            try {
                mBottomDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            noteref = db.document("UserProfile/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
            Log.d("Authstate",FirebaseAuth.getInstance().getCurrentUser().getUid());
            noteref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Toast.makeText(MainActivity2.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                        Log.d("Exception", e.toString());
                        return;
                    }
                    if(documentSnapshot.exists()){
                        Log.d("Authstate","Exist");
                        if(documentSnapshot.getString("Address") != null){
                            mModel.setAddress(documentSnapshot.getString("Address"));
                            Log.d("Authstate","Address"+documentSnapshot.getString("Address"));
                        }
                        if(documentSnapshot.getString("Email") != null){
                            mModel.setMail(documentSnapshot.getString("Email"));
                            Log.d("Authstate","Address"+documentSnapshot.getString("Email"));
                        }

                        if(documentSnapshot.get("Phone") != null){
                            try {
                                Log.d("Value phone1",documentSnapshot.getString("Phone"));
                                mModel.setPhone(documentSnapshot.getString("Phone"));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        if(documentSnapshot.get("Pin") !=null){

                            try {
                                Log.d("Value pin1",documentSnapshot.getString("Pin"));
                                mModel.setPin(documentSnapshot.getString("Pin"));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                    }

                }
            });
            check();
        } catch (Exception e) {
           Log.d("Problem",e.toString());
        }
    }

    private void start() {
      adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,suggestion);
        try {
            suggest.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        searchView.setSuggestions(suggestion);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
            suggest.setVisibility(View.GONE);
        } else {

            if ( !navController.getCurrentDestination().getLabel().equals("Home")) {
                super.onBackPressed();
            }else {
                mDialog.getAnimationView().setScaleType(ImageView.ScaleType.CENTER_CROP);
                mDialog.show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_profile);
                Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.cart:
              selectCART.show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public NavigationView getNavigationView(){
        return navigationView;
    }
}