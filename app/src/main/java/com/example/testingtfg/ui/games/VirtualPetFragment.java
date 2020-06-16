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

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.example.testingtfg.BaseFragment;
import com.example.testingtfg.MainActivity;
import com.example.testingtfg.R;
import com.example.testingtfg.minigames.virtualPet.VirtualPet;
import com.example.testingtfg.minigames.virtualPet.VirtualPetManager;

/*Clase que gestiona el fragmento de las mascotas virtuales*/
public class VirtualPetFragment extends BaseFragment {

    //region Parámetros de funcionalidad
    private GamesViewModel gamesViewModel;
    private MainActivity activity;
    private VirtualPetManager virtualPetManager;
    private LinearLayout petLayout;
    //endregion

    //region Parámetros visuales
    private View root;
    private Button minigamesButton;
    private Button marketButton;
    private TextView pointsText;
    private LinearLayout equipmentLayout;
    private LinearLayout pillsLayout;
    private Button expBeltButton;
    private Button cleanBeltButton;
    private Button foodBeltButton;
    private Button unattachButton;
    private Button closeButton;
    private Button expPillButton;
    private Button foodPillButton;
    private Button cleanPillButton;
    private Button evoRockButton;
    //endregion

    //region Métodos visuales
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gamesViewModel =
                ViewModelProviders.of(this).get(GamesViewModel.class);
        root = inflater.inflate(R.layout.fragment_games_virtual_pet, container, false);
        activity = getMainActivity();
        virtualPetManager = activity.getVirtualPetManager();
        loadColors(root);
        setupBackground(root);
        ((TextView)root.findViewById(R.id.textView)).setTextColor(defaultTextMainColor);
        initButtons();
        createPetLayouts();
        initTextSizes();
        notifyHungerAndDirt();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    //Oculta los botones
    private void hideButtons(){
        minigamesButton.setVisibility(View.GONE);
        marketButton.setVisibility(View.GONE);
        pointsText.setVisibility(View.GONE);
    }

