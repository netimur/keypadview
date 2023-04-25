package com.netimur.keypadview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.netimur.keypadview.callbacks.BackspaceButtonClickCallback
import com.netimur.keypadview.callbacks.KeypadCallback
import com.netimur.keypadview.callbacks.NumberButtonClickCallback
import com.netimur.keypadview.callbacks.OptionalButtonClickCallback

class KeypadView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) :
    ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val numberButtons: List<FloatingActionButton>
    private val keypadButtons: List<FloatingActionButton>
    private val optionalButton: FloatingActionButton
    private val backspaceButton: FloatingActionButton
    private val oneButton: FloatingActionButton
    private val twoButton: FloatingActionButton
    private val threeButton: FloatingActionButton
    private val fourButton: FloatingActionButton
    private val fiveButton: FloatingActionButton
    private val sixButton: FloatingActionButton
    private val sevenButton: FloatingActionButton
    private val eightButton: FloatingActionButton
    private val nineButton: FloatingActionButton
    private val zeroButton: FloatingActionButton


    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        R.style.KeypadButtonsStyle
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.keypadStyle)
    constructor(context: Context) : this(context, null)

    init {
        val layoutInflater = LayoutInflater.from(context)
        layoutInflater.inflate(R.layout.keypad, this, true)
        zeroButton = findViewById<FloatingActionButton>(R.id.zero)
        oneButton = findViewById<FloatingActionButton>(R.id.one)
        twoButton = findViewById<FloatingActionButton>(R.id.two)
        threeButton = findViewById<FloatingActionButton>(R.id.three)
        fourButton = findViewById<FloatingActionButton>(R.id.four)
        fiveButton = findViewById<FloatingActionButton>(R.id.five)
        sixButton = findViewById<FloatingActionButton>(R.id.six)
        sevenButton = findViewById<FloatingActionButton>(R.id.seven)
        eightButton = findViewById<FloatingActionButton>(R.id.eight)
        nineButton = findViewById<FloatingActionButton>(R.id.nine)
        backspaceButton = findViewById<FloatingActionButton>(R.id.backspc)
        optionalButton = findViewById<FloatingActionButton>(R.id.fingerprint)
        numberButtons = listOf(
            zeroButton,
            oneButton,
            twoButton,
            threeButton,
            fourButton,
            fiveButton,
            sixButton,
            sevenButton,
            eightButton,
            nineButton
        )
        keypadButtons = listOf(
            zeroButton,
            oneButton,
            twoButton,
            threeButton,
            fourButton,
            fiveButton,
            sixButton,
            sevenButton,
            eightButton,
            nineButton,
            backspaceButton,
            optionalButton
        )
        initiateAttributes(attrs, defStyleAttr, defStyleRes)
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initiateAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        if (attrs == null) {
            return
        }
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.KeypadView, defStyleAttr, defStyleRes)

        val backgroundColor =
            typedArray.getColor(R.styleable.KeypadView_keypadBackgroundTint, Color.WHITE)
        this.setBackgroundColor(backgroundColor)

        val buttonColor =
            typedArray.getColor(R.styleable.KeypadView_keypadButtonsColor, Color.WHITE)
        setButtonsColor(buttonColor)

        val iconColor =
            typedArray.getColor(R.styleable.KeypadView_keypadIconColor, Color.rgb(0, 136, 96))
        setKeypadButtonsIconColor(iconColor)


        val optionalButtonVisibility =
            typedArray.getInt(R.styleable.KeypadView_keypadOptionalButtonVisibility, View.INVISIBLE)
        setOptionalButtonVisibility(optionalButtonVisibility)

        val optionalButtonIcon = typedArray.getResourceId(
            R.styleable.KeypadView_keypadOptionalButtonIcon,
            R.drawable.fingerprint
        )
        setOptionalButtonIcon(optionalButtonIcon)

        val optionalButtonIconColor =
            typedArray.getColor(
                R.styleable.KeypadView_keypadOptionalButtonIconColor,
                iconColor
            )
        setOptionalButtonIconColor(optionalButtonIconColor)

        val optionalButtonColor =
            typedArray.getColor(R.styleable.KeypadView_keypadOptionalButtonColor, Color.WHITE)
        setOptionalButtonColor(optionalButtonColor)

        val backspaceButtonIcon = typedArray.getResourceId(
            R.styleable.KeypadView_keypadBackspaceButtonIcon,
            R.drawable.backspace
        )
        setBackspaceButtonIcon(backspaceButtonIcon)

        val backspaceButtonIconColor =
            typedArray.getColor(
                R.styleable.KeypadView_keypadBackspaceButtonIconColor,
                Color.rgb(0, 136, 96)
            )
        setBackspaceButtonIconColor(backspaceButtonIconColor)

        val backspaceButtonColor =
            typedArray.getColor(R.styleable.KeypadView_keypadBackspaceButtonColor, Color.WHITE)
        setBackspaceButtonColor(backspaceButtonColor)

        val numberButtonsIconColor = typedArray.getColor(
            R.styleable.KeypadView_keypadNumberButtonsIconColor,
            Color.rgb(0, 136, 96)
        )
        setNumberButtonsIconColor(numberButtonsIconColor)

        val buttonsSize = typedArray.getDimension(R.styleable.KeypadView_keypadButtonsSize, 120F)
        for (i in keypadButtons.indices) {
            keypadButtons[i].customSize = buttonsSize.toInt()
        }
        typedArray.recycle()
    }

    fun setButtonsColor(color: Int) {
        for (i in keypadButtons.indices) {
            keypadButtons[i].backgroundTintList = ColorStateList.valueOf(color)
        }
    }

    fun setOptionalButtonIcon(resourceId: Int) {
        try {
            optionalButton.setImageDrawable(context.getDrawable(resourceId))
        } catch (e: Exception) {
            optionalButton.setImageDrawable(context.getDrawable(R.drawable.fingerprint))
        }
    }

    fun setOptionalButtonVisibility(viewVisibility: Int) {
        optionalButton.visibility = when (viewVisibility) {
            0 -> View.VISIBLE
            1 -> View.INVISIBLE
            2 -> View.GONE
            else -> {
                View.VISIBLE
            }
        }
    }

    fun setOptionalButtonIconColor(color: Int) {
        optionalButton.imageTintList = ColorStateList.valueOf(color)
    }

    fun setOptionalButtonColor(color: Int) {
        optionalButton.backgroundTintList = ColorStateList.valueOf(color)
    }

    fun setKeypadButtonsIconColor(color: Int) {
        for (i in keypadButtons.indices) {
            keypadButtons[i].imageTintList = ColorStateList.valueOf(color)
        }
    }

    fun setNumberButtonsIconColor(color: Int) {
        for (i in numberButtons.indices) {
            numberButtons[i].imageTintList = ColorStateList.valueOf(color)
        }
    }

    fun setBackspaceButtonIcon(resourceId: Int) {
        try {
            backspaceButton.setImageDrawable(context.getDrawable(resourceId))
        } catch (e: Exception) {
            backspaceButton.setImageDrawable(context.getDrawable(R.drawable.fingerprint))
        }
    }

    fun setBackspaceButtonIconColor(color: Int) {
        backspaceButton.imageTintList = ColorStateList.valueOf(color)
    }

    fun setBackspaceButtonColor(color: Int) {
        backspaceButton.backgroundTintList = ColorStateList.valueOf(color)
    }


    fun setNumberButtonClickListenerCallback(callback: NumberButtonClickCallback) {
        for (i in numberButtons.indices) {
            numberButtons[i].setOnClickListener {
                callback.registerClick(i)
            }
        }
    }

    fun setOptionalButtonClickCallback(callback: OptionalButtonClickCallback) {
        optionalButton.setOnClickListener {
            callback.registerOptionalButtonClick()
        }
    }

    fun setBackspaceButtonClickCallback(callback: BackspaceButtonClickCallback) {
        backspaceButton.setOnClickListener {
            callback.registerBackspaceButtonClick()
        }
    }

    fun setKeypadClickCallback(callback: KeypadCallback) {
        setNumberButtonClickListenerCallback(callback)
        setOptionalButtonClickCallback(callback)
        setBackspaceButtonClickCallback(callback)
    }
}