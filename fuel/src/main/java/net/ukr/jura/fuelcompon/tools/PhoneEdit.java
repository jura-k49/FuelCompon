package net.ukr.jura.fuelcompon.tools;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.ukr.jura.fuelcompon.R;

public class PhoneEdit extends RelativeLayout {
    private static final String hint = "";
    private static final int phoneNumberLength = 12;
    private final String patternPhone = "^[0-9]{9}";

    private EditText input;
    private View bottomDrawable;
    private TextView phoneHint;

    private String stLast = "";
    private String stInEdit = "";
    private String stReal = "";

    private int textSize;
    private int textColor;
    private int hintColor;
    private boolean hasError = false;
    private View.OnFocusChangeListener onFocusChangeListener;
    private TextWatcher phoneFormatter;
    private SimpleTextWatcher simpleTextWatcher;
    private int currentSelector;

    public PhoneEdit(Context context) {
        super(context);
        init(context, null);
    }

    public PhoneEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PhoneEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.phone_number_layout, this);

        input = (EditText) findViewById(R.id.edit_field);
        bottomDrawable = findViewById(R.id.bottom_drawable);
        phoneHint = (TextView) findViewById(R.id.phone_hint);

//        input.setInputType(InputType.TYPE_CLASS_NUMBER);
//        InputFilter[] filters = new InputFilter[1];
//        filters[0] = new InputFilter.LengthFilter(phoneNumberLength);
//        input.setFilters(filters);


        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EditTextView,
                0, 0);

        try {
            textSize = a.getDimensionPixelSize(R.styleable.EditTextView_textSize, 18);
            textColor = a.getColor(R.styleable.EditTextView_textColor, ContextCompat.getColor(context, R.color.black));
            hintColor = a.getColor(R.styleable.EditTextView_hintTextColor, ContextCompat.getColor(context, R.color.silver));
        } finally {
            a.recycle();
        }

        if (hint != null) {
            input.setHint(hint);
        }
        if (textSize > 0) {
            input.setTextSize(textSize);
        }
        if (textColor != 0) {
            input.setTextColor(textColor);
        }
        if (hintColor != 0) {
            input.setHintTextColor(hintColor);
        }

        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (bottomDrawable != null) {
                    bottomDrawable.setSelected(hasFocus);
                }
                if (hasFocus == false) {
                    validatePhoneNumber();
                }
                if(onFocusChangeListener != null) {
                    onFocusChangeListener.onFocusChange(v, hasFocus);
                }
            }
        });

//        input.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                stReal = replaceAll(s.toString());
//                validatePhoneNumber();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(simpleTextWatcher != null) {
//                    simpleTextWatcher.onTextChanged(input.getText().toString());
//                }
//            }
//        });

        phoneFormatter = new DefaultTextFormatter();
        input.addTextChangedListener(phoneFormatter);

        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setKeyListener(DigitsKeyListener.getInstance("0123456789 -"));
    }

    public void setTextWatcher(SimpleTextWatcher simpleTextWatcher) {
        this.simpleTextWatcher = simpleTextWatcher;
    }

    public EditText getInputField() {
        return input;
    }

    public interface SimpleTextWatcher {
        void onTextChanged(String newText);
    }

//    public void openKeyboard(){
//        input.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                input.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
//                input.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
//            }
//        }, 100);
//    }
    private String replaceAll(String s) {
        return s.replaceAll("[\\-\\+\\.\\^:,\\(\\)\\* /N]*", "");
    }

    public void setPhoneHintColor(int color) {
        phoneHint.setTextColor(color);
    }

    private void maskPhone(String stBuf) {
        stBuf = replaceAll(stBuf);
        stInEdit = "";
        int ln = stBuf.length();
        if (ln > 0) {
            if (ln > 2) {
                stInEdit = stInEdit + stBuf.substring(0, 2) + " ";
                stBuf = stBuf.substring(2);
                ln = stBuf.length();
                if (ln > 3) {
                    stInEdit = stInEdit + stBuf.substring(0, 3) + "-";
                    stBuf = stBuf.substring(3);
                    ln = stBuf.length();
                    if (ln > 2) {
                        stInEdit = stInEdit + stBuf.substring(0, 2) + "-" + stBuf.substring(2);
                    } else {
                        stInEdit += stBuf;
                    }
                } else {
                    stInEdit += stBuf;
                }
            } else {
                stInEdit += stBuf;
            }
        }
    }

    @Override
    public void setOnFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        if (onFocusChangeListener != null) {
            this.onFocusChangeListener = onFocusChangeListener;
        }
    }

    public void cursorToEnd() {
        input.setSelection(input.length());
    }

    private void validatePhoneNumber() {
        Log.d("QWERT","validatePhoneNumber stReal="+stReal);
        if (!stReal.matches(patternPhone)) {
            hasError = true;
            // setFieldHasError(hasError);
        } else {
            hasError = false;
            // setFieldHasError(hasError);
        }
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setText(String s) {
        input.setText(s);
    }

    public String getText() {
        return input.getText().toString();
    }

    public int getTextSize() {
        return textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        input.setTextColor(textColor);
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        input.setTextSize(textSize);
    }

    public void setSelector(int selectorId) {
        currentSelector = selectorId;
        bottomDrawable.setBackgroundResource(currentSelector);
    }

    public void setFieldHasError(boolean hasError) {
        if (hasError) {
            bottomDrawable.setBackgroundResource(R.drawable.selector_edit_text_unfocus);
        } else {
            bottomDrawable.setBackgroundResource(currentSelector);
        }
    }

    public void setFieldWasValidated(boolean validated) {
        if (validated) {
            bottomDrawable.setBackgroundResource(R.drawable.selector_edit_text_focus);
        } else {
            bottomDrawable.setBackgroundResource(currentSelector);
        }
    }

    public void setCustomPhoneFormater(@NonNull TextWatcher customTextFormater) {
        if(phoneFormatter != null) {
            input.removeTextChangedListener(phoneFormatter);
        }
        input.addTextChangedListener(customTextFormater);
    }

    private class DefaultTextFormatter implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("QWERT","onTextChanged S="+s);
//            input.removeTextChangedListener(this);

            String stBuf = replaceAll(s.toString());
            Log.d("QWERT","onTextChanged stBuf="+stBuf+"<< stReal="+stReal+"<< stInEdit="+stInEdit+"<< stLast="+stLast);
            if (!stBuf.equals(stReal)) {
                maskPhone(s.toString());
            }
            Log.d("QWERT","onTextChanged 1111 stBuf="+stBuf+"<< stReal="+stReal+"<< stInEdit="+stInEdit+"<< stLast="+stLast);
            stReal = replaceAll(s.toString());
            Log.d("QWERT","onTextChanged 2222 stBuf="+stBuf+"<< stReal="+stReal+"<< stInEdit="+stInEdit+"<< stLast="+stLast);
            if (stInEdit.length() == stLast.length()) {
                if(start > 0) {
                    maskPhone(String.format("%s%s", stInEdit.substring(0, start - 1), stInEdit.substring(start)));
                }
                input.setText(stInEdit);
            } else {
                input.setText(stInEdit);
            }
            int sel = start+ stInEdit.length() - s.length();
            if (stInEdit.length() > stLast.length()) {
                input.setSelection(sel + 1);
            } else if(stInEdit.length() == stLast.length()){
                input.setSelection(start);
            }  else{
                if (sel < 0) {
                    sel = 0;
                }
                input.setSelection(sel);
            }

            stLast = String.valueOf(input.getText());
            input.addTextChangedListener(this);
        }

        @Override
        public void afterTextChanged (Editable s){

        }
    }
}
