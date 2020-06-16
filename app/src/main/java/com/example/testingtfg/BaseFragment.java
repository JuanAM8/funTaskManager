package com.example.testingtfg;

import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.fragment.app.Fragment;

/*Clase padre del resto de Fragments en la aplicación. Alberga métodos y parámetros compartidos*/
public class BaseFragment extends Fragment {

    //region Constantes
    protected final float TEXT_SIZE_CHANGE_THRESHOLD_DOWN = -5.0f;
    protected final float TEXT_SIZE_CHANGE_THRESHOLD_UP = 3.5f;
    protected final float TEXT_SIZE_CHANGE = 0.2f;
    //endregion
    //region Parámetros
    protected int defaultBackgroundColor;
    protected int defaultMainColor;//Botones, iconos, menú superior y fondo de ventanas
    protected int defaultSecondaryColor;//Botones dentro de ventanas, menú de navegación y bordes
    protected int defaultTextMainColor;//For main background
    protected int defaultTextSecondaryColor;//For secondary background (main color)
    protected int defaultBackgroundGradientColor1;
    protected int defaultBackgroundGradientColor2;
    protected int defaultBackgroundGradientColor3;
    protected int defaultMainGradientColor1;
    protected int defaultMainGradientColor2;
    protected int defaultMainGradientColor3;
    protected int defaultSecondaryGradientColor1;
    protected int defaultSecondaryGradientColor2;
    protected int defaultSecondaryGradientColor3;
    protected int defaultBackgroundImage;
    protected boolean gradientBGActive;
    protected boolean gradientMainColorActive;
    protected boolean gradientSecondaryColorActive;
    protected boolean shadowsActive;
    protected boolean bordersActive;
    protected boolean imageBGActive;
    protected int defaultButtonRadius = 8;
    protected float defaultShadowRadius = 3.0f;
    protected int defaultShadowDx = -1;
    protected int defaultShadowDy = 1;
    protected float defaultTextSizeChange;
    //endregion
    //region Métodos
    /*Carga todos los parámetros desde el StylesManager para la estética de la aplicación*/
    protected void loadColors(View v){
        defaultBackgroundColor = getMainActivity().getStylesManager().getBackgroundColor();
        defaultMainColor = getMainActivity().getStylesManager().getMainColor();
        defaultSecondaryColor = getMainActivity().getStylesManager().getSecondaryColor();
        defaultTextMainColor = getMainActivity().getStylesManager().getTextMainColor();
        defaultTextSecondaryColor = getMainActivity().getStylesManager().getTextSecondaryColor();

        defaultBackgroundGradientColor1 = getMainActivity().getStylesManager().getBackgroundGradientColor1();
        defaultBackgroundGradientColor2 = getMainActivity().getStylesManager().getBackgroundGradientColor2();
        defaultBackgroundGradientColor3 = getMainActivity().getStylesManager().getBackgroundGradientColor3();
        defaultMainGradientColor1 = getMainActivity().getStylesManager().getMainGradientColor1();
        defaultMainGradientColor2 = getMainActivity().getStylesManager().getMainGradientColor2();
        defaultMainGradientColor3 = getMainActivity().getStylesManager().getMainGradientColor3();
        defaultSecondaryGradientColor1 = getMainActivity().getStylesManager().getSecondaryGradientColor1();
        defaultSecondaryGradientColor2 = getMainActivity().getStylesManager().getSecondaryGradientColor2();
        defaultSecondaryGradientColor3 = getMainActivity().getStylesManager().getSecondaryGradientColor3();

        shadowsActive = getMainActivity().getStylesManager().isShadowsActive();
        bordersActive = getMainActivity().getStylesManager().isRadiusActive();
        gradientBGActive = getMainActivity().getStylesManager().isGradientBGActive();
        gradientMainColorActive = getMainActivity().getStylesManager().isGradientMainColorActive();
        gradientSecondaryColorActive = getMainActivity().getStylesManager().isGradientSecondaryColorActive();
        imageBGActive = getMainActivity().getStylesManager().isImageBGActive();

        defaultBackgroundImage = getMainActivity().getStylesManager().getBackgroundImageId();

        defaultTextSizeChange = getMainActivity().getStylesManager().getTextSizeChange();
    }
    /*Establece el color o imagen de fondo en función de la información recogida*/
    protected void setupBackground(View v){
        if (gradientBGActive){
            GradientDrawable drawBG = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[]{});
            drawBG.setCornerRadius(0f);
            drawBG.setColors(new int[]{defaultBackgroundGradientColor1,
                    defaultBackgroundGradientColor2, defaultBackgroundGradientColor3});
            v.setBackground(drawBG);
        } else if (imageBGActive){
            v.setBackground(getMainActivity().getDrawable(defaultBackgroundImage));
        } else {
            v.setBackgroundColor(defaultBackgroundColor);
        }
    }

    /*Devuelve la actividad para invocar métodos propios de esta*/
    protected MainActivity getMainActivity(){
        return (MainActivity) getActivity();
    }
    //endregion
}
