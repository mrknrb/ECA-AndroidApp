package com.example.android.trackmysleepquality;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private Keyboard keyboard;

    private boolean caps = false;

    private void sendMessage(Boolean allapot) {
        Intent intent = new Intent("billentyuzettol");
        intent.putExtra("allapot", allapot);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String utasitas = intent.getStringExtra("utasitas");
            if (utasitas == "click") {
                Integer elozokeycodemod = elozokeycode;
                if (elozokeycode < 48 && elozokeycode > 39) {
                    elozokeycodemod = 40;
                } else if (elozokeycode >47 && elozokeycode <50) {
                    elozokeycodemod = 41;
                }
                karakterberako(Keygenvissza(elozokeycodemod));
            } else {
                Integer keycode = elozokeycode;
                if (utasitas == "balra") {
                    if (elozokeycode == 0) {
                        keycode = 9;
                    } else if (elozokeycode == 10) {
                        keycode = 19;
                    } else if (elozokeycode == 20) {
                        keycode = 29;
                    } else if (elozokeycode == 30) {
                        keycode = 39;
                    } else if (elozokeycode == 40) {
                        keycode = 49;
                    } else {
                        keycode = elozokeycode - 1;
                    }
                } else if (utasitas == "jobbra") {
                    if (elozokeycode == 9) {
                        keycode = 0;
                    } else if (elozokeycode == 19) {
                        keycode = 10;
                    } else if (elozokeycode == 29) {
                        keycode = 20;
                    } else if (elozokeycode == 39) {
                        keycode = 30;
                    } else if (elozokeycode == 49) {
                        keycode = 40;
                    } else {
                        keycode = elozokeycode + 1;
                    }
                } else if (utasitas == "fel") {
                    if (elozokeycode < 10 && elozokeycode > -1) {
                        keycode = elozokeycode + 39;
                    } else {
                        keycode = elozokeycode - 11;
                    }
                } else if (utasitas == "le") {
                    if (elozokeycode < 50 && elozokeycode > 39) {
                        keycode = elozokeycode - 39;
                    } else {
                        keycode = elozokeycode + 11;
                    }
                }
                kijelolesfrissito(keycode);
            }
        }
    };


    @Override
    public void onWindowHidden() {
        super.onWindowHidden();

        sendMessage(false);
    }

    @Override
    public void onWindowShown() {
        super.onWindowShown();

        sendMessage(true);

    }


    @Override
    public void onCreate() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("accessibilitytol"));

        super.onCreate();
    }

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboard = new Keyboard(this, R.xml.keys_layout);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);


        Keyboard.Key key = keyboardView.getKeyboard().getKeys().get(25);
        key.width = key.width - 10;
        key.height = key.height - 10;
        keyboardView.getKeyboard().getKeys().set(25, key);

        keyboardView.invalidateAllKeys();
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

    public Integer elozokeycode = 25;
    public Integer elozoszelesseg = 0;
    public Integer elozomagassag = 0;

    public void kijelolesfrissito(int keycode) {
        Integer elozokeycodemod = elozokeycode;
        if (elozokeycode < 48 && elozokeycode > 39) {
            elozokeycodemod = 40;
        } else if (elozokeycode == 48 || elozokeycode == 49) {
            elozokeycodemod = 41;
        }
        Integer keycodemod = keycode;
        if (keycode < 48 && keycode > 39) {
            keycodemod = 40;
        } else if (keycode == 48 || keycode == 49) {
            keycodemod = 41;
        }

            Keyboard.Key key2 = keyboardView.getKeyboard().getKeys().get(elozokeycodemod);
            key2.width = key2.width + 10;
            key2.height = key2.height + 10;
            keyboardView.getKeyboard().getKeys().set(elozokeycodemod, key2);


        Keyboard.Key key = keyboardView.getKeyboard().getKeys().get(keycodemod);
        key.width = key.width - 10;
        key.height = key.height - 10;
        keyboardView.getKeyboard().getKeys().set(keycodemod, key);


        elozokeycode = keycode;
        elozoszelesseg = key.width;
        elozomagassag = key.height;
        keyboardView.invalidateAllKeys();

    }

    public void karakterberako(int primaryCode) {
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
    public void onKey(int primaryCode, int[] keyCodes) {

        kijelolesfrissito(Keygen(primaryCode));
        karakterberako(primaryCode);

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
        if (primaryCode == 48) {
            keynumber = 0;}
       else if (primaryCode == 49) {
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

    public Integer Keygenvissza(Integer keynum) {
        Integer primaryc = 0;
        if (keynum == 0) {
            primaryc = 48;
        }
       else if (keynum == 1) {
            primaryc = 49;
        } else if (keynum == 2) {
            primaryc = 50;
        } else if (keynum == 3) {
            primaryc = 51;
        } else if (keynum == 4) {
            primaryc = 52;
        } else if (keynum == 5) {
            primaryc = 53;
        } else if (keynum == 6) {
            primaryc = 54;
        } else if (keynum == 7) {
            primaryc = 55;
        } else if (keynum == 8) {
            primaryc = 56;
        } else if (keynum == 9) {
            primaryc = 57;
        } else if (keynum == 10) {
            primaryc = 113;
        } else if (keynum == 11) {
            primaryc = 119;
        } else if (keynum == 12) {
            primaryc = 101;
        } else if (keynum == 13) {
            primaryc = 114;
        } else if (keynum == 14) {
            primaryc = 116;
        } else if (keynum == 15) {
            primaryc = 121;
        } else if (keynum == 16) {
            primaryc = 117;
        } else if (keynum == 17) {
            primaryc = 105;
        } else if (keynum == 18) {
            primaryc = 111;
        } else if (keynum == 19) {
            primaryc = 112;
        } else if (keynum == 20) {
            primaryc = 97;
        } else if (keynum == 21) {
            primaryc = 115;
        } else if (keynum == 22) {
            primaryc = 100;
        } else if (keynum == 23) {
            primaryc = 102;
        } else if (keynum == 24) {
            primaryc = 103;
        } else if (keynum == 25) {
            primaryc = 104;
        } else if (keynum == 26) {
            primaryc = 106;
        } else if (keynum == 27) {
            primaryc = 107;
        } else if (keynum == 28) {
            primaryc = 108;
        } else if (keynum == 29) {
            primaryc = -5;
        } else if (keynum == 30) {
            primaryc = 122;
        } else if (keynum == 31) {
            primaryc = 120;
        } else if (keynum == 32) {
            primaryc = 99;
        } else if (keynum == 33) {
            primaryc = 118;
        } else if (keynum == 34) {
            primaryc = 98;
        } else if (keynum == 35) {
            primaryc = 110;
        } else if (keynum == 36) {
            primaryc = 109;
        } else if (keynum == 37) {
            primaryc = 46;
        } else if (keynum == 38) {
            primaryc = 63;
        } else if (keynum == 39) {
            primaryc = 33;
        } else if (keynum == 40) {
            primaryc = 32;
        } else if (keynum == 41) {
            primaryc = -4;
        }

        return primaryc;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT).show();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}