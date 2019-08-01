package com.adaxiom.subFragments.timesheet;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adaxiom.locumset.R;
import com.adaxiom.locumset.TimeSheet;
import com.adaxiom.utils.RuntimePermissions;
import com.adaxiom.utils.Utilities;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.adaxiom.utils.Constants.PREF_SIGNATURE;


public class FragTimeSheetFour extends Fragment {

    View view;
    SignaturePad signaturePad;
    View btnSaveSign, btnClearSign;
    public static String postImagePath ="";
    Utilities utilities = new Utilities();
    TimeSheet timeSheet;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_timesheet_four, container, false);


        setViews();


        return view;
    }

    private void setViews() {

        signaturePad = view.findViewById(R.id.signature_pad);
        btnSaveSign = view.findViewById(R.id.tvSaveSignFragFour);
        btnClearSign = view.findViewById(R.id.tvClearSignFragFour);

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {

            }

            @Override
            public void onClear() {

            }
        });


        btnClearSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signaturePad.clear();
            }
        });


        btnSaveSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RuntimePermissions.checkPermission(getActivity())) {
                    Bitmap signatureBitmap = signaturePad.getSignatureBitmap();
                    Uri tempUri = getImageUri(getActivity(), signatureBitmap);
//
//                    utilities.setImagePath(postImagePath);
//                    timeSheet.setImagePath(postImagePath);

//                    Prefs.putString(PREF_SIGNATURE, postImagePath);

                    Toast.makeText(getActivity(), "Signature Saved", Toast.LENGTH_LONG).show();
//                    if (addJpgSignatureToGallery(signatureBitmap)) {
//                        Toast.makeText(getActivity(), "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getActivity(), "Unable to store the signature", Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    RuntimePermissions.requestPermission(getActivity());
                }
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Frag","4 Pause");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
//            Toast.makeText(getActivity(), "4", Toast.LENGTH_SHORT).show();
//            utilities.setImagePath(postImagePath);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public String getImageRealPath(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }




    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(Environment.getExternalStorageDirectory()+"/SignaturePad"
                    , String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }


    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }


}
