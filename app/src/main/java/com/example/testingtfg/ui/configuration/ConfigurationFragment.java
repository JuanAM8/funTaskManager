package com.example.testingtfg.ui.configuration;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.example.testingtfg.BaseFragment;
import com.example.testingtfg.R;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

/*Clase que gestiona el Fragment de configuración*/
public class ConfigurationFragment extends BaseFragment {

    //region Mensajes
    private final String BG_MESSAGE = "Color del fondo de la app";
    private final String COLOR_MESSAGE = "Color de los botones, la barra superior y las ventanas";
    private final String COLOR2_MESSAGE = "Color de la barra inferior, los botones de las ventanas y borde de los botones";
    private final String TEXT_MESSAGE = "Color de textos sobre el fondo de la app";
    private final String TEXT2_MESSAGE = "Color de textos en los botones y en las ventanas";
    private final String GRADIENT_BG_MESSAGE = "Permite establecer un color que no sea plano al fondo";
    private final String GRADIENT_COLOR_MESSAGE = "Permite establecer un color que no sea plano como color principal";
    private final String GRADIENT_COLOR2_MESSAGE = "Permite establecer un color que no sea plano como color secundario";
    private final String IMAGE_BACKGROUND_MESSAGE = "Establece una imagen como fondo de pantalla";
    private final String TEXT_SIZE_MESSAGE = "Cambia el tamaño de los textos";
    private final String SHADOWS_MESSAGE = "Si está activo, los textos tendrán relieve en forma de sombra";
    private final String BORDERS_MESSAGE = "Si está activo, los botones tendrán borde de distinto color";
    //endregion
    //region Botones, Switches y Spinners
    private Button buttonBackgroundColor;
    private Button buttonMainColor;
    private Button buttonTextMainColor;
    private Button buttonSecondaryColor;
    private Button buttonTextSecondaryColor;
    private Button buttonGradient1BG;
    private Button buttonGradient2BG;
    private Button buttonGradient3BG;
    private Button buttonGradient1MainColor;
    private Button buttonGradient2MainColor;
    private Button buttonGradient3MainColor;
    private Button buttonGradient1SecondaryColor;
    private Button buttonGradient2SecondaryColor;
    private Button buttonGradient3SecondaryColor;
    private Button buttonDecreaseTextSize;
    private Button buttonIncreaseTextSize;
    private Switch gradientBGSwitch;
    private Switch gradientMainColorSwitch;
    private Switch gradientSecondaryColorSwitch;
    private Switch shadowsSwitch;
    private Switch bordersSwitch;
    private Switch imageBGSwitch;
    private Spinner imageBGSpinner;
    //endregion
    //region Drawables y Textos
    GradientDrawable drawableBackground;
    GradientDrawable drawableColor;
    GradientDrawable drawableText;
    GradientDrawable drawableColor2;
    GradientDrawable drawableText2;
    GradientDrawable drawableGradient1BG;
    GradientDrawable drawableGradient2BG;
    GradientDrawable drawableGradient3BG;
    GradientDrawable drawableGradient1MainColor;
    GradientDrawable drawableGradient2MainColor;
    GradientDrawable drawableGradient3MainColor;
    GradientDrawable drawableGradient1SecondaryColor;
    GradientDrawable drawableGradient2SecondaryColor;
    GradientDrawable drawableGradient3SecondaryColor;
    VectorDrawable drawableDecreaseTextSize;
    VectorDrawable drawableIncreaseTextSize;
    GradientDrawable drawableBackgroundWithGradient;
    GradientDrawable drawableColorWithGradient;
    GradientDrawable drawableColor2WithGradient;
    GradientDrawable drawableImageBG;
    int colorBG1;
    int colorBG2;
    int colorBG3;
    int colorMain1;
    int colorMain2;
    int colorMain3;
    int colorSecondary1;
    int colorSecondary2;
    int colorSecondary3;
    TextView bgText;
    TextView colorText;
    TextView color2Text;
    TextView textText;
    TextView text2Text;
    TextView shadowsText;
    TextView bordersText;
    TextView gradientBGText;
    TextView gradientColor1Text;
    TextView gradientColor2Text;
    TextView textSizeText;
    TextView textImageBG;
    //endregion

    private View root;
    private ConfigurationViewModel gamesViewModel;

    //region Métodos Visuales
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gamesViewModel =
                ViewModelProviders.of(this).get(ConfigurationViewModel.class);
        root = inflater.inflate(R.layout.fragment_configuration, container, false);
        loadColors(root);
        initViews(root);
        return root;
    }

    //Inicializa todas las Views del Fragment
    private void initViews(View v){
        bgText = (TextView) v.findViewById(R.id.bg_text);
        colorText = (TextView) v.findViewById(R.id.color_main_text);
        color2Text = (TextView) v.findViewById(R.id.color_secondary_text);
        textText = (TextView) v.findViewById(R.id.text_main_text);
        text2Text = (TextView) v.findViewById(R.id.text_secondary_text);
        shadowsText = (TextView) v.findViewById(R.id.text_switch_shadows);
        bordersText = (TextView) v.findViewById(R.id.text_switch_borders);
        gradientBGText = (TextView) v.findViewById(R.id.text_gradient_text);
        gradientColor1Text = (TextView) v.findViewById(R.id.text_gradient2_text);
        gradientColor2Text = (TextView) v.findViewById(R.id.text_gradient3_text);
        textSizeText = (TextView) v.findViewById(R.id.text_size_text);
        textImageBG = (TextView) v.findViewById(R.id.text_image_bg_text);
        bgText.setTextColor(defaultTextMainColor);
        colorText.setTextColor(defaultTextMainColor);
        color2Text.setTextColor(defaultTextMainColor);
        textText.setTextColor(defaultTextMainColor);
        text2Text.setTextColor(defaultTextMainColor);
        shadowsText.setTextColor(defaultTextMainColor);
        bordersText.setTextColor(defaultTextMainColor);
        gradientBGText.setTextColor(defaultTextMainColor);
        gradientColor1Text.setTextColor(defaultTextMainColor);
        gradientColor2Text.setTextColor(defaultTextMainColor);
        textSizeText.setTextColor(defaultTextMainColor);
        textImageBG.setTextColor(defaultTextMainColor);
        onSwitchShadowsChanged();
        initTextSizes();
        buttonBackgroundColor = (Button) v.findViewById(R.id.button_bg_color);
        buttonMainColor = (Button) v.findViewById(R.id.button_main_color);
        buttonTextMainColor = (Button) v.findViewById(R.id.button_text_main_color);
        buttonSecondaryColor = (Button) v.findViewById(R.id.button_secondary_color);
        buttonTextSecondaryColor = (Button) v.findViewById(R.id.button_text_secondary_color);
        buttonGradient1BG = (Button) v.findViewById(R.id.button_bg_color_gradient_1);
        buttonGradient2BG = (Button) v.findViewById(R.id.button_bg_color_gradient_2);
        buttonGradient3BG = (Button) v.findViewById(R.id.button_bg_color_gradient_3);
        buttonGradient1MainColor = (Button) v.findViewById(R.id.button_main_color_gradient_1);
        buttonGradient2MainColor = (Button) v.findViewById(R.id.button_main_color_gradient_2);
        buttonGradient3MainColor = (Button) v.findViewById(R.id.button_main_color_gradient_3);
        buttonGradient1SecondaryColor = (Button) v.findViewById(R.id.button_secondary_color_gradient_1);
        buttonGradient2SecondaryColor = (Button) v.findViewById(R.id.button_secondary_color_gradient_2);
        buttonGradient3SecondaryColor = (Button) v.findViewById(R.id.button_secondary_color_gradient_3);
        buttonDecreaseTextSize = (Button) v.findViewById(R.id.button_decrease_text_size);
        buttonIncreaseTextSize = (Button) v.findViewById(R.id.button_increase_text_size);
        gradientBGSwitch = (Switch) v.findViewById(R.id.switch_gradient);
        gradientMainColorSwitch = (Switch) v.findViewById(R.id.switch_gradient2);
        gradientSecondaryColorSwitch = (Switch) v.findViewById(R.id.switch_gradient3);
        bordersSwitch = (Switch) v.findViewById(R.id.switch_borders);
        shadowsSwitch = (Switch) v.findViewById(R.id.switch_shadows);
        imageBGSwitch = (Switch) v.findViewById(R.id.switch_image_bg);
        imageBGSpinner = (Spinner) v.findViewById(R.id.spinner_background);
        colorBG1 = getMainActivity().getStylesManager().getBackgroundGradientColor1();
        colorBG2 = getMainActivity().getStylesManager().getBackgroundGradientColor2();
        colorBG3 = getMainActivity().getStylesManager().getBackgroundGradientColor3();
        colorMain1 = getMainActivity().getStylesManager().getMainGradientColor1();
        colorMain2 = getMainActivity().getStylesManager().getMainGradientColor2();
        colorMain3 = getMainActivity().getStylesManager().getMainGradientColor3();
        colorSecondary1 = getMainActivity().getStylesManager().getSecondaryGradientColor1();
        colorSecondary2 = getMainActivity().getStylesManager().getSecondaryGradientColor2();
        colorSecondary3 = getMainActivity().getStylesManager().getSecondaryGradientColor3();
        drawableBackground = (GradientDrawable) buttonBackgroundColor.getBackground();
        drawableColor = (GradientDrawable) buttonMainColor.getBackground();
        drawableText = (GradientDrawable) buttonTextMainColor.getBackground();
        drawableColor2 = (GradientDrawable) buttonSecondaryColor.getBackground();
        drawableText2 = (GradientDrawable) buttonTextSecondaryColor.getBackground();
        drawableGradient1BG = (GradientDrawable) buttonGradient1BG.getBackground();
        drawableGradient2BG = (GradientDrawable) buttonGradient2BG.getBackground();
        drawableGradient3BG = (GradientDrawable) buttonGradient3BG.getBackground();
        drawableGradient1MainColor = (GradientDrawable) buttonGradient1MainColor.getBackground();
        drawableGradient2MainColor = (GradientDrawable) buttonGradient2MainColor.getBackground();
        drawableGradient3MainColor = (GradientDrawable) buttonGradient3MainColor.getBackground();
        drawableGradient1SecondaryColor = (GradientDrawable) buttonGradient1SecondaryColor.getBackground();
        drawableGradient2SecondaryColor = (GradientDrawable) buttonGradient2SecondaryColor.getBackground();
        drawableGradient3SecondaryColor = (GradientDrawable) buttonGradient3SecondaryColor.getBackground();
        drawableImageBG = (GradientDrawable) imageBGSpinner.getBackground();
        if (gradientMainColorActive){
            drawableImageBG.setColors(new int[]{defaultMainGradientColor1, defaultMainGradientColor2,
                    defaultMainGradientColor3});
        } else {
            drawableImageBG.setColor(defaultMainColor);
        }
        if (bordersActive){
            drawableImageBG.setStroke(defaultButtonRadius, defaultBackgroundColor);
        } else {
            drawableImageBG.setStroke(defaultButtonRadius, defaultSecondaryColor);
        }
        drawableDecreaseTextSize = (VectorDrawable) buttonDecreaseTextSize.getBackground();
        drawableIncreaseTextSize = (VectorDrawable) buttonIncreaseTextSize.getBackground();
        drawableBackground.setColor(defaultBackgroundColor);
        drawableColor.setColor(defaultMainColor);
        drawableText.setColor(defaultTextMainColor);
        drawableColor2.setColor(defaultSecondaryColor);
        drawableText2.setColor(defaultTextSecondaryColor);
        drawableGradient1BG.setColor(defaultBackgroundGradientColor1);
        drawableGradient2BG.setColor(defaultBackgroundGradientColor2);
        drawableGradient3BG.setColor(defaultBackgroundGradientColor3);
        drawableGradient1MainColor.setColor(defaultMainGradientColor1);
        drawableGradient2MainColor.setColor(defaultMainGradientColor2);
        drawableGradient3MainColor.setColor(defaultMainGradientColor3);
        drawableGradient1SecondaryColor.setColor(defaultSecondaryGradientColor1);
        drawableGradient2SecondaryColor.setColor(defaultSecondaryGradientColor2);
        drawableGradient3SecondaryColor.setColor(defaultSecondaryGradientColor3);
        drawableImageBG.setColor(defaultMainColor);
        drawableDecreaseTextSize.setTint(defaultMainColor);
        drawableIncreaseTextSize.setTint(defaultMainColor);
        drawableBackgroundWithGradient = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{});
        drawableBackgroundWithGradient.setCornerRadius(0f);
        drawableColorWithGradient = (GradientDrawable)getMainActivity().getDrawable(R.drawable.button3_shape);
        drawableColor2WithGradient = (GradientDrawable)getMainActivity().getDrawable(R.drawable.button3_shape);
        changeBackground(gradientBGActive, imageBGActive);
        onSwitchBordersChanged();
        shadowsSwitch.setHighlightColor(defaultMainColor);
        bordersSwitch.setHighlightColor(defaultMainColor);
        gradientBGSwitch.setHighlightColor(defaultMainColor);
        gradientMainColorSwitch.setHighlightColor(defaultMainColor);
        gradientSecondaryColorSwitch.setHighlightColor(defaultMainColor);
        imageBGSwitch.setHighlightColor(defaultMainColor);
        //OnClick Methods
        //Texts
        bgText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getMainActivity().toastMessage(BG_MESSAGE);
            }
        });
        colorText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getMainActivity().toastMessage(COLOR_MESSAGE);
            }
        });
        color2Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getMainActivity().toastMessage(COLOR2_MESSAGE);
            }
        });
        textText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getMainActivity().toastMessage(TEXT_MESSAGE);
            }
        });
        text2Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getMainActivity().toastMessage(TEXT2_MESSAGE);
            }
        });
        shadowsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getMainActivity().toastMessage(SHADOWS_MESSAGE);
            }
        });
        bordersText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getMainActivity().toastMessage(BORDERS_MESSAGE);
            }
        });
        gradientBGText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getMainActivity().toastMessage(GRADIENT_BG_MESSAGE);}
        });
        gradientColor1Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getMainActivity().toastMessage(GRADIENT_COLOR_MESSAGE);}
        });
        gradientColor2Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getMainActivity().toastMessage(GRADIENT_COLOR2_MESSAGE);}
        });
        textSizeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getMainActivity().toastMessage(TEXT_SIZE_MESSAGE);}
        });
        textImageBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { getMainActivity().toastMessage(IMAGE_BACKGROUND_MESSAGE);}
        });
        //Buttons
        buttonBackgroundColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickButtonBackground();
            }
        });
        buttonMainColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonColor();
            }
        });
        buttonTextMainColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickButtonText();
            }
        });
        buttonSecondaryColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickButtonColor2();
            }
        });
        buttonTextSecondaryColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickButtonText2();
            }
        });
        buttonGradient1BG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickGradientBG(0);
            }
        });
        buttonGradient2BG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickGradientBG(1);
            }
        });
        buttonGradient3BG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickGradientBG(2);
            }
        });
        buttonGradient1MainColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickGradientColor1(0);
            }
        });
        buttonGradient2MainColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickGradientColor1(1);
            }
        });
        buttonGradient3MainColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickGradientColor1(2);
            }
        });
        buttonGradient1SecondaryColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickGradientColor2(0);
            }
        });
        buttonGradient2SecondaryColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickGradientColor2(1);
            }
        });
        buttonGradient3SecondaryColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickGradientColor2(2);
            }
        });
        buttonDecreaseTextSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickTextSize(false);
            }
        });
        buttonIncreaseTextSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickTextSize(true);
            }
        });
        showButtonsBackgroundGradient(gradientBGActive);
        showButtonsMainColorGradient(gradientMainColorActive);
        showButtonsSecondaryColorGradient(gradientSecondaryColorActive);
        if (imageBGActive){
            imageBGSpinner.setVisibility(View.VISIBLE);
        } else {
            imageBGSpinner.setVisibility(View.GONE);
        }
        initSpinner();
        imageBGSwitch.setChecked(imageBGActive);
        gradientBGSwitch.setChecked(gradientBGActive);
        gradientMainColorSwitch.setChecked(gradientMainColorActive);
        gradientSecondaryColorSwitch.setChecked(gradientSecondaryColorActive);
        shadowsSwitch.setChecked(shadowsActive);
        bordersSwitch.setChecked(bordersActive);
        imageBGSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                imageBGActive = isChecked;
                getMainActivity().getStylesManager().setImageBGActive(isChecked);
                if (gradientBGActive && isChecked){
                    gradientBGSwitch.setChecked(false);
                    showButtonsBackgroundGradient(false);
                    gradientBGActive = false;
                    getMainActivity().getStylesManager().setGradientBGActive(false);
                }
                changeBackground(false, isChecked);
                if (!isChecked){
                    imageBGSpinner.setVisibility(View.GONE);
                    drawableBackground.setColor(defaultBackgroundColor);
                    buttonBackgroundColor.setBackground(drawableBackground);
                } else {
                    imageBGSpinner.setVisibility(View.VISIBLE);
                }
            }
        });
        gradientBGSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showButtonsBackgroundGradient(isChecked);
                gradientBGActive = isChecked;
                getMainActivity().getStylesManager().setGradientBGActive(isChecked);
                if (imageBGActive && isChecked){
                    imageBGSwitch.setChecked(false);
                    imageBGActive = false;
                    getMainActivity().getStylesManager().setImageBGActive(false);
                }
                changeBackground(isChecked, false);
                if (!isChecked){
                    drawableBackground.setColor(defaultBackgroundColor);
                    buttonBackgroundColor.setBackground(drawableBackground);
                }
            }
        });
        gradientMainColorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showButtonsMainColorGradient(isChecked);
                gradientMainColorActive = isChecked;
                getMainActivity().getStylesManager().setGradientMainColorActive(isChecked);
                if (!isChecked){
                    drawableColor.setColor(defaultMainColor);
                    buttonMainColor.setBackground(drawableColor);
                }
            }
        });
        gradientSecondaryColorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showButtonsSecondaryColorGradient(isChecked);
                gradientSecondaryColorActive = isChecked;
                getMainActivity().getStylesManager().setGradientSecondaryColorActive(isChecked);
                if (!isChecked){
                    drawableColor2.setColor(defaultSecondaryColor);
                    buttonSecondaryColor.setBackground(drawableColor2);
                }
            }
        });
        shadowsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    getMainActivity().getStylesManager().setShadowsActive(true);
                } else {
                    getMainActivity().getStylesManager().setShadowsActive(false);
                }
                shadowsActive = isChecked;
                onSwitchShadowsChanged();
            }
        });
        bordersSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    getMainActivity().getStylesManager().setRadiusActive(true);
                } else {
                    getMainActivity().getStylesManager().setRadiusActive(false);
                }
                bordersActive = isChecked;
            }
        });
        //Spinner
        imageBGSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changeSpinnerBackground(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    //endregion

    //region Eventos
    //ColorPickerDialogBuilder es una librería desarrollada por QuadFlask
    //Licensed under the Apache License, Version 2.0
    //https://github.com/QuadFlask/colorpicker
    //Pulsación del botón de fondo
    private void onClickButtonBackground(){
        if (!gradientBGActive && !imageBGActive){
        ColorPickerDialogBuilder
                .with(getMainActivity())
                .setTitle("Escoge un color para el fondo")
                .initialColor(getMainActivity().getStylesManager().getBackgroundColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        root.setBackgroundColor(selectedColor);
                        drawableBackground.setColor(selectedColor);
                        getMainActivity().getStylesManager().setBackgroundColor(selectedColor);
                        defaultBackgroundColor = selectedColor;
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .build()
                .show();
        } else {
            getMainActivity().toastMessage("Desactiva primero la otra opción del fondo");
        }
    }
    //Pulsación del botón de color principal
    private void onClickButtonColor(){
        if (!gradientMainColorActive) {
            ColorPickerDialogBuilder
                    .with(getMainActivity())
                    .setTitle("Escoge el color principal")
                    .initialColor(getMainActivity().getStylesManager().getMainColor())
                    .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                    .density(12)
                    .setOnColorSelectedListener(new OnColorSelectedListener() {
                        @Override
                        public void onColorSelected(int selectedColor) {
                        }
                    })
                    .setPositiveButton("ok", new ColorPickerClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                            drawableColor.setColor(selectedColor);
                            drawableColor2.setStroke(defaultButtonRadius, selectedColor);
                            buttonMainColor.setBackground(drawableColor);
                            buttonSecondaryColor.setBackground(drawableColor2);
                            drawableDecreaseTextSize.setTint(selectedColor);
                            drawableIncreaseTextSize.setTint(selectedColor);
                            getMainActivity().getStylesManager().setMainColor(selectedColor);
                            defaultMainColor = selectedColor;
                            drawableImageBG.setColor(selectedColor);
                            getMainActivity().getSupportActionBar().setBackgroundDrawable(new ColorDrawable(selectedColor));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                getMainActivity().getNavView().setItemIconTintList(ColorStateList.valueOf(selectedColor));
                                getMainActivity().getNavView().setItemTextColor(ColorStateList.valueOf(selectedColor));
                            }

                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .build()
                    .show();
        } else {
            getMainActivity().toastMessage("Desactiva primero la opción de degradado");
        }
    }
    //Pulsación del botón de color secundario
    private void onClickButtonColor2(){
        if (!gradientSecondaryColorActive) {
            ColorPickerDialogBuilder
                    .with(getMainActivity())
                    .setTitle("Escoge el color secundario")
                    .initialColor(getMainActivity().getStylesManager().getSecondaryColor())
                    .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                    .density(12)
                    .setOnColorSelectedListener(new OnColorSelectedListener() {
                        @Override
                        public void onColorSelected(int selectedColor) {
                        }
                    })
                    .setPositiveButton("ok", new ColorPickerClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                            getMainActivity().getStylesManager().setSecondaryColor(selectedColor);
                            defaultSecondaryColor = selectedColor;
                            drawableColor2.setColor(selectedColor);
                            onSwitchBordersChanged();
                            Window window = getMainActivity().getWindow();
                            window.setStatusBarColor(selectedColor);
                            getMainActivity().getNavView().setBackgroundColor(selectedColor);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .build()
                    .show();
        } else {
            getMainActivity().toastMessage("Desactiva primero la opción de degradado");
        }
    }
    //Pulsación del botón de color principal de textos
    private void onClickButtonText(){
        ColorPickerDialogBuilder
                .with(getMainActivity())
                .setTitle("Escoge un color para los textos")
                .initialColor(getMainActivity().getStylesManager().getTextMainColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        drawableText.setColor(selectedColor);
                        bgText.setTextColor(selectedColor);
                        colorText.setTextColor(selectedColor);
                        color2Text.setTextColor(selectedColor);
                        textText.setTextColor(selectedColor);
                        text2Text.setTextColor(selectedColor);
                        gradientBGText.setTextColor(selectedColor);
                        gradientColor1Text.setTextColor(selectedColor);
                        gradientColor2Text.setTextColor(selectedColor);
                        textSizeText.setTextColor(selectedColor);
                        shadowsText.setTextColor(selectedColor);
                        bordersText.setTextColor(selectedColor);
                        textImageBG.setTextColor(selectedColor);
                        if (shadowsActive){
                            bgText.setShadowLayer(1.5f, -1, 1, selectedColor);
                            colorText.setShadowLayer(1.5f, -1, 1, selectedColor);
                            color2Text.setShadowLayer(1.5f, -1, 1, selectedColor);
                            textText.setShadowLayer(1.5f, -1, 1, selectedColor);
                            text2Text.setShadowLayer(1.5f, -1, 1, selectedColor);
                            gradientBGText.setShadowLayer(1.5f, -1, 1, selectedColor);
                            gradientColor1Text.setShadowLayer(1.5f, -1, 1, selectedColor);
                            gradientColor2Text.setShadowLayer(1.5f, -1, 1, selectedColor);
                            textSizeText.setShadowLayer(1.5f, -1, 1, selectedColor);
                            shadowsText.setShadowLayer(1.5f, -1, 1, selectedColor);
                            bordersText.setShadowLayer(1.5f, -1, 1, selectedColor);
                            textImageBG.setShadowLayer(1.5f, -1, 1, selectedColor);
                        }
                        getMainActivity().getStylesManager().setTextMainColor(selectedColor);
                        defaultTextMainColor = selectedColor;
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .build()
                .show();
    }
    //Pulsación del botón de color secundario de textos
    private void onClickButtonText2(){
        ColorPickerDialogBuilder
                .with(getMainActivity())
                .setTitle("Escoge un color para los textos secundarios")
                .initialColor(getMainActivity().getStylesManager().getTextSecondaryColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        drawableText2.setColor(selectedColor);
                        getMainActivity().getStylesManager().setTextSecondaryColor(selectedColor);
                        defaultTextSecondaryColor = selectedColor;
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }
    //Pulsación del switch de sombras
    private void onSwitchShadowsChanged(){
        if (shadowsActive){
            bgText.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            colorText.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            color2Text.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            textText.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            text2Text.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            shadowsText.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            bordersText.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            gradientBGText.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            gradientColor1Text.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            gradientColor2Text.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            textSizeText.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            textImageBG.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
        } else {
            bgText.setShadowLayer(0, 0, 0, defaultMainColor);
            colorText.setShadowLayer(0, 0, 0, defaultMainColor);
            color2Text.setShadowLayer(0, 0, 0, defaultMainColor);
            textText.setShadowLayer(0, 0, 0, defaultMainColor);
            text2Text.setShadowLayer(0, 0, 0, defaultMainColor);
            shadowsText.setShadowLayer(0, 0, 0, defaultMainColor);
            bordersText.setShadowLayer(0, 0, 0, defaultMainColor);
            gradientBGText.setShadowLayer(0, 0, 0, defaultMainColor);
            gradientColor1Text.setShadowLayer(0, 0, 0, defaultMainColor);
            gradientColor2Text.setShadowLayer(0, 0, 0, defaultMainColor);
            textSizeText.setShadowLayer(0, 0, 0, defaultMainColor);
            textImageBG.setShadowLayer(0, 0, 0, defaultMainColor);
        }
    }
    //Pulsación del switch de bordes de los botones
    private void onSwitchBordersChanged(){
            drawableBackground.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableColor.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableText.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableColor2.setStroke(defaultButtonRadius, defaultMainColor);
            drawableText2.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableImageBG.setStroke(defaultButtonRadius, defaultSecondaryColor);
            buttonBackgroundColor.setBackground(drawableBackground);
            buttonMainColor.setBackground(drawableColor);
            buttonSecondaryColor.setBackground(drawableColor2);
            buttonTextMainColor.setBackground(drawableText);
            buttonTextSecondaryColor.setBackground(drawableText2);
    }
    //Pulsación del botón de gradiente del fondo
    private void onClickGradientBG(final int gradientIndex){
        ColorPickerDialogBuilder
                .with(getMainActivity())
                .setTitle("Escoge un color para el fondo")
                .initialColor(getMainActivity().getStylesManager().getBackgroundColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        //root.setBackgroundColor(selectedColor);
                        //getMainActivity().getStylesManager().setBackgroundColor(selectedColor);
                        switch(gradientIndex){
                            case 0:
                                colorBG1 = selectedColor;
                                drawableGradient1BG.setColor(selectedColor);
                                buttonGradient1BG.setBackground(drawableGradient1BG);
                                defaultBackgroundGradientColor1 = selectedColor;
                                getMainActivity().getStylesManager().setBackgroundGradientColor1(selectedColor);
                                break;
                            case 1:
                                colorBG2 = selectedColor;
                                drawableGradient2BG.setColor(selectedColor);
                                buttonGradient2BG.setBackground(drawableGradient2BG);
                                defaultBackgroundGradientColor2 = selectedColor;
                                getMainActivity().getStylesManager().setBackgroundGradientColor2(selectedColor);
                                break;
                            case 2:
                                colorBG3 = selectedColor;
                                drawableGradient3BG.setColor(selectedColor);
                                buttonGradient3BG.setBackground(drawableGradient3BG);
                                defaultBackgroundGradientColor3 = selectedColor;
                                getMainActivity().getStylesManager().setBackgroundGradientColor3(selectedColor);
                                break;
                            default:
                                break;
                        }
                        //drawableBackground.setColors(new int[]{colorBG1, colorBG2, colorBG3});
                        changeBackground(true, false);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .build()
                .show();
    }
    //Pulsación del botón de gradiente del color primario
    private void onClickGradientColor1(final int gradientIndex){
        ColorPickerDialogBuilder
                .with(getMainActivity())
                .setTitle("Escoge un color para el fondo")
                .initialColor(getMainActivity().getStylesManager().getBackgroundColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        //root.setBackgroundColor(selectedColor);
                        //getMainActivity().getStylesManager().setBackgroundColor(selectedColor);
                        switch(gradientIndex){
                            case 0:
                                colorMain1 = selectedColor;
                                drawableGradient1MainColor.setColor(selectedColor);
                                buttonGradient1MainColor.setBackground(drawableGradient1MainColor);
                                defaultMainGradientColor1 = selectedColor;
                                getMainActivity().getStylesManager().setMainGradientColor1(selectedColor);
                                break;
                            case 1:
                                colorMain2 = selectedColor;
                                drawableGradient2MainColor.setColor(selectedColor);
                                buttonGradient2MainColor.setBackground(drawableGradient2MainColor);
                                defaultMainGradientColor2 = selectedColor;
                                getMainActivity().getStylesManager().setMainGradientColor2(selectedColor);
                                break;
                            case 2:
                                colorMain3 = selectedColor;
                                drawableGradient3MainColor.setColor(selectedColor);
                                buttonGradient3MainColor.setBackground(drawableGradient3MainColor);
                                defaultMainGradientColor3 = selectedColor;
                                getMainActivity().getStylesManager().setMainGradientColor3(selectedColor);
                                break;
                            default:
                                break;
                        }
                        drawableImageBG.setColors(new int[]{defaultMainGradientColor1, defaultMainGradientColor2,
                                defaultMainGradientColor3});
                        //drawableColorWithGradient.setColors(new int[]{colorMain1, colorMain2, colorMain3});
                        //buttonMainColor.setBackground(drawableColorWithGradient);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .build()
                .show();
    }
    //Pulsación del botón de gradiente del color secundario
    private void onClickGradientColor2(final int gradientIndex){
        ColorPickerDialogBuilder
                .with(getMainActivity())
                .setTitle("Escoge un color para el fondo")
                .initialColor(getMainActivity().getStylesManager().getBackgroundColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        //root.setBackgroundColor(selectedColor);
                        //getMainActivity().getStylesManager().setBackgroundColor(selectedColor);
                        switch(gradientIndex){
                            case 0:
                                colorSecondary1 = selectedColor;
                                drawableGradient1SecondaryColor.setColor(selectedColor);
                                buttonGradient1SecondaryColor.setBackground(drawableGradient1SecondaryColor);
                                defaultSecondaryGradientColor1 = selectedColor;
                                getMainActivity().getStylesManager().setSecondaryGradientColor1(selectedColor);
                                break;
                            case 1:
                                colorSecondary2 = selectedColor;
                                drawableGradient2SecondaryColor.setColor(selectedColor);
                                buttonGradient2SecondaryColor.setBackground(drawableGradient2SecondaryColor);
                                defaultSecondaryGradientColor2 = selectedColor;
                                getMainActivity().getStylesManager().setSecondaryGradientColor2(selectedColor);
                                break;
                            case 2:
                                colorSecondary3 = selectedColor;
                                drawableGradient3SecondaryColor.setColor(selectedColor);
                                buttonGradient3SecondaryColor.setBackground(drawableGradient3SecondaryColor);
                                defaultSecondaryGradientColor3 = selectedColor;
                                getMainActivity().getStylesManager().setSecondaryGradientColor3(selectedColor);
                                break;
                            default:
                                break;
                        }
                        //drawableColor2WithGradient.setColors(new int[]{colorSecondary1,
                        //        colorSecondary2, colorSecondary3});
                        //buttonSecondaryColor.setBackground(drawableColor2WithGradient);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .build()
                .show();
    }
    //Pulsación de los botones del tamaño de textos
    private void onClickTextSize(boolean isGrowing){
        if (isGrowing){
            defaultTextSizeChange += TEXT_SIZE_CHANGE;
            if (defaultTextSizeChange > TEXT_SIZE_CHANGE_THRESHOLD_UP){
                getMainActivity().toastMessage("Has alcanzado el tamaño máximo de texto");
                defaultTextSizeChange = TEXT_SIZE_CHANGE_THRESHOLD_UP;
            } else {
                changeAllTexts(true);
            }
        } else {
            defaultTextSizeChange -= TEXT_SIZE_CHANGE;
            if (defaultTextSizeChange < TEXT_SIZE_CHANGE_THRESHOLD_DOWN){
                getMainActivity().toastMessage("Has alcanzado el tamaño mínimo de texto");
                defaultTextSizeChange = TEXT_SIZE_CHANGE_THRESHOLD_DOWN;
            } else {
                changeAllTexts(false);
            }
        }
        getMainActivity().getStylesManager().setTextSizeChange(defaultTextSizeChange);
    }
    //endregion

    //region Visibilidad
    private void showButtonsBackgroundGradient(boolean visible){
        if (visible){
            buttonGradient1BG.setVisibility(View.VISIBLE);
            buttonGradient2BG.setVisibility(View.VISIBLE);
            buttonGradient3BG.setVisibility(View.VISIBLE);
        } else {
            buttonGradient1BG.setVisibility(View.GONE);
            buttonGradient2BG.setVisibility(View.GONE);
            buttonGradient3BG.setVisibility(View.GONE);
        }
    }
    private void showButtonsMainColorGradient(boolean visible){
        if (visible){
            buttonGradient1MainColor.setVisibility(View.VISIBLE);
            buttonGradient2MainColor.setVisibility(View.VISIBLE);
            buttonGradient3MainColor.setVisibility(View.VISIBLE);
        } else {
            buttonGradient1MainColor.setVisibility(View.GONE);
            buttonGradient2MainColor.setVisibility(View.GONE);
            buttonGradient3MainColor.setVisibility(View.GONE);
        }
    }
    private void showButtonsSecondaryColorGradient(boolean visible){
        if (visible){
            buttonGradient1SecondaryColor.setVisibility(View.VISIBLE);
            buttonGradient2SecondaryColor.setVisibility(View.VISIBLE);
            buttonGradient3SecondaryColor.setVisibility(View.VISIBLE);
        } else {
            buttonGradient1SecondaryColor.setVisibility(View.GONE);
            buttonGradient2SecondaryColor.setVisibility(View.GONE);
            buttonGradient3SecondaryColor.setVisibility(View.GONE);
        }
    }
    private void changeBackground(boolean gradientActive, boolean imageActive){
        if (gradientActive){
            drawableBackgroundWithGradient.setColors(new int[]{defaultBackgroundGradientColor1,
                defaultBackgroundGradientColor2, defaultBackgroundGradientColor3});
            root.setBackground(drawableBackgroundWithGradient);
        } else if (imageActive){
            root.setBackground(getMainActivity().getDrawable(defaultBackgroundImage));
        } else {
            root.setBackgroundColor(defaultBackgroundColor);
        }
    }
    private void changeAllTexts(boolean isGrowing){
        float sizeOfChange = TEXT_SIZE_CHANGE;
        if (!isGrowing){
            sizeOfChange = -TEXT_SIZE_CHANGE;
        }
        bgText.setTextSize(bgText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+sizeOfChange);
        colorText.setTextSize(bgText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+sizeOfChange);
        color2Text.setTextSize(color2Text.getTextSize()/getResources().getDisplayMetrics().scaledDensity+sizeOfChange);
        textText.setTextSize(textText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+sizeOfChange);
        text2Text.setTextSize(text2Text.getTextSize()/getResources().getDisplayMetrics().scaledDensity+sizeOfChange);
        shadowsText.setTextSize(shadowsText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+sizeOfChange);
        bordersText.setTextSize(bordersText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+sizeOfChange);
        gradientBGText.setTextSize(gradientBGText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+sizeOfChange);
        gradientColor1Text.setTextSize(gradientColor1Text.getTextSize()/getResources().getDisplayMetrics().scaledDensity+sizeOfChange);
        gradientColor2Text.setTextSize(gradientColor2Text.getTextSize()/getResources().getDisplayMetrics().scaledDensity+sizeOfChange);
        textSizeText.setTextSize(textSizeText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+sizeOfChange);
        textImageBG.setTextSize(textImageBG.getTextSize()/getResources().getDisplayMetrics().scaledDensity+sizeOfChange);
    }
    private void initTextSizes(){
        bgText.setTextSize(bgText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        colorText.setTextSize(bgText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        color2Text.setTextSize(color2Text.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textText.setTextSize(textText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        text2Text.setTextSize(text2Text.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        shadowsText.setTextSize(shadowsText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        bordersText.setTextSize(bordersText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        gradientBGText.setTextSize(gradientBGText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        gradientColor1Text.setTextSize(gradientColor1Text.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        gradientColor2Text.setTextSize(gradientColor2Text.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textSizeText.setTextSize(textSizeText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textImageBG.setTextSize(textImageBG.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
    }

    private void changeSpinnerBackground(int index){
        switch (index){
            case 0:
                defaultBackgroundImage = R.drawable.bg_1;
                break;
            case 1:
                defaultBackgroundImage = R.drawable.bg_2;
                break;
            case 2:
                defaultBackgroundImage = R.drawable.bg_3;
                break;
            case 3:
                defaultBackgroundImage = R.drawable.bg_4;
                break;
            case 4:
                defaultBackgroundImage = R.drawable.bg_5;
                break;
            case 5:
                defaultBackgroundImage = R.drawable.bg_6;
                break;
            case 6:
                defaultBackgroundImage = R.drawable.bg_7;
                break;
            case 7:
                defaultBackgroundImage = R.drawable.bg_8;
                break;
        }
        getMainActivity().getStylesManager().setBackgroundImageId(defaultBackgroundImage);
        changeBackground(gradientBGActive, imageBGActive);
    }

    private void initSpinner(){
        switch (defaultBackgroundImage){
            case R.drawable.bg_1:
                imageBGSpinner.setSelection(0);
                break;
            case R.drawable.bg_2:
                imageBGSpinner.setSelection(1);
                break;
            case R.drawable.bg_3:
                imageBGSpinner.setSelection(2);
                break;
            case R.drawable.bg_4:
                imageBGSpinner.setSelection(3);
                break;
            case R.drawable.bg_5:
                imageBGSpinner.setSelection(4);
                break;
            case R.drawable.bg_6:
                imageBGSpinner.setSelection(5);
                break;
            case R.drawable.bg_7:
                imageBGSpinner.setSelection(6);
                break;
            case R.drawable.bg_8:
                imageBGSpinner.setSelection(7);
                break;
        }
    }
    //endregion
}