    //Crea los layouts de las mascotas
    private void createPetLayouts(){
        petLayout = root.findViewById(R.id.virtualPetLayout);
        petLayout.removeAllViewsInLayout();
        virtualPetManager.increasePetsHungerAndDirt();
        for (VirtualPet vp : virtualPetManager.getVirtualPetList()){
            createPetSpot(petLayout, vp);
        }
        pointsText = (TextView) root.findViewById(R.id.pointsText);
        pointsText.setText("Fichas de juego: " + virtualPetManager.getPlayPoints() + " \nCápsulas de comida: "
                + virtualPetManager.getFoodTokens() + " \nCápsulas de limpieza: " + virtualPetManager.getCleanTokens() + " \nCápsulas de experiencia: "
                + virtualPetManager.getExpTokens() + " \nRocas de evolución: " + virtualPetManager.getEvoTokens());
        pointsText.setTextColor(defaultTextMainColor);
        if (shadowsActive){
            pointsText.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
        } else {
            pointsText.setShadowLayer(0, 0, 0, defaultTextMainColor);
        }
    }
    //Crea un layout para una mascota en concreto
    private void createPetSpot(LinearLayout verticalLayout, VirtualPet virtualPet){
        //Index
        final int id = virtualPet.getIdInList();
        //Layout
        LinearLayout horizontalLayout = new LinearLayout(getMainActivity());
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        //Pet Sprite
        ImageView petSprite = new ImageView(getMainActivity());
        petSprite.setImageResource(virtualPet.getPetSprite());
        //Pet Information
        LinearLayout innerVerticalLayout = new LinearLayout(getMainActivity());
        innerVerticalLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textName = new TextView(getMainActivity());
        TextView textExp= new TextView(getMainActivity());
        TextView textHungry = new TextView(getMainActivity());
        TextView textDirt = new TextView(getMainActivity());
        TextView textEquipment = new TextView(getMainActivity());
        textName.setText("Nombre: " + virtualPet.getPetName());
        textName.setTextColor(defaultTextMainColor);
        textName.setTextSize(textName.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textExp.setText("Experiencia: " + virtualPet.getExperience());
        textExp.setTextColor(defaultTextMainColor);
        textExp.setTextSize(textExp.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textHungry.setText("Hambre: " + virtualPet.getHunger());
        textHungry.setTextColor(defaultTextMainColor);
        textHungry.setTextSize(textHungry.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textDirt.setText("Suciedad: " + virtualPet.getDirt());
        textDirt.setTextColor(defaultTextMainColor);
        textDirt.setTextSize(textDirt.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textEquipment.setText("Equipo: " + virtualPet.getEquipedItemAsString());
        textEquipment.setTextColor(defaultTextMainColor);
        textEquipment.setTextSize(textEquipment.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        innerVerticalLayout.addView(petSprite);
        innerVerticalLayout.addView(textName);
        innerVerticalLayout.addView(textExp);
        innerVerticalLayout.addView(textHungry);
        innerVerticalLayout.addView(textDirt);
        innerVerticalLayout.addView(textEquipment);
        //Separation Bar
        View separationBar = new View(getMainActivity());
        separationBar.setBackgroundResource(R.color.paletteDarkBlue);
        separationBar.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 5));
        //Button
        GradientDrawable buttonShape = (GradientDrawable) getMainActivity().getResources()
                .getDrawable(R.drawable.button2_shape, null);
        if (gradientMainColorActive){
            buttonShape.setColors(new int[]{defaultMainGradientColor1,
                defaultMainGradientColor2, defaultMainGradientColor3});
        } else {
            buttonShape.setColor(defaultMainColor);
        }
        if (bordersActive){
            buttonShape.setStroke(defaultButtonRadius, defaultSecondaryColor);
        } else {
            buttonShape.setStroke(defaultButtonRadius, defaultMainColor);
        }
        Button itemsButton = new Button(getMainActivity());
        itemsButton.setText("Dar objeto");
        itemsButton.setLayoutParams(new LinearLayout.LayoutParams((int)getMainActivity().getResources().
                getDimension(R.dimen.button_virtualPet_width), (int)getMainActivity().getResources().
                getDimension(R.dimen.button_virtualPet_height)));
        itemsButton.setBackground(buttonShape);
        itemsButton.setTextColor(defaultTextSecondaryColor);
        itemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickConsume(id);
            }
        });
        Button equipButton = new Button(getMainActivity());
        equipButton.setText("Equipar objeto");
        equipButton.setLayoutParams(new LinearLayout.LayoutParams((int)getMainActivity().getResources().
                getDimension(R.dimen.button_virtualPet_width), (int)getMainActivity().getResources().
                getDimension(R.dimen.button_virtualPet_height)));
        equipButton.setBackground(buttonShape);
        equipButton.setTextColor(defaultTextSecondaryColor);
        equipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItems(id);
            }
        });
        horizontalLayout.addView(itemsButton);
        horizontalLayout.addView(equipButton);
        innerVerticalLayout.addView(horizontalLayout);
        verticalLayout.addView(innerVerticalLayout);
        verticalLayout.addView(separationBar);
        //Set Paddings
        petSprite.setPadding(25, 25, 0, 0);
        horizontalLayout.setPadding(5, 5, 0, 0);
        //Set Shadows
        if (shadowsActive){
            textName.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            textExp.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            textHungry.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            textDirt.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            textEquipment.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            itemsButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            equipButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
        } else {
            textName.setShadowLayer(0, 0, 0, defaultTextMainColor);
            textExp.setShadowLayer(0, 0, 0, defaultTextMainColor);
            textHungry.setShadowLayer(0, 0, 0, defaultTextMainColor);
            textDirt.setShadowLayer(0, 0, 0, defaultTextMainColor);
            textEquipment.setShadowLayer(0, 0, 0, defaultTextMainColor);
            itemsButton.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            equipButton.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
        }
    }
    //inicialización de los botones
    private void initButtons(){
        minigamesButton = root.findViewById(R.id.buttonMinigames);
        minigamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideButtons();
                MinigamesFragment dstFragment = new MinigamesFragment();
                activity.goToFragment(dstFragment, R.id.games_virtual_pet_fragment);
            }
        });

        marketButton = root.findViewById(R.id.buttonMarket);
        marketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideButtons();
                MarketFragment dstFragment = new MarketFragment();
                activity.goToFragment(dstFragment, R.id.games_virtual_pet_fragment);
            }
        });

        GradientDrawable minigamesDrawable = (GradientDrawable) minigamesButton.getBackground();
        GradientDrawable marketDrawable = (GradientDrawable)  marketButton.getBackground();
        if (gradientMainColorActive){
            minigamesDrawable.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
            marketDrawable.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
        } else {
            minigamesDrawable.setColor(defaultMainColor);
            marketDrawable.setColor(defaultMainColor);
        }
        if (bordersActive){
            minigamesDrawable.setStroke(defaultButtonRadius, defaultSecondaryColor);
            marketDrawable.setStroke(defaultButtonRadius, defaultSecondaryColor);
        } else {
            minigamesDrawable.setStroke(defaultButtonRadius, defaultMainColor);
            marketDrawable.setStroke(defaultButtonRadius, defaultMainColor);
        }

        minigamesButton.setTextColor(defaultTextSecondaryColor);
        marketButton.setTextColor(defaultTextSecondaryColor);
        equipmentLayout = (LinearLayout) root.findViewById(R.id.attachments_layout);
        pillsLayout = (LinearLayout) root.findViewById(R.id.pet_items_layout);

        if (shadowsActive){
            minigamesButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            marketButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
        } else {
            minigamesButton.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            marketButton.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
        }


    }
    //endregion

    //region Eventos
    //Evento desatado al pulsar el botón de experiencia
    private void onClickExp(int id){
        virtualPetManager.consumeExpToken(id);
        createPetLayouts();
    }
    //Evento desatado al pulsar el botón de comida
    private void onClickFood(int id){
        virtualPetManager.consumeFood(id);
        createPetLayouts();
    }
    //Evento desatado al pulsar el botón de limpieza
    private void onClickClean(int id){
        virtualPetManager.consumeCleaner(id);
        createPetLayouts();
    }
    //Evento desatado al pulsar el botón de evolucionar
    private void onClickEvolve(int id){
        virtualPetManager.consumeEvoRock(id);
        createPetLayouts();
    }
    //Evento desatado al pulsar el botón de Dar Objeto
    private void onClickConsume(int id){
        final int index = id;
        GradientDrawable drawableLayout = (GradientDrawable)pillsLayout.getBackground();
        if (gradientMainColorActive){
            drawableLayout.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
        } else {
            drawableLayout.setColor(defaultMainColor);
        }
        drawableLayout.setStroke(defaultButtonRadius+3, defaultSecondaryColor);
        expPillButton = (Button) pillsLayout.findViewById(R.id.button_exp);
        cleanPillButton = (Button) pillsLayout.findViewById(R.id.button_clean);
        foodPillButton = (Button) pillsLayout.findViewById(R.id.button_food);
        evoRockButton = (Button) pillsLayout.findViewById(R.id.button_evo);
        closeButton = (Button) pillsLayout.findViewById(R.id.button_close_items);
        GradientDrawable drawableExp = (GradientDrawable)expPillButton.getBackground();
        GradientDrawable drawableClean = (GradientDrawable)cleanPillButton.getBackground();
        GradientDrawable drawableFood = (GradientDrawable)foodPillButton.getBackground();
        GradientDrawable drawableEvo = (GradientDrawable)evoRockButton.getBackground();
        GradientDrawable drawableClose = (GradientDrawable)closeButton.getBackground();
        if (gradientSecondaryColorActive){
            drawableExp.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            drawableClean.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            drawableFood.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            drawableEvo.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            drawableClose.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
        } else {
            drawableExp.setColor(defaultSecondaryColor);
            drawableClean.setColor(defaultSecondaryColor);
            drawableFood.setColor(defaultSecondaryColor);
            drawableEvo.setColor(defaultSecondaryColor);
            drawableClose.setColor(defaultSecondaryColor);
        }
        if (bordersActive){
            drawableExp.setStroke(defaultButtonRadius, defaultBackgroundColor);
            drawableClean.setStroke(defaultButtonRadius, defaultBackgroundColor);
            drawableFood.setStroke(defaultButtonRadius, defaultBackgroundColor);
            drawableEvo.setStroke(defaultButtonRadius, defaultBackgroundColor);
            drawableClose.setStroke(defaultButtonRadius, defaultBackgroundColor);
        } else {
            drawableExp.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableClean.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableFood.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableEvo.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableClose.setStroke(defaultButtonRadius, defaultSecondaryColor);
        }
        pillsLayout.setBackground(drawableLayout);
        pillsLayout.setVisibility(View.VISIBLE);
        pillsLayout.setClickable(true);
        expPillButton.setBackground(drawableExp);
        cleanPillButton.setBackground(drawableClean);
        foodPillButton.setBackground(drawableFood);
        evoRockButton.setBackground(drawableEvo);
        closeButton.setBackground(drawableClose);
        expPillButton.setTextColor(defaultTextMainColor);
        cleanPillButton.setTextColor(defaultTextMainColor);
        foodPillButton.setTextColor(defaultTextMainColor);
        evoRockButton.setTextColor(defaultTextMainColor);
        closeButton.setTextColor(defaultTextMainColor);
        if (shadowsActive){
            expPillButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            cleanPillButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            foodPillButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            evoRockButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            closeButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
        } else {
            expPillButton.setShadowLayer(0, 0, 0,  defaultTextMainColor);
            cleanPillButton.setShadowLayer(0, 0, 0, defaultTextMainColor);
            foodPillButton.setShadowLayer(0, 0, 0, defaultTextMainColor);
            evoRockButton.setShadowLayer(0, 0, 0, defaultTextMainColor);
            closeButton.setShadowLayer(0, 0, 0, defaultTextMainColor);
        }
        expPillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickExp(index);
            }
        });
        cleanPillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickClean(index);
            }
        });
        foodPillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFood(index);
            }
        });
        evoRockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEvolve(index);
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickClose(0);
            }
        });
    }
    //Evento desatado al pulsar el botón de Equipar Objeto
    private void onClickItems(int id){
        final int index = id;
        GradientDrawable drawableLayout = (GradientDrawable)equipmentLayout.getBackground();
        if (gradientMainColorActive){
            drawableLayout.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
        } else {
            drawableLayout.setColor(defaultMainColor);
        }

        drawableLayout.setStroke(defaultButtonRadius+3, defaultSecondaryColor);
        expBeltButton = (Button) equipmentLayout.findViewById(R.id.button_attach_exp);
        cleanBeltButton = (Button) equipmentLayout.findViewById(R.id.button_attach_clean);
        foodBeltButton = (Button) equipmentLayout.findViewById(R.id.button_attach_food);
        unattachButton = (Button) equipmentLayout.findViewById(R.id.button_unattach);
        closeButton = (Button) equipmentLayout.findViewById(R.id.button_close_attach);
        GradientDrawable drawableExp = (GradientDrawable)expBeltButton.getBackground();
        GradientDrawable drawableClean = (GradientDrawable)cleanBeltButton.getBackground();
        GradientDrawable drawableFood = (GradientDrawable)foodBeltButton.getBackground();
        GradientDrawable drawableUnattach = (GradientDrawable)unattachButton.getBackground();
        GradientDrawable drawableClose = (GradientDrawable)closeButton.getBackground();
        if (gradientSecondaryColorActive){
            drawableExp.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            drawableClean.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            drawableFood.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            drawableUnattach.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            drawableClose.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
        } else {
            drawableExp.setColor(defaultSecondaryColor);
            drawableClean.setColor(defaultSecondaryColor);
            drawableFood.setColor(defaultSecondaryColor);
            drawableUnattach.setColor(defaultSecondaryColor);
            drawableClose.setColor(defaultSecondaryColor);
        }
        if (bordersActive){
            drawableExp.setStroke(defaultButtonRadius, defaultBackgroundColor);
            drawableClean.setStroke(defaultButtonRadius, defaultBackgroundColor);
            drawableFood.setStroke(defaultButtonRadius, defaultBackgroundColor);
            drawableUnattach.setStroke(defaultButtonRadius, defaultBackgroundColor);
            drawableClose.setStroke(defaultButtonRadius, defaultBackgroundColor);
        } else {
            drawableExp.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableClean.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableFood.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableUnattach.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableClose.setStroke(defaultButtonRadius, defaultSecondaryColor);
        }
        equipmentLayout.setBackground(drawableLayout);
        equipmentLayout.setVisibility(View.VISIBLE);
        equipmentLayout.setClickable(true);
        expBeltButton.setBackground(drawableExp);
        cleanBeltButton.setBackground(drawableClean);
        foodBeltButton.setBackground(drawableFood);
        unattachButton.setBackground(drawableUnattach);
        closeButton.setBackground(drawableClose);
        expBeltButton.setTextColor(defaultTextMainColor);
        cleanBeltButton.setTextColor(defaultTextMainColor);
        foodBeltButton.setTextColor(defaultTextMainColor);
        unattachButton.setTextColor(defaultTextMainColor);
        closeButton.setTextColor(defaultTextMainColor);
        if (shadowsActive){
            expBeltButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            cleanBeltButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            foodBeltButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            unattachButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            closeButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
        } else {
            expBeltButton.setShadowLayer(0, 0, 0, defaultTextMainColor);
            cleanBeltButton.setShadowLayer(0, 0, 0, defaultTextMainColor);
            foodBeltButton.setShadowLayer(0, 0, 0, defaultTextMainColor);
            unattachButton.setShadowLayer(0, 0, 0, defaultTextMainColor);
            closeButton.setShadowLayer(0, 0, 0, defaultTextMainColor);
        }
        expBeltButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSelectedItem(index, 0);
            }
        });
        cleanBeltButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSelectedItem(index, 1);
            }
        });
        foodBeltButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSelectedItem(index, 2);
            }
        });
        unattachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUnattach(index);
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickClose(1);
            }
        });
    }
    //Evento desatado al pulsar en un equipo
    private void onClickSelectedItem(int petId, int itemId){
        virtualPetManager.equipItem(petId, itemId);
        createPetLayouts();
    }
    //Evento desatado al pulsar en quitar equipo
    private void onClickUnattach(int petId){
        virtualPetManager.unattachItem(petId);
        createPetLayouts();
    }
    //Cierre de las ventanas emergentes
    private void onClickClose(int index){
        if (index == 0){
            pillsLayout.setVisibility(View.INVISIBLE);
            pillsLayout.setClickable(false);
        } else {
            equipmentLayout.setVisibility(View.INVISIBLE);
            equipmentLayout.setClickable(false);
        }
    }
    //endregion

    //region Setup
    //Inicialización del tamaño de los textos
    private void initTextSizes(){
        ((TextView)root.findViewById(R.id.textView)).setTextSize(((TextView) root
                .findViewById(R.id.textView)).getTextSize()/getResources().getDisplayMetrics()
                .scaledDensity+defaultTextSizeChange);
        pointsText.setTextSize(pointsText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
    }
    //notificaciones
    private void notifyHungerAndDirt(){
        if (virtualPetManager.isNotifyDirt()){
            getMainActivity().notifyMessage(virtualPetManager.DIRT_TITLE, virtualPetManager.DIRT_CONTENT, 0);
        }
        if (virtualPetManager.isNotifyHunger()){
            getMainActivity().notifyMessage(virtualPetManager.HUNGER_TITLE, virtualPetManager.HUNGER_CONTENT, 1);
        }
    }
    //endregion
}
