package com.timkontrakan.component;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

public class ContactAddressMapper {

    private String TAG = getClass().getSimpleName();
    private Activity mActivity;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    public ContactAddressMapper(Activity activity) {
        mActivity = activity;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                && mActivity.checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED)
            mActivity.requestPermissions
                    (new String[]{Manifest.permission.READ_CONTACTS},
                            PERMISSIONS_REQUEST_READ_CONTACTS);
    }

    public void startContactPicker(int pickContactRequest) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);

        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(mActivity)
                .toBundle();

        mActivity.startActivityForResult(intent,
                pickContactRequest,
                bundle);
    }

    public String getAddressFromContact(Uri contactUri) {

        // Obtain a reference to our Content Resolver.
        ContentResolver cr = mActivity.getContentResolver();

        // Obtain a cursor to the appropriate contact at the
        // designated Uri.
        String id;
        String where;
        String[] whereParameters;
        try (Cursor cursor = cr.query(contactUri,
                null, null, null, null)) {

            // Start the cursor at the beginning.
            assert cursor != null;
            cursor.moveToFirst();

            // Obtain the id of the contact.
            id = cursor.getString
                    (cursor.getColumnIndex(ContactsContract.Contacts._ID));
        }

        // Create an SQL "where" clause that will search for the
        // street address of the designated contact Id.
        where = ContactsContract.Data.CONTACT_ID
                + " = ? AND "
                + ContactsContract.Data.MIMETYPE
                + " = ?";
        whereParameters = new String[]{
                id,
                ContactsContract.CommonDataKinds.StructuredPostal
                        .CONTENT_ITEM_TYPE
        };

        // Create a cursor that contains the results of a query for
        // the street address of the designated contact Id.
        try (Cursor addrCursor = cr.query(ContactsContract.Data.CONTENT_URI,
                null,
                where,
                whereParameters,
                null)) {
            // Start the cursor at the beginning.
            assert addrCursor != null;
            addrCursor.moveToFirst();

            // Extract and return the postal address for the contact.
            return addrCursor
                    .getString(addrCursor.getColumnIndexOrThrow
                            (ContactsContract.CommonDataKinds
                                    .StructuredPostal.FORMATTED_ADDRESS));
        } catch (Exception e) {
            return "";
        }
    }

    public void startMapperActivity(String address) {

        // Launch the activity by sending an intent.  Android will
        // choose the right one or let the user choose if more than
        // one Activity can handle it.

        // Create an Intent that will launch the "Maps" app.
        final Intent geoIntent =
                makeMapsIntent(address);

        // Check to see if there's a Map app to handle the "geo"
        // intent.
        if (geoIntent.resolveActivity
                (mActivity.getPackageManager()) != null) {
            mActivity.startActivity(geoIntent);
        } else
        // Start the Browser app instead.
        {
            mActivity.startActivity(makeBrowserIntent(address));
        }
    }

    private Intent makeMapsIntent(String address) {
        // Note the "loose coupling" between the Intent and the app(s)
        // that handle this Intent.
        Log.d(TAG, "makeMapsIntent: address = " + address);
        return new Intent(Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=" + Uri.encode(address)));
    }

    private Intent makeBrowserIntent(String address) {
        // Note the "loose coupling" between the Intent and the app(s)
        // that handle this Intent.
        Log.d(TAG, "makeBrowserIntent: address = " + address);

        // Construct the intent using the encoded address.
        Intent intent =
                new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://maps.google.com/?q="
                                + Uri.encode(address)));

        // WebView Browser Tester on Emulators without Google APIs will
        // not remain open unless the activity is started as a new task.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return intent;
    }

}
