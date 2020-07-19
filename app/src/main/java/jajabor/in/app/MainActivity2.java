package jajabor.in.app;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.ModalDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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
import com.shashank.sony.fancytoastlib.FancyToast;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    NavigationView navigationView;
    MaterialSearchView searchView;
    private AppBarConfiguration mAppBarConfiguration;
    TextView prof,email;
    FirebaseFirestore db;
    List<String>pname;
    String [] suggestion;
    FirebaseUser mUser;
    DatabaseReference mDatabaseReference;
    ListView suggest;
    ArrayAdapter adapter;
    NavController navController;
    String data;
    MaterialDialog mDialog;
    DocumentReference mDocumentReference;
    private SharedViewModel viewModel;
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
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        suggest = findViewById(R.id.suggest);
        prof = findViewById(R.id.profilename);
        email = findViewById(R.id.emailid);
        pname = new ArrayList<>();
        mDialog = new MaterialDialog.Builder(this)
                .setAnimation(R.raw.edited)
                .setMessage("Are you sure want to exit?")
                .setCancelable(false)
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
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_men, R.id.nav_women,R.id.nav_about,R.id.nav_profile,R.id.nav_orderstatus,R.id.nav_wishlist,R.id.nav_customdesign, R.id.nav_contactus,R.id.nav_competition)
                .setDrawerLayout(drawer)
                .build();
        //        NavigationView navigationView = findViewById(R.id.nav_header_main);
        View linearLayout=navigationView.inflateHeaderView(R.layout.nav_header_main);
        final TextView name =  linearLayout.findViewById(R.id.profilename);
        final TextView mail =  linearLayout.findViewById(R.id.emailid);
//        View header = navigationView.getHeaderView(1);
//        View header1 = navigationView.getHeaderView(2);
//        final TextView navUser = header.findViewById(R.id.profilename);
//        final TextView navMail = header1.findViewById(R.id.emailid);
        if (mUser != null) {
            db.collection("UserProfile").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if(documentSnapshot.exists()){
                        name.setText(documentSnapshot.getString("Username"));
                      mail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    }
                }
            });
        }
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
                    BusStation.getBus().post(new Message(newText));
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
//                try {
//                    suggest.setVisibility(View.VISIBLE);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                start();
            }
        });
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.nav_home:
//                        navigationView.getMenu().getItem(0).setChecked(true);
//                        break;
//                }
//                return false;
//            }
//        });
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
//                com.shreyaspatil.MaterialDialog.MaterialDialog mDialog = new MaterialDialog.Builder(this)
//                        .setTitle("Exit?")
//                        .setMessage("Are you sure want to exit?")
//                        .setCancelable(false)
//                        .setPositiveButton("Exit", new MaterialDialog.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int which) {
//                                finishAffinity();
//                            }
//                        }).setButtonTextColor(Color.parseColor("#000000"))
//                        .setNegativeButton("Close", new MaterialDialog.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int which) {
//                                dialogInterface.dismiss();
//                            }
//                        }).show();


                // Show Dialog
                mDialog.getAnimationView().setScaleType(ImageView.ScaleType.CENTER_CROP);
                mDialog.show();
//               MaterialDialog(this)

            }


            // Show Dialog



//            finishAffinity();
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
//                FragmentManager manager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = manager.beginTransaction();
//                fragmentTransaction.replace(R.id.nav_host_fragment,new ProfileFragment());
//                fragmentTransaction.addToBackStack("1");
//                fragmentTransaction.commit();
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_profile);

//                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
//                        new ProfileFragment()).addToBackStack("v").commit();
//                getSupportFragmentManager().beginTransaction().replace(R.id.nav_view,new ProfileFragment()).addToBackStack(null).commit();
                Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.cart:
//                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_cart);
                startActivity(new Intent(this,CartActivity.class));
//                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_productview);
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