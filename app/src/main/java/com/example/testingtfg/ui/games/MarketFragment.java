package com.example.testingtfg.ui.games;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testingtfg.BaseFragment;
import com.example.testingtfg.R;
import com.example.testingtfg.minigames.virtualPet.Market;
import com.example.testingtfg.minigames.virtualPet.PurchasableItem;
import com.example.testingtfg.minigames.virtualPet.VirtualPetManager;

/*Clase que gestiona el Fragment del Mercado*/
public class MarketFragment extends BaseFragment {

    //region Parámetros de funcionalidad
    VirtualPetManager virtualPetManager;
    Market market;
    //endregion

    //region Parámetros visuales
    private LinearLayout marketPetsLayout;
    private LinearLayout marketItemsLayout;
    private LinearLayout marketAccesoriesLayout;
    private TextView pointsInfo;
    Button buttonBack;
    //endregion

    //region Métodos visuales
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_games_market, container, false);
        loadColors(root);
        initObjects();
        setupViews(root);
        createMarketLayouts(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }
    //Crea un hueco del mercado para un objeto concreto
    private void createMarketSpot(LinearLayout categoryLayout, int drawableRes, String name,
                                  String description, int cost, boolean available, int index){
        //Layout
        LinearLayout horizontalLayout = new LinearLayout(getMainActivity());
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        //Pet Sprite
        ImageView purchasableSprite = new ImageView(getMainActivity());
        purchasableSprite.setImageResource(drawableRes);
        horizontalLayout.addView(purchasableSprite);
        //Purchasable Item Information
        LinearLayout innerVerticalLayout = new LinearLayout(getMainActivity());
        innerVerticalLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textName = new TextView(getMainActivity());
        TextView textDesc= new TextView(getMainActivity());
        TextView textCost = new TextView(getMainActivity());
        TextView textAvailable = new TextView(getMainActivity());
        textName.setText(name);
        textName.setTextColor(defaultTextMainColor);
        textName.setTextSize(textName.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textDesc.setText(description);
        textDesc.setTextColor(defaultTextMainColor);
        textDesc.setTextSize(textDesc.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textCost.setText("Coste: " + cost);
        textCost.setTextColor(defaultTextMainColor);
        textCost.setTextSize(textCost.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        if (available)textAvailable.setText("Disponible");
        else textAvailable.setText("No disponible");
        textAvailable.setTextColor(defaultTextMainColor);
        textAvailable.setId(index);
        textAvailable.setTextSize(textAvailable.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        innerVerticalLayout.addView(textName);
        innerVerticalLayout.addView(textDesc);
        innerVerticalLayout.addView(textCost);
        innerVerticalLayout.addView(textAvailable);
        horizontalLayout.addView(innerVerticalLayout);
        //Separation Bar
        View separationBar = new View(getMainActivity());
        separationBar.setBackgroundResource(R.color.paletteDarkBlue);
        separationBar.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 5));
        //Button
        final int id = index;
        GradientDrawable drawableButton = (GradientDrawable)getMainActivity()
                .getResources().getDrawable(R.drawable.button2_shape, null);
        if (gradientMainColorActive){
            drawableButton.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
        } else {
            drawableButton.setColor(defaultMainColor);
        }
        if (bordersActive){
            drawableButton.setStroke(defaultButtonRadius, defaultSecondaryColor);
        } else {
            drawableButton.setStroke(defaultButtonRadius, defaultMainColor);
        }
        Button buyButton = new Button(getMainActivity());
        buyButton.setText("Comprar");
        buyButton.setLayoutParams(new LinearLayout.LayoutParams((int)getMainActivity().getResources().
                getDimension(R.dimen.button_buy_market_width), (int)getMainActivity().getResources().
                getDimension(R.dimen.button_buy_market_height)));
        buyButton.setBackground(drawableButton);
        buyButton.setTextColor(defaultTextSecondaryColor);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purchaseItem(id, v);
            }
        });
        //horizontalLayout.addView(buyButton);
        categoryLayout.addView(horizontalLayout);
        categoryLayout.addView(buyButton);
        categoryLayout.addView(separationBar);
        buyButton.setTranslationX((int)getMainActivity().getResources().
                getDimension(R.dimen.button_buy_market_offset));
        //Set Paddings
        purchasableSprite.setPadding(25, 25, 0, 0);
        horizontalLayout.setPadding(5, 5, 0, 0);
        buyButton.setPadding(0, 0, 0, 5);
        if (shadowsActive){
            textName.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            textDesc.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            textCost.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            textAvailable.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            buyButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
        } else {
            textName.setShadowLayer(0,0,0, defaultTextMainColor);
            textDesc.setShadowLayer(0,0,0, defaultTextMainColor);
            textCost.setShadowLayer(0,0,0,defaultTextMainColor);
            textAvailable.setShadowLayer(0,0,0, defaultTextMainColor);
            buyButton.setShadowLayer(0,0,0, defaultTextSecondaryColor);
        }
    }
    //Preparación de las Views
    private void setupViews(View v){
        setupBackground(v);
        marketPetsLayout = (LinearLayout) v.findViewById(R.id.marketPetsLayout);
        marketItemsLayout = (LinearLayout) v.findViewById(R.id.marketItemsLayout);
        marketAccesoriesLayout = (LinearLayout) v.findViewById(R.id.marketAccesoriesLayout);
        pointsInfo = (TextView) v.findViewById(R.id.points_info);
        buttonBack = (Button) v.findViewById(R.id.buttonBackMarket);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMainActivity().getSupportFragmentManager().popBackStack();
            }
        });
        GradientDrawable drawableButton = (GradientDrawable)getMainActivity()
                .getResources().getDrawable(R.drawable.button2_shape, null);
        if (gradientMainColorActive){
            drawableButton.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
        } else {
            drawableButton.setColor(defaultMainColor);
        }

        if (bordersActive){
            drawableButton.setStroke(defaultButtonRadius, defaultSecondaryColor);
        } else {
            drawableButton.setStroke(defaultButtonRadius, defaultMainColor);
        }
        buttonBack.setBackground(drawableButton);
        buttonBack.setTextColor(defaultTextSecondaryColor);
        ((TextView)v.findViewById(R.id.text_pets)).setTextColor(defaultTextMainColor);
        ((TextView)v.findViewById(R.id.text_accesories)).setTextColor(defaultTextMainColor);
        ((TextView)v.findViewById(R.id.text_others)).setTextColor(defaultTextMainColor);
        ((TextView)v.findViewById(R.id.text_pets)).setTextSize(((TextView)v.findViewById(R.id.text_pets))
                .getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        ((TextView)v.findViewById(R.id.text_accesories)).setTextSize(((TextView)v.findViewById(R.id.text_pets))
                .getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        ((TextView)v.findViewById(R.id.text_others)).setTextSize(((TextView)v.findViewById(R.id.text_pets))
                .getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        if (shadowsActive){
            buttonBack.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            ((TextView)v.findViewById(R.id.text_pets)).setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            ((TextView)v.findViewById(R.id.text_accesories)).setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            ((TextView)v.findViewById(R.id.text_others)).setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
        } else {
            buttonBack.setShadowLayer(0,0,0, defaultTextSecondaryColor);
            ((TextView)v.findViewById(R.id.text_pets)).setShadowLayer(0,0,0, defaultTextMainColor);
            ((TextView)v.findViewById(R.id.text_accesories)).setShadowLayer(0,0,0, defaultTextMainColor);
            ((TextView)v.findViewById(R.id.text_others)).setShadowLayer(0,0,0, defaultTextMainColor);
        }
    }
    //Crea todos los espacios del mercado
    private void createMarketLayouts(View v) {
        int index = 0;
        marketPetsLayout.removeAllViewsInLayout();
        marketItemsLayout.removeAllViewsInLayout();
        marketAccesoriesLayout.removeAllViewsInLayout();
        for (PurchasableItem item : market.getPurchasableItems()) {
            if (item.getItemClass() == PurchasableItem.purchasableClass.Pet) {
                createMarketSpot(marketPetsLayout, item.getSprite(), item.getName(),
                        item.getDescription(), item.getCost(), item.isAvailable(), index);
            } else if (item.getItemClass() == PurchasableItem.purchasableClass.Expendable) {
                createMarketSpot(marketItemsLayout, item.getSprite(), item.getName(),
                        item.getDescription(), item.getCost(), item.isAvailable(), index);
            } else if (item.getItemClass() == PurchasableItem.purchasableClass.Attachment) {
                createMarketSpot(marketAccesoriesLayout, item.getSprite(), item.getName(),
                        item.getDescription(), item.getCost(), item.isAvailable(), index);
            }
            //item.setIdInView(index);
            index++;
        }
        pointsInfo.setText("Fichas de juego: " + getMainActivity().getVirtualPetManager().getPlayPoints());
        pointsInfo.setTextColor(defaultTextMainColor);
        pointsInfo.setTextSize(pointsInfo.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        if (shadowsActive){
            pointsInfo.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
        } else {
            pointsInfo.setShadowLayer(0,0,0, defaultTextMainColor);
        }
    }
    //endregion

    //region Gestión del Mercado
    //Inicialización de los objetos
    private void initObjects(){
        virtualPetManager = getMainActivity().getVirtualPetManager();
        market = new Market(virtualPetManager, getActivity());
    }
    //Comprar un objeto
    private void purchaseItem(int index, View v){
        PurchasableItem item = market.getItem(index);
        if (getMainActivity().getVirtualPetManager().getPlayPoints() >= item.getCost()){
            if (item.isAvailable()){
                market.purchase(index);
                getMainActivity().getVirtualPetManager().increasePlayPoints(-item.getCost());
                getMainActivity().toastMessage("Has comprado: " + item.getName());
                createMarketLayouts(v);
            } else {
                getMainActivity().toastMessage("Ya has comprado ese artículo");
            }
        } else {
            getMainActivity().toastMessage("No tienes suficientes fichas");
        }
    }
    //endregion
}
