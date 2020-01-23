package com.example.android.trackmysleepquality;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import androidx.annotation.ColorInt;

public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private Keyboard keyboard;

    private boolean caps = false;

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboard = new Keyboard(this, R.xml.keys_layout);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }

    @Override
    public void onPress(int i) {
        //keyboardView.findViewById(R.id.key0);
        //System.out.println("mrk"+keyboardView.getKeyboard().getKeys().get(7));
        //  keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        // keyboard = new Keyboard(this, R.xml.keys_layout);
        // keyboard.getKeys().set()
        //keyboardView.setKeyboard(keyboard);

    }

    @Override
    public void onRelease(int i) {

    }

    public Integer elozokeycode = 0;
    public Integer elozoszelesseg = 0;
    public Integer elozomagassag = 0;

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {


        if (elozokeycode != 0) {
            Keyboard.Key key2 = keyboardView.getKeyboard().getKeys().get(elozokeycode);
            key2.width = key2.width + 10;
            key2.height = key2.height + 10;
            keyboardView.getKeyboard().getKeys().set(elozokeycode, key2);

        }

        Keyboard.Key key = keyboardView.getKeyboard().getKeys().get(Keygen(primaryCode));


        key.width = key.width - 10;
        key.height = key.height - 10;
        keyboardView.getKeyboard().getKeys().set(Keygen(primaryCode), key);


        elozokeycode = Keygen(primaryCode);
        elozoszelesseg = key.width;
        elozomagassag = key.height;
        keyboardView.invalidateAllKeys();
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection != null) {
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:
                    CharSequence selectedText = inputConnection.getSelectedText(0);

                    if (TextUtils.isEmpty(selectedText)) {
                        inputConnection.deleteSurroundingText(1, 0);
                    } else {
                        inputConnection.commitText("", 1);
                    }
                    break;
                case Keyboard.KEYCODE_SHIFT:
                    caps = !caps;
                    keyboard.setShifted(caps);
                    keyboardView.invalidateAllKeys();
                    break;
                case Keyboard.KEYCODE_DONE:
                    inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));

                    break;
                default:
                    char code = (char) primaryCode;
                    if (Character.isLetter(code) && caps) {
                        code = Character.toUpperCase(code);
                    }
                    inputConnection.commitText(String.valueOf(code), 1);


            }
        }

    }


    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    public Integer Keygen(Integer primaryCode) {
        Integer keynumber = 0;
        if (primaryCode == 49) {
            keynumber = 1;
        } else if (primaryCode == 50) {
            keynumber = 2;
        } else if (primaryCode == 51) {
            keynumber = 3;
        } else if (primaryCode == 52) {
            keynumber = 4;
        } else if (primaryCode == 53) {
            keynumber = 5;
        } else if (primaryCode == 54) {
            keynumber = 6;
        } else if (primaryCode == 55) {
            keynumber = 7;
        } else if (primaryCode == 56) {
            keynumber = 8;
        } else if (primaryCode == 57) {
            keynumber = 9;
        } else if (primaryCode == 113) {
            keynumber = 10;
        } else if (primaryCode == 119) {
            keynumber = 11;
        } else if (primaryCode == 101) {
            keynumber = 12;
        } else if (primaryCode == 114) {
            keynumber = 13;
        } else if (primaryCode == 116) {
            keynumber = 14;
        } else if (primaryCode == 121) {
            keynumber = 15;
        } else if (primaryCode == 117) {
            keynumber = 16;
        } else if (primaryCode == 105) {
            keynumber = 17;
        } else if (primaryCode == 111) {
            keynumber = 18;
        } else if (primaryCode == 112) {
            keynumber = 19;
        } else if (primaryCode == 97) {
            keynumber = 20;
        } else if (primaryCode == 115) {
            keynumber = 21;
        } else if (primaryCode == 100) {
            keynumber = 22;
        } else if (primaryCode == 102) {
            keynumber = 23;
        } else if (primaryCode == 103) {
            keynumber = 24;
        } else if (primaryCode == 104) {
            keynumber = 25;
        } else if (primaryCode == 106) {
            keynumber = 26;
        } else if (primaryCode == 107) {
            keynumber = 27;
        } else if (primaryCode == 108) {
            keynumber = 28;
        } else if (primaryCode == -5) {
            keynumber = 29;
        } else if (primaryCode == 122) {
            keynumber = 30;
        } else if (primaryCode == 120) {
            keynumber = 31;
        } else if (primaryCode == 99) {
            keynumber = 32;
        } else if (primaryCode == 118) {
            keynumber = 33;
        } else if (primaryCode == 98) {
            keynumber = 34;
        } else if (primaryCode == 110) {
            keynumber = 35;
        } else if (primaryCode == 109) {
            keynumber = 36;
        } else if (primaryCode == 46) {
            keynumber = 37;
        } else if (primaryCode == 63) {
            keynumber = 38;
        } else if (primaryCode == 33) {
            keynumber = 39;
        } else if (primaryCode == 32) {
            keynumber = 40;
        } else if (primaryCode == -4) {
            keynumber = 41;
        }

        return keynumber;
    }

}