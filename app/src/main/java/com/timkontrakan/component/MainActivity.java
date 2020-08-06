package com.timkontrakan.component;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.timkontrakan.component.databinding.ActivityMainBinding;

public class MainActivity extends LifecycleLoggingActivity {

    private ActivityMainBinding binding;
    private String TAG = getClass().getSimpleName();
    private static final int PICK_CONTACT_REQUEST = 0;
    private ImageButton mAddButton;
    private ContactAddressMapper mContactAddressMapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAddButton = (ImageButton) findViewById(R.id.addButton);
        mContactAddressMapper = new ContactAddressMapper(this);

    }

    public void findAddress(View view) {
        animatedAddFab(false);
        mContactAddressMapper.startContactPicker(PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK
                && requestCode == PICK_CONTACT_REQUEST)
            displayMap(data);
    }

    private void animatedAddFab(boolean b) {
        mAddButton.setImageResource(
                b ? R.drawable.icon_morph_reverse : R.drawable.icon_morph);
        ((Animatable) mAddButton.getDrawable()).start();
    }

    private void displayMap(final Intent data) {

        final Runnable getAndDisplayAddressFromContact = () -> {
            String address =
                    mContactAddressMapper.getAddressFromContact(data.getData());

            // Launch the mapper Activity in the UI thread.
            runOnUiThread(() -> {
                if (!TextUtils.isEmpty(address.trim()))
                    mContactAddressMapper.startMapperActivity(address);
                else
                    Toast.makeText(this,
                            "No address found.",
                            Toast.LENGTH_SHORT).show();
            });
        };
        new Thread(getAndDisplayAddressFromContact).start();
    }
}