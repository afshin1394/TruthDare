package com.afshin.truthordare.Utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;

import com.afshin.truthordare.Challenger;
import com.afshin.truthordare.R;

public class EditTextUtils {


    @BindingAdapter({"app:binding"})
    public static void bindEditText(final EditText view, final Challenger bindableString) {
        // We use tag to ensure that we aren't adding multiple TextWatcher for same EditText. This ensures that
        // EditText has only one TextWatcher
        if (view.getTag(R.id.dataBinding) == null) {
            view.setTag(R.id.dataBinding, true);
            view.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    bindableString.setName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        // Checking if the value has really changed. This prevents problems with the position of cursor
        // in the EditText
        String newValue = bindableString.getName();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }

    }
}
