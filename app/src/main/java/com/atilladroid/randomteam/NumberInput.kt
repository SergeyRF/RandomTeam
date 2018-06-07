package com.atilladroid.randomteam

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout

/**
 * Created by sergey on 5/31/18.
 */
class NumberInput : LinearLayout {

    private var et_preview:EditText
    private var button_1:Button
    private var button_2:Button
    private var button_3:Button
    private var button_4:Button
    private var button_5:Button
    private var button_6:Button
    private var button_7:Button
    private var button_8:Button
    private var button_9:Button
    private var button_0:Button

    private var ib_plus:ImageButton
    private var ib_minus:ImageButton
    private var ib_delete:ImageButton
    private var ib_cancel:ImageButton

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.number_input, this, true)

        et_preview = findViewById(R.id.et_number_preview)

        button_0 = findViewById(R.id.button_0)
        button_1 = findViewById(R.id.button_1)
        button_2 = findViewById(R.id.button_2)
        button_3 = findViewById(R.id.button_3)
        button_4 = findViewById(R.id.button_4)
        button_5 = findViewById(R.id.button_5)
        button_6 = findViewById(R.id.button_6)
        button_7 = findViewById(R.id.button_7)
        button_8 = findViewById(R.id.button_8)
        button_9 = findViewById(R.id.button_9)

        ib_plus=findViewById(R.id.ib_plus)
        ib_minus=findViewById(R.id.ib_minus)
        ib_delete=findViewById(R.id.ib_delete)
        ib_cancel=findViewById(R.id.ib_cancel)

        attrs?.let {
            val array = context.obtainStyledAttributes(it, R.styleable.NumberInput)

            //title.setText(array.getText(R.styleable.SwitchSetting_switch_setting_title))
        }




    }
}