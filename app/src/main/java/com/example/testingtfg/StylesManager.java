package com.example.testingtfg;

import android.content.Context;
import android.content.SharedPreferences;

public class StylesManager {

    //region Parámetros de SharedPreferences
    SharedPreferences preferences;
    public static final String StylesPreferences = "styles_pref";
    public static final String BackgroundColor = "bg_color";
    public static final String MainColor = "main_color";
    public static final String SecondaryColor = "secondary_color";
    public static final String TextMainColor = "text_color";
    public static final String TextSecondaryColor = "text2_color";
    public static final String ButtonWidth = "button_width";
    public static final String ButtonHeight = "button_height";
    public static final String TextSize = "text_size";
    public static final String RadiusSize = "radius_size";
    public static final String RadiusActive = "radius_active";
    public static final String ShadowsActive = "shadows_active";
    public static final String GradientBGActive = "gradient_bg_active";
    public static final String GradientMainColorActive = "gradient_main_active";
    public static final String GradientSecondaryColorActive = "gradient_secondary_active";
    public static final String ImageBGActive = "image_bg_active";
    public static final String BackgroundGradientColor1 = "bg_gradient_color1";
    public static final String BackgroundGradientColor2 = "bg_gradient_color2";
    public static final String BackgroundGradientColor3 = "bg_gradient_color3";
    public static final String MainGradientColor1 = "main_gradient_color1";
    public static final String MainGradientColor2 = "main_gradient_color2";
    public static final String MainGradientColor3 = "main_gradient_color3";
    public static final String SecondaryGradientColor1 = "secondary_gradient_color1";
    public static final String SecondaryGradientColor2 = "secondary_gradient_color2";
    public static final String SecondaryGradientColor3 = "secondary_gradient_color3";
    public static final String BackgroundImageId ="bg_image_id";

    private int backgroundColor;
    private int mainColor;
    private int secondaryColor;
    private int textMainColor;
    private int textSecondaryColor;
    private int buttonWidth;
    private int buttonHeight;
    private float textSizeChange;
    private int radiusSizeChange;
    private boolean shadowsActive;
    private boolean radiusActive;
    private boolean gradientBGActive;
    private boolean gradientMainColorActive;
    private boolean gradientSecondaryColorActive;
    private boolean imageBGActive;
    private int backgroundGradientColor1;
    private int backgroundGradientColor2;
    private int backgroundGradientColor3;
    private int mainGradientColor1;
    private int mainGradientColor2;
    private int mainGradientColor3;
    private int secondaryGradientColor1;
    private int secondaryGradientColor2;
    private int secondaryGradientColor3;
    private int backgroundImageId;
    //endregion

    private MainActivity activity;

    //region constructor
    public StylesManager(MainActivity a){
        activity = a;
        loadPreferences();
    }
    //endregion

    //region Métodos de SharedPreferences
    /*Carga datos desde SharedPreferences a los parámetros de la clase*/
    private void loadPreferences(){
        preferences = activity.getSharedPreferences(StylesPreferences, Context.MODE_PRIVATE);
        //Colores
        backgroundColor = preferences.getInt(BackgroundColor, activity.getResources().getColor(R.color.paletteBackground, null));
        mainColor = preferences.getInt(MainColor, activity.getResources().getColor(R.color.paletteOrange, null));
        secondaryColor = preferences.getInt(SecondaryColor, activity.getResources().getColor(R.color.paletteSoftBG, null));
        textMainColor = preferences.getInt(TextMainColor, activity.getResources().getColor(R.color.paletteDarkBlue, null));
        textSecondaryColor = preferences.getInt(TextSecondaryColor, activity.getResources().getColor(R.color.paletteSoftBlue, null));
        //Estética extra
        shadowsActive = preferences.getBoolean(ShadowsActive, true);
        radiusActive = preferences.getBoolean(RadiusActive, true);
        //Tamaños
        buttonHeight = preferences.getInt(ButtonHeight, 30);
        buttonWidth = preferences.getInt(ButtonWidth, 122);
        radiusSizeChange = preferences.getInt(RadiusSize, 2);
        textSizeChange = preferences.getFloat(TextSize, 0);
        //Gradientes
        gradientBGActive = preferences.getBoolean(GradientBGActive, false);
        gradientMainColorActive = preferences.getBoolean(GradientMainColorActive, false);
        gradientSecondaryColorActive = preferences.getBoolean(GradientSecondaryColorActive, false);
        backgroundGradientColor1 = preferences.getInt(BackgroundGradientColor1, activity.getResources().getColor(R.color.paletteBackground, null));
        backgroundGradientColor2 = preferences.getInt(BackgroundGradientColor2, activity.getResources().getColor(R.color.paletteBackground, null));
        backgroundGradientColor3 = preferences.getInt(BackgroundGradientColor3, activity.getResources().getColor(R.color.paletteBackground, null));
        mainGradientColor1 = preferences.getInt(MainGradientColor1, activity.getResources().getColor(R.color.paletteOrange, null));
        mainGradientColor2 = preferences.getInt(MainGradientColor2, activity.getResources().getColor(R.color.paletteOrange, null));
        mainGradientColor3 = preferences.getInt(MainGradientColor3, activity.getResources().getColor(R.color.paletteOrange, null));
        secondaryGradientColor1 = preferences.getInt(SecondaryGradientColor1, activity.getResources().getColor(R.color.paletteSoftBG, null));
        secondaryGradientColor2 = preferences.getInt(SecondaryGradientColor2, activity.getResources().getColor(R.color.paletteSoftBG, null));
        secondaryGradientColor3 = preferences.getInt(SecondaryGradientColor3, activity.getResources().getColor(R.color.paletteSoftBG, null));
        //Imagen de fondo
        imageBGActive = preferences.getBoolean(ImageBGActive, false);
        backgroundImageId = preferences.getInt(BackgroundImageId, R.drawable.bg_1);
    }

    /*Guarda los datos de la clase en SharedPreferences*/
    private void savePreferences(){
        SharedPreferences.Editor spEditor = preferences.edit();
        spEditor.putInt(BackgroundColor, backgroundColor);
        spEditor.putInt(MainColor, mainColor);
        spEditor.putInt(SecondaryColor, secondaryColor);
        spEditor.putInt(TextMainColor, textMainColor);
        spEditor.putInt(TextSecondaryColor, textSecondaryColor);
        spEditor.putBoolean(ShadowsActive, shadowsActive);
        spEditor.putBoolean(RadiusActive, radiusActive);
        spEditor.putInt(ButtonWidth, buttonWidth);
        spEditor.putInt(ButtonHeight, buttonHeight);
        spEditor.putInt(RadiusSize, radiusSizeChange);
        spEditor.putFloat(TextSize, textSizeChange);
        spEditor.putBoolean(GradientBGActive, gradientBGActive);
        spEditor.putBoolean(GradientMainColorActive, gradientMainColorActive);
        spEditor.putBoolean(GradientSecondaryColorActive, gradientSecondaryColorActive);
        spEditor.putInt(BackgroundGradientColor1, backgroundGradientColor1);
        spEditor.putInt(BackgroundGradientColor2, backgroundGradientColor2);
        spEditor.putInt(BackgroundGradientColor3, backgroundGradientColor3);
        spEditor.putInt(MainGradientColor1, mainGradientColor1);
        spEditor.putInt(MainGradientColor2, mainGradientColor2);
        spEditor.putInt(MainGradientColor3, mainGradientColor3);
        spEditor.putInt(SecondaryGradientColor1, secondaryGradientColor1);
        spEditor.putInt(SecondaryGradientColor2, secondaryGradientColor2);
        spEditor.putInt(SecondaryGradientColor3, secondaryGradientColor3);
        spEditor.putBoolean(ImageBGActive, imageBGActive);
        spEditor.putInt(BackgroundImageId, backgroundImageId);
        spEditor.commit();
    }
    //endregion

    //region getters y setters
    //Todos los setters invocan al método SavePreferences()
    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        savePreferences();
    }

    public int getMainColor() {
        return mainColor;
    }

    public void setMainColor(int mainColor) {
        this.mainColor = mainColor;
        savePreferences();
    }

    public int getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(int secondaryColor) {
        this.secondaryColor = secondaryColor;
        savePreferences();
    }

    public int getTextMainColor() {
        return textMainColor;
    }

    public void setTextMainColor(int textMainColor) {
        this.textMainColor = textMainColor;
        savePreferences();
    }

    public int getTextSecondaryColor() {
        return textSecondaryColor;
    }

    public void setTextSecondaryColor(int textSecondaryColor) {
        this.textSecondaryColor = textSecondaryColor;
        savePreferences();
    }

    public int getButtonWidth() {
        return buttonWidth;
    }

    public void setButtonWidth(int buttonWidth) {
        this.buttonWidth = buttonWidth;
        savePreferences();
    }

    public int getButtonHeight() {
        return buttonHeight;
    }

    public void setButtonHeight(int buttonHeight) {
        this.buttonHeight = buttonHeight;
        savePreferences();
    }

    public float getTextSizeChange() {
        return textSizeChange;
    }

    public void setTextSizeChange(float textSizeChange) {
        this.textSizeChange = textSizeChange;
        savePreferences();
    }

    public int getRadiusSizeChange() {
        return radiusSizeChange;
    }

    public void setRadiusSizeChange(int radiusSizeChange) {
        this.radiusSizeChange = radiusSizeChange;
        savePreferences();
    }

    public boolean isShadowsActive() {
        return shadowsActive;
    }

    public void setShadowsActive(boolean shadowsActive) {
        this.shadowsActive = shadowsActive;
        savePreferences();
    }

    public boolean isRadiusActive() {
        return radiusActive;
    }

    public void setRadiusActive(boolean radiusActive) {
        this.radiusActive = radiusActive;
        savePreferences();
    }

    public boolean isGradientBGActive() {
        return gradientBGActive;
    }

    public void setGradientBGActive(boolean gradientBGActive) {
        this.gradientBGActive = gradientBGActive;
        savePreferences();
    }

    public boolean isGradientMainColorActive() {
        return gradientMainColorActive;
    }

    public void setGradientMainColorActive(boolean gradientMainColorActive) {
        this.gradientMainColorActive = gradientMainColorActive;
        savePreferences();
    }

    public boolean isGradientSecondaryColorActive() {
        return gradientSecondaryColorActive;
    }

    public void setGradientSecondaryColorActive(boolean gradientSecondaryColorActive) {
        this.gradientSecondaryColorActive = gradientSecondaryColorActive;
        savePreferences();
    }

    public int getBackgroundGradientColor1() {
        return backgroundGradientColor1;
    }

    public void setBackgroundGradientColor1(int backgroundGradientColor1) {
        this.backgroundGradientColor1 = backgroundGradientColor1;
        savePreferences();
    }

    public int getBackgroundGradientColor2() {
        return backgroundGradientColor2;
    }

    public void setBackgroundGradientColor2(int backgroundGradientColor2) {
        this.backgroundGradientColor2 = backgroundGradientColor2;
        savePreferences();
    }

    public int getBackgroundGradientColor3() {
        return backgroundGradientColor3;
    }

    public void setBackgroundGradientColor3(int backgroundGradientColor3) {
        this.backgroundGradientColor3 = backgroundGradientColor3;
        savePreferences();
    }

    public int getMainGradientColor1() {
        return mainGradientColor1;
    }

    public void setMainGradientColor1(int mainGradientColor1) {
        this.mainGradientColor1 = mainGradientColor1;
        savePreferences();
    }

    public int getMainGradientColor2() {
        return mainGradientColor2;
    }

    public void setMainGradientColor2(int mainGradientColor2) {
        this.mainGradientColor2 = mainGradientColor2;
        savePreferences();
    }

    public int getMainGradientColor3() {
        return mainGradientColor3;
    }

    public void setMainGradientColor3(int mainGradientColor3) {
        this.mainGradientColor3 = mainGradientColor3;
        savePreferences();
    }

    public int getSecondaryGradientColor1() {
        return secondaryGradientColor1;
    }

    public void setSecondaryGradientColor1(int secondaryGradientColor1) {
        this.secondaryGradientColor1 = secondaryGradientColor1;
        savePreferences();
    }

    public int getSecondaryGradientColor2() {
        return secondaryGradientColor2;
    }

    public void setSecondaryGradientColor2(int secondaryGradientColor2) {
        this.secondaryGradientColor2 = secondaryGradientColor2;
        savePreferences();
    }

    public int getSecondaryGradientColor3() {
        return secondaryGradientColor3;
    }

    public void setSecondaryGradientColor3(int secondaryGradientColor3) {
        this.secondaryGradientColor3 = secondaryGradientColor3;
        savePreferences();
    }

    public boolean isImageBGActive() {
        return imageBGActive;
    }

    public void setImageBGActive(boolean imageBGActive) {
        this.imageBGActive = imageBGActive;
        savePreferences();
    }

    public int getBackgroundImageId() {
        return backgroundImageId;
    }

    public void setBackgroundImageId(int backgroundImageId) {
        this.backgroundImageId = backgroundImageId;
        savePreferences();
    }
    //endregion
}
