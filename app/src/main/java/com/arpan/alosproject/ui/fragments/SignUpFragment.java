package com.arpan.alosproject.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arpan.alosproject.R;
import com.arpan.alosproject.util.Others;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpFragment extends BottomSheetDialogFragment {

    private static final String TAG = "SignUpFragment";
    
    private TextInputEditText nameET;
    private TextInputEditText emailET;
    private TextInputEditText passwordET;
    private TextInputEditText confirmPasswordET;
    private MaterialButton signUpButton;

    SignUpListener listener;

    public SignUpFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        nameET = view.findViewById(R.id.nameSignUp);
        emailET = view.findViewById(R.id.emailSignUp);
        passwordET = view.findViewById(R.id.passwordSignUp);
        confirmPasswordET = view.findViewById(R.id.confirmPassword);
        signUpButton = view.findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(v -> {
            if(validateEmail(emailET.getText().toString())
                    && validateName(nameET.getText().toString())
                    && validatePassword(passwordET.getText().toString())
                    && checkEqual(passwordET.getText().toString(), confirmPasswordET.getText().toString())) {
                listener.signUp(nameET.getText().toString(), emailET.getText().toString(), passwordET.getText().toString());
                dismiss();
            } else {
                Log.d(TAG, "onCreateView: Failed "
                                + emailET.getText().toString()
                                + nameET.getText().toString()
                                + passwordET.getText().toString()
                                + confirmPasswordET.getText().toString()
                        );
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (SignUpListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " implement SignUp Bottom Sheet Listener");
        }
    }

    public interface SignUpListener {
        void signUp(String name, String email, String password);
    }


    private boolean validateName(String name) {

        if(name.isEmpty()) {
            nameET.setError("Name can't be empty");
            Log.d(TAG, "validateName: ");
            return false;
        }
        nameET.setError(null);
        return true;
    }

    private boolean validateEmail(String email) {

        if(email.isEmpty()) {
            emailET.setError("Field can't be empty");
            return false;
        } else if(!Others.EMAIL_PATTERN.matcher(email).matches()) {
            emailET.setError("Please enter a valid email");
            Log.d(TAG, "validateEmail: ");
            return false;
        }
        emailET.setError(null);
        return true;
    }

    private boolean validatePassword(String password) {

        if(password.isEmpty()) {
            passwordET.setError("Field can't be empty");
            return false;
        } else if(!Others.PASSWORD_PATTERN.matcher(password).matches()) {
            passwordET.setError("a-z or A-Z and min 6 char");
            Log.d(TAG, "validatePassword: ");
            return false;
        }
        passwordET.setError(null);
        return true;
    }

    private boolean checkEqual(String val1, String val2) {
        return val1.equals(val2);
    }
}
