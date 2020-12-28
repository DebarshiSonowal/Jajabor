package jajabor.in.app.ui.CustomDesign;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pd.chocobar.ChocoBar;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.elasticviews.ElasticImageView;
import com.squareup.picasso.Picasso;

import java.security.SecureRandom;

import de.mrapp.android.dialog.ProgressDialog;
import jajabor.in.app.Helper.Contract3;
import jajabor.in.app.Helper.DatabaseHelper3;
import jajabor.in.app.R;
import jajabor.in.app.ui.Cart.SpecialOrderActivity;
import lib.kingja.switchbutton.SwitchMultiButton;
import me.himanshusoni.quantityview.QuantityView;

import static android.app.Activity.RESULT_OK;

public class CustomDesignFragment extends Fragment {
    //Darius Dan
    StorageReference mStorageReference;
    FirebaseStorage mStorage;
    FirebaseFirestore db;
    RoundedImageView design;
    ElasticImageView select, upload;
    SwitchMultiButton sizeswitch, colourswitch;
    QuantityView quantity;
    String size = "S", colour = "Black";
    Integer quant = 1, monry = 299;
    SQLiteDatabase mDatabase;
    Uri mUri;
    ElasticButton pay;
    Uri downloadUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom_design, container, false);
        DatabaseHelper3 databaseHelper = new DatabaseHelper3(getContext());
        mDatabase = databaseHelper.getWritableDatabase();
        mStorage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        mStorageReference = mStorage.getReference();
        select = view.findViewById(R.id.selectbtn);
        upload = view.findViewById(R.id.upldbtn);
        design = view.findViewById(R.id.designview);
        colourswitch = view.findViewById(R.id.colourswitch);
        sizeswitch = view.findViewById(R.id.sizeswitch);
        quantity = view.findViewById(R.id.quantityview);
        pay = view.findViewById(R.id.paybtn2);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUri != null && downloadUrl != null) {
                    ContentValues cv = new ContentValues();
                    cv.put(Contract3.CartItem.COLUMN_NAME, "Custom" + getRandomNumberString());
                    cv.put(Contract3.CartItem.COLUMN_PIC, downloadUrl.toString());
                    cv.put(Contract3.CartItem.COLUMN_PRICE, monry * quant);
                    Log.d("PICNAHI", monry * quant + "");
                    cv.put(Contract3.CartItem.COLUMN_SIZE, size);
                    Log.d("PICNAHI", size);
                    cv.put(Contract3.CartItem.COLUMN_COLOR, colour);
                    Log.d("PICNAHI", colour);
                    cv.put(Contract3.CartItem.COLUMN_QUANTITY, quant);
                    Log.d("PICNAHI", quant + "");
                    cv.put(Contract3.CartItem.COLUMN_PID, getRandomNumberString());
                    mDatabase.insert(Contract3.CartItem.TABLE_NAME, null, cv);
                    ChocoBar.builder().setActivity(getActivity())
                            .setText("Added to cart")
                            .setActionText("Go to cart")
                            .setActionClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getActivity(), SpecialOrderActivity.class));
                                }
                            })
                            .green()  // in built green ChocoBar
                            .show();
                } else {
                    ChocoBar.builder().setActivity(getActivity())
                            .setText("No file is selected")
                            .setDuration(ChocoBar.LENGTH_SHORT)
                            .setActionText(android.R.string.ok)
                            .red()   // in built red ChocoBar
                            .show();
                }
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectVideo();
                } else
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUri != null) {
                    uploadvideo(mUri);
                } else {
                    ChocoBar.builder().setActivity(getActivity())
                            .setText("No file is selected")
                            .setDuration(ChocoBar.LENGTH_SHORT)
                            .setActionText(android.R.string.ok)
                            .red()   // in built red ChocoBar
                            .show();
                }
            }
        });
        colourswitch.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                Toast.makeText(getContext(), tabText, Toast.LENGTH_SHORT).show();
                colour = tabText;
            }
        });
        sizeswitch.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                Toast.makeText(getContext(), tabText, Toast.LENGTH_SHORT).show();
                size = tabText;
            }
        });
        quantity.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {
                Toast.makeText(getContext(), "" + newQuantity, Toast.LENGTH_SHORT).show();
                quant = newQuantity;
                pay.setText("₹" + monry * quant);
            }

            @Override
            public void onLimitReached() {
                Toast.makeText(getContext(), "Limit reached", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void uploadvideo(Uri uri) {
        SecureRandom d = new SecureRandom();
        final ProgressDialog.Builder dialogBuilder = new ProgressDialog.Builder(getContext());
        dialogBuilder.setTitle("Uploading");
        dialogBuilder.setMessage(FirebaseAuth.getInstance().getCurrentUser().getUid() + d.nextInt(100));
        dialogBuilder.setPositiveButton(android.R.string.ok, null);
        dialogBuilder.setNegativeButton(android.R.string.cancel, null);
        dialogBuilder.setProgressBarPosition(ProgressDialog.ProgressBarPosition.LEFT);
        final ProgressDialog dialog = dialogBuilder.create();
        dialog.show();

        mStorageReference.child("Uploads").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(FirebaseAuth.getInstance().getCurrentUser().getUid() + d.nextInt(1000))
                .putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful()) ;
                downloadUrl = urlTask.getResult();
                Log.d("PICNAHI", urlTask.getResult() + "");
                final String url = String.valueOf(downloadUrl);
                dialog.hide();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FancyToast.makeText(getContext(), "Unable to save the image" + e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 86 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectVideo();

        } else
            Toast.makeText(getContext(), "please give permission", Toast.LENGTH_SHORT).show();

    }

    private void selectVideo() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mUri = data.getData();
            Picasso.get().load(mUri).into(design);

            //+
//            notificatiom.setText("A file is seleted "+data.getData().getLastPathSegment());
        } else
            Toast.makeText(getContext(), "Please upload a file", Toast.LENGTH_SHORT).show();
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        SecureRandom secRan = new SecureRandom();
        int number = secRan.nextInt(999999);
        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}