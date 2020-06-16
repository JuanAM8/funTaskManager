package com.example.testingtfg.ui.task;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.testingtfg.BaseFragment;
import com.example.testingtfg.MainActivity;
import com.example.testingtfg.R;
import com.example.testingtfg.taskOrganizer.Annotation;
import com.example.testingtfg.taskOrganizer.SubTask;
import com.example.testingtfg.taskOrganizer.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskEditorFragment extends BaseFragment {

    //region Parámetros
    private TaskEditorViewModel taskEditorViewModel;
    private Task currentTask;
    private TasksFragment fatherFragment;
    int subTasksNumber;
    int annotationsNumber;
    boolean isInputEditVisible;
    private String todo_message = "Disponible próximamente";
    //endregion

    //region Parámetros Visuales
    private LinearLayout subTasksLayout;
    private LinearLayout annotationsLayout;
    private TextView textTaskName;
    private TextView textTaskPriority;
    private TextView textTaskCategory;
    private TextView textTaskTime;
    private TextView textTaskEstimatedTime;
    private Button buttonClose;
    private Button buttonName;
    private Button buttonPomodoro;
    private Button buttonNewSubTask;
    private Button buttonClearSubTasks;
    private Button buttonSetTime;
    private Button buttonSubmitTime;
    private Button buttonChangeDate;
    private Button buttonSubmitAnnotations;
    private EditText inputTextAnnotations;
    private EditText inputNewSubTask;
    private EditText inputEditTaskName;
    private Spinner spinnerPriority;
    private Spinner spinnerCategory;
    private TimePicker timePicker;
    private CalendarView calendarDate;
    //endregion

    //region Métodos Visuales
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        taskEditorViewModel = ViewModelProviders.of(this).get(TaskEditorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_task_editor, container, false);
        final TextView textView = root.findViewById(R.id.text_task_editor);
        taskEditorViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(null);
            }
        });
        loadColors(root);
        initiateObjects();
        setupViews(root);
        setupButtons(root);
        setupSpinners(root);
        initSubTaskViews();
        initAnnotationViews();
        return root;
    }

    //Prepara las Views
    private void setupViews(View v){
        //Background setup
        GradientDrawable windowDrawable = (GradientDrawable) v.getBackground();
        if (gradientMainColorActive){
            windowDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
            windowDrawable.setCornerRadius(100);
        } else {
            windowDrawable.setColor(defaultMainColor);
        }
        windowDrawable.setStroke(defaultButtonRadius+3, defaultSecondaryColor);
        v.setBackground(windowDrawable);
        //Layout setup
        subTasksLayout = (LinearLayout) v.findViewById(R.id.subtasksList);
        annotationsLayout = (LinearLayout) v.findViewById(R.id.annotationList);
        //Text Setup
        ((TextView)v.findViewById(R.id.subtasksTitle)).setTextColor(defaultTextSecondaryColor);
        ((TextView)v.findViewById(R.id.annotationsTitle)).setTextColor(defaultTextSecondaryColor);
        ((TextView)v.findViewById(R.id.subtasksTitle)).setTextSize(((TextView)v.findViewById(R.id.subtasksTitle))
                .getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        ((TextView)v.findViewById(R.id.annotationsTitle)).setTextSize(((TextView)v.findViewById(R.id.annotationsTitle))
                .getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textTaskName = (TextView) v.findViewById(R.id.taskTitle);
        textTaskPriority = (TextView) v.findViewById(R.id.taskPriority);
        textTaskCategory = (TextView) v.findViewById(R.id.taskCategory);
        textTaskTime = (TextView) v.findViewById(R.id.taskTime);
        textTaskEstimatedTime = (TextView) v.findViewById(R.id.estimatedTimeTitle);
        //Text Content
        textTaskName.setText(currentTask.getName());
        textTaskTime.setText("Vencimiento: " + currentTask.getExpirationDay() + "/" +
                (currentTask.getExpirationMonth()+1) + "/" + currentTask.getExpirationYear());
        if (currentTask.getEstimatedTimeMinutes() != 0){
            textTaskEstimatedTime.setText("Tiempo estimado: " + (int)currentTask.getEstimatedTimeHours()
                    + "h " + (int)currentTask.getEstimatedTimeMinutes() + "min");
        } else if (currentTask.getEstimatedTimeHours() != 0){
            textTaskEstimatedTime.setText("Tiempo estimado: " + (int)currentTask.getEstimatedTimeHours()
                    + "h");
        } else {
            textTaskEstimatedTime.setText("Tiempo estimado");
        }
        //Text Size
        textTaskName.setTextSize(textTaskName.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textTaskPriority.setTextSize(textTaskPriority.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textTaskCategory.setTextSize(textTaskCategory.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textTaskTime.setTextSize(textTaskTime.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textTaskEstimatedTime.setTextSize(textTaskEstimatedTime.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        //Text Color
        textTaskName.setTextColor(defaultTextSecondaryColor);
        textTaskPriority.setTextColor(defaultTextSecondaryColor);
        textTaskCategory.setTextColor(defaultTextSecondaryColor);
        textTaskTime.setTextColor(defaultTextSecondaryColor);
        textTaskEstimatedTime.setTextColor(defaultTextSecondaryColor);
        //InputText Setup
        inputTextAnnotations = (EditText) v.findViewById(R.id.inputAnnotation);
        inputNewSubTask = (EditText) v.findViewById(R.id.inputSubTaskName);
        inputEditTaskName = (EditText) v.findViewById(R.id.renameTask);
        GradientDrawable inputTextDrawable = (GradientDrawable) inputTextAnnotations.getBackground();
        GradientDrawable inputNewSubTaskDrawable = (GradientDrawable) inputNewSubTask.getBackground();
        GradientDrawable inputEditTaskNameDrawable = (GradientDrawable) inputEditTaskName.getBackground();
        if (gradientSecondaryColorActive){
            inputTextDrawable.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            inputNewSubTaskDrawable.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            inputEditTaskNameDrawable.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
        } else {
            inputTextDrawable.setColor(defaultSecondaryColor);
            inputNewSubTaskDrawable.setColor(defaultSecondaryColor);
            inputEditTaskNameDrawable.setColor(defaultSecondaryColor);
        }
        inputTextDrawable.setStroke(defaultButtonRadius, defaultSecondaryColor);
        inputNewSubTaskDrawable.setStroke(defaultButtonRadius, defaultSecondaryColor);
        inputEditTaskNameDrawable.setStroke(defaultButtonRadius, defaultSecondaryColor);
        inputTextAnnotations.setBackground(inputTextDrawable);
        inputNewSubTask.setBackground(inputNewSubTaskDrawable);
        inputEditTaskName.setBackground(inputEditTaskNameDrawable);
        inputTextAnnotations.setTextColor(defaultTextMainColor);
        inputNewSubTask.setTextColor(defaultTextMainColor);
        inputEditTaskName.setTextColor(defaultTextMainColor);
        inputTextAnnotations.setTextSize(inputTextAnnotations.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        inputNewSubTask.setTextSize(inputNewSubTask.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        inputEditTaskName.setTextSize(inputEditTaskName.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        //Widgets Setup
        timePicker = (TimePicker) v.findViewById(R.id.timePickerEstimatedTime);
        timePicker.setIs24HourView(true);
        calendarDate = (CalendarView) v.findViewById(R.id.calendar_task_date);
        calendarDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                onDateChanged(year, month, dayOfMonth);
            }
        });
        GradientDrawable calendarDrawable = (GradientDrawable) getMainActivity().getDrawable(R.drawable.pet_layout_shape);
        if (gradientMainColorActive){
            calendarDrawable.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
        } else {
            calendarDrawable.setColor(defaultMainColor);
        }
        calendarDrawable.setStroke(defaultButtonRadius+3, defaultSecondaryColor);
        calendarDate.setBackground(calendarDrawable);
        calendarDate.setVisibility(View.INVISIBLE);
        calendarDate.setClickable(false);

        //Shadows in Texts setup
        if (shadowsActive){
            ((TextView)v.findViewById(R.id.subtasksTitle)).setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            ((TextView)v.findViewById(R.id.annotationsTitle)).setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            inputTextAnnotations.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            inputNewSubTask.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            inputEditTaskName.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            textTaskName.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            textTaskPriority.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            textTaskCategory.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            textTaskTime.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            textTaskEstimatedTime.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
        } else {
            ((TextView)v.findViewById(R.id.subtasksTitle)).setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            ((TextView)v.findViewById(R.id.annotationsTitle)).setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            inputTextAnnotations.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            inputNewSubTask.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            inputEditTaskName.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            textTaskName.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            textTaskPriority.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            textTaskCategory.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            textTaskTime.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            textTaskEstimatedTime.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
        }
    }
    //Prepara los botones
    private void setupButtons(View v){
        buttonClose = (Button) v.findViewById(R.id.buttonClose);
        buttonName = (Button) v.findViewById(R.id.buttonEditTask);
        buttonPomodoro = (Button) v.findViewById(R.id.buttonPomodoro);
        buttonNewSubTask = (Button) v.findViewById(R.id.buttonAddSubTask);
        buttonSetTime = (Button) v.findViewById(R.id.buttonTime);
        buttonClearSubTasks = (Button) v.findViewById(R.id.buttonRemoveSubTasks);
        buttonSubmitTime = (Button) v.findViewById(R.id.buttonSubmitEstimatedTime);
        buttonSubmitAnnotations = (Button) v.findViewById(R.id.buttonAddAnnotations);
        buttonChangeDate = (Button) v.findViewById(R.id.button_date);
        GradientDrawable drawableClose = (GradientDrawable) buttonClose.getBackground();
        GradientDrawable drawableName = (GradientDrawable) buttonName.getBackground();
        GradientDrawable drawablePomodoro = (GradientDrawable) buttonPomodoro.getBackground();
        GradientDrawable drawableNewSubTask = (GradientDrawable) buttonNewSubTask.getBackground();
        GradientDrawable drawableSetTime = (GradientDrawable) buttonSetTime.getBackground();
        GradientDrawable drawableClearSubTasks = (GradientDrawable) buttonClearSubTasks.getBackground();
        GradientDrawable drawableSubmitTime = (GradientDrawable) buttonSubmitTime.getBackground();
        GradientDrawable drawableSubmitAnnotations = (GradientDrawable) buttonSubmitAnnotations.getBackground();
        GradientDrawable drawableChangeDate = (GradientDrawable) buttonChangeDate.getBackground();
        if (gradientSecondaryColorActive){
            drawableClose.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            drawableName.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            drawablePomodoro.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            drawableNewSubTask.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            drawableSetTime.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            drawableClearSubTasks.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            drawableSubmitTime.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            drawableSubmitAnnotations.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
            drawableChangeDate.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
        } else {
            drawableClose.setColor(defaultSecondaryColor);
            drawableName.setColor(defaultSecondaryColor);
            drawablePomodoro.setColor(defaultSecondaryColor);
            drawableNewSubTask.setColor(defaultSecondaryColor);
            drawableSetTime.setColor(defaultSecondaryColor);
            drawableClearSubTasks.setColor(defaultSecondaryColor);
            drawableSubmitTime.setColor(defaultSecondaryColor);
            drawableSubmitAnnotations.setColor(defaultSecondaryColor);
            drawableChangeDate.setColor(defaultSecondaryColor);
        }
        if (bordersActive){
            drawableClose.setStroke(defaultButtonRadius, defaultBackgroundColor);
            drawableName.setStroke(defaultButtonRadius, defaultBackgroundColor);
            drawablePomodoro.setStroke(defaultButtonRadius, defaultBackgroundColor);
            drawableNewSubTask.setStroke(defaultButtonRadius, defaultBackgroundColor);
            drawableSetTime.setStroke(defaultButtonRadius, defaultBackgroundColor);
            drawableClearSubTasks.setStroke(defaultButtonRadius, defaultBackgroundColor);
            drawableSubmitTime.setStroke(defaultButtonRadius, defaultBackgroundColor);
            drawableSubmitAnnotations.setStroke(defaultButtonRadius, defaultBackgroundColor);
            drawableChangeDate.setStroke(defaultButtonRadius, defaultBackgroundColor);
        } else {
            drawableClose.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableName.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawablePomodoro.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableNewSubTask.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableSetTime.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableClearSubTasks.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableSubmitTime.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableSubmitAnnotations.setStroke(defaultButtonRadius, defaultSecondaryColor);
            drawableChangeDate.setStroke(defaultButtonRadius, defaultSecondaryColor);
        }
        buttonClose.setBackground(drawableClose);
        buttonName.setBackground(drawableName);
        buttonPomodoro.setBackground(drawablePomodoro);
        buttonNewSubTask.setBackground(drawableNewSubTask);
        buttonSetTime.setBackground(drawableSetTime);
        buttonClearSubTasks.setBackground(drawableClearSubTasks);
        buttonSubmitTime.setBackground(drawableSubmitTime);
        buttonSubmitAnnotations.setBackground(drawableSubmitAnnotations);
        buttonChangeDate.setBackground(drawableChangeDate);
        buttonClose.setTextColor(defaultTextMainColor);
        buttonName.setTextColor(defaultTextMainColor);
        buttonPomodoro.setTextColor(defaultTextMainColor);
        buttonNewSubTask.setTextColor(defaultTextMainColor);
        buttonSetTime.setTextColor(defaultTextMainColor);
        buttonClearSubTasks.setTextColor(defaultTextMainColor);
        buttonSubmitTime.setTextColor(defaultTextMainColor);
        buttonSubmitAnnotations.setTextColor(defaultTextMainColor);
        buttonChangeDate.setTextColor(defaultTextMainColor);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickClose();
            }
        });
        buttonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickName();
            }
        });
        buttonPomodoro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPomodoro();
            }
        });
        buttonNewSubTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNewSubTask();
            }
        });
        buttonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSetTime();
            }
        });
        buttonClearSubTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickClear();
            }
        });
        buttonSubmitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubmitTime();
            }
        });
        buttonSubmitAnnotations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onClickSubmitAnnotations();
            }
        });
        buttonChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickChangeDate();
            }
        });
        if (shadowsActive){
            buttonClose.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            buttonName.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            buttonPomodoro.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            buttonNewSubTask.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            buttonSetTime.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            buttonClearSubTasks.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            buttonSubmitTime.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            buttonSubmitAnnotations.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            buttonChangeDate.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
        } else {
            buttonClose.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            buttonName.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            buttonPomodoro.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            buttonNewSubTask.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);;
            buttonSetTime.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            buttonClearSubTasks.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            buttonSubmitTime.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            buttonSubmitAnnotations.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            buttonChangeDate.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
        }
    }
    //Prepara los Spinners
    private void setupSpinners(View v){
        spinnerPriority = (Spinner) v.findViewById(R.id.spinner_priority);
        spinnerCategory = (Spinner) v.findViewById(R.id.spinner_category);

        spinnerPriority.setSelection(currentTask.getPriorityIndex());
        spinnerCategory.setSelection(currentTask.getCategoryIndex());

        GradientDrawable drawableSpinner = (GradientDrawable) getMainActivity().getDrawable(R.drawable.button3_shape);

        if (gradientSecondaryColorActive){
            drawableSpinner.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
        } else {
            drawableSpinner.setColor(defaultSecondaryColor);
        }

        if (bordersActive){
            drawableSpinner.setStroke(defaultButtonRadius, defaultBackgroundColor);
        } else {
            drawableSpinner.setStroke(defaultButtonRadius, defaultSecondaryColor);
        }

        spinnerPriority.setBackground(drawableSpinner);
        spinnerCategory.setBackground(drawableSpinner);

        spinnerPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentTask.setPriorityAsString(String.valueOf(spinnerPriority.getSelectedItem()));
                fatherFragment.updateTaskInDatabase(currentTask);
                textTaskPriority.setText("Prioridad: " + currentTask.getPriorityAsString());
                ((TextView)parent.getChildAt(0)).setTextColor(defaultTextMainColor);
                ((TextView)parent.getChildAt(0)).setTextSize(12);
                if (shadowsActive){
                    ((TextView)parent.getChildAt(0)).setShadowLayer(defaultShadowRadius,
                            defaultShadowDx, defaultShadowDy, defaultTextMainColor);
                } else {
                    ((TextView)parent.getChildAt(0)).setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onChangeCategory(String.valueOf(spinnerCategory.getSelectedItem()));
                ((TextView)parent.getChildAt(0)).setTextColor(defaultTextMainColor);
                ((TextView)parent.getChildAt(0)).setTextSize(12);
                if (shadowsActive){
                    ((TextView)parent.getChildAt(0)).setShadowLayer(defaultShadowRadius,
                            defaultShadowDx, defaultShadowDy, defaultTextMainColor);
                } else {
                    ((TextView)parent.getChildAt(0)).setShadowLayer(0,
                            0, 0, defaultTextMainColor);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    //Inicializa las Views de subtareas
    private void initSubTaskViews(){
        for (SubTask st : currentTask.getSubTasksList()){
            submit(st.getName(), st.getIdInView(), st.isFinished());
        }
    }
    //Inicializa las Views de anotaciones
    private void initAnnotationViews(){
        for (Annotation ant : currentTask.getAnnotations()){
            newAnnotation(ant.getContent(), ant.getIdInView());
        }
    }

    //Crea una nueva subtarea y la añade al layout
    private void submit(String subTaskName, int index, boolean finished){
        final int id = index;
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView textView = new TextView(getActivity());
        textView.setTextSize(18);
        textView.setTextSize(textView.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textView.setTextColor(defaultTextSecondaryColor);
        if (shadowsActive){
            textView.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
        } else {
            textView.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
        }
        textView.setText(subTaskName);
        textView.setId(index);
        textView.setPadding(25, 0, 0, 0);
        textView.setClickable(true);
        textView.setFocusableInTouchMode(false);
        CheckBox checkBox = new CheckBox(getActivity());
        if (finished){
            checkBox.setChecked(true);
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    finishTask(id, true);
                } else {
                    finishTask(id, false);
                }
            }
        });
        linearLayout.addView(checkBox);
        linearLayout.addView(textView);
        subTasksLayout.addView(linearLayout);
        subTasksLayout.invalidate();
    }
    //Crea una nueva anotación y la añade al layout
    private void newAnnotation(String content, int idInView){
        TextView annotationText = new TextView(getMainActivity());
        annotationText.setText(content);
        annotationText.setTextSize(18);
        annotationText.setTextSize(annotationText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        annotationText.setTextColor(defaultTextSecondaryColor);
        annotationText.setId(idInView);
        if (shadowsActive){
            annotationText.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultSecondaryColor);
        } else {
            annotationText.setShadowLayer(0, 0, 0, defaultTextMainColor);
        }
        annotationsLayout.addView(annotationText);
        annotationText.setPadding(15, 0, 0, 0);
    }
    //endregion

    //region Gestión de subtareas
    //Recibe el paquete e inicializa el objeto
    private void initiateObjects(){
        Bundle bundle = getArguments();
        currentTask = (Task) bundle.getSerializable("currentTask");
        fatherFragment = (TasksFragment) bundle.getSerializable("fragmentReference");
        fatherFragment.getSubTaskList(currentTask);
        fatherFragment.getAnnotationList(currentTask);
        subTasksNumber = currentTask.getSubTasksListSize();
        annotationsNumber = currentTask.getAnnotationsListSize();
        isInputEditVisible = false;
    }

    //Añade una nueva subtarea a la lista
    private void createNewSubTask() {
        String inputContent = inputNewSubTask.getText().toString();
        inputNewSubTask.getText().clear();
        if (inputContent != null && !inputContent.trim().isEmpty()) {
            fatherFragment.saveSubTaskInDatabase(currentTask, inputContent, subTasksNumber);
            fatherFragment.updateTaskInDatabase(currentTask);
            submit(inputContent, subTasksNumber, false);
            subTasksNumber++;
        } else {
            ((MainActivity)getActivity()).toastMessage("La tarea debe tener un nombre");
        }
    }
    //Marca como finalizada una subtarea
    private void finishTask(int index, boolean finished){
        currentTask.finishSubTask(index, finished);
        fatherFragment.updateSubTaskInDatabase(currentTask.getSubTaskById(index));
        TextView subTaskText = (TextView) getView().findViewById(index);
        if (finished){
            subTaskText.setPaintFlags(subTaskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            subTaskText.setPaintFlags(subTaskText.getPaintFlags() & ~ Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
    //endregion

    //region Eventos
    private void onClickClose(){
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void onClickName(){
        if (!isInputEditVisible){
            inputEditTaskName.setVisibility(View.VISIBLE);
            inputEditTaskName.requestFocus();
            buttonName.setText("Listo");
            isInputEditVisible = true;
        } else {
            String inputContent = inputEditTaskName.getText().toString();
            if (inputContent != null && !inputContent.trim().isEmpty()){
                textTaskName.setText(inputContent);
                currentTask.setName(inputContent);
                fatherFragment.updateTaskInDatabase(currentTask);
                fatherFragment.clearFinishedTasks();
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                inputEditTaskName.clearFocus();
                inputEditTaskName.setVisibility(View.INVISIBLE);
                buttonName.setText("Cambiar nombre");
            } else {
                ((MainActivity)getActivity()).toastMessage("La tarea debe tener un nombre");
            }
            isInputEditVisible = false;
        }
    }

    private void onChangeCategory(String category){
        if (category != "Nueva"){
            currentTask.setCategory(category);
            fatherFragment.updateTaskInDatabase(currentTask);
            textTaskCategory.setText("Categoría:" + currentTask.getCategory());
        } else {
            onAddNewCategory();
        }
    }

    private void onAddNewCategory(){

    }

    private void onClickPomodoro(){
        Bundle bundle = new Bundle();
        bundle.putInt("minutes", (int)currentTask.getEstimatedTimeMinutes());
        bundle.putInt("hours", (int)currentTask.getEstimatedTimeHours());
        getMainActivity().goToFragment(new TimerFragment(), R.id.timer_view, bundle);
    }

    private void onClickNewSubTask(){
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        inputNewSubTask.clearFocus();
        createNewSubTask();
    }

    private void onClickSetTime(){
        buttonSubmitTime.setClickable(true);
        buttonSubmitTime.setVisibility(View.VISIBLE);

        timePicker.setVisibility(View.VISIBLE);
        timePicker.setClickable(true);
        GradientDrawable timePickerDrawable = (GradientDrawable) timePicker.getBackground();
        if (gradientMainColorActive){
            timePickerDrawable.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
        } else {
            timePickerDrawable.setColor(defaultMainColor);
        }
        timePickerDrawable.setStroke(defaultButtonRadius+3, defaultSecondaryColor);
        timePicker.setBackground(timePickerDrawable);
    }

    private void onClickSubmitTime(){
        buttonSubmitTime.setClickable(false);
        buttonSubmitTime.setVisibility(View.INVISIBLE);

        currentTask.setEstimatedTime(timePicker.getHour(), timePicker.getMinute());
        fatherFragment.updateTaskInDatabase(currentTask);

        if (currentTask.getEstimatedTimeMinutes() != 0){
            textTaskEstimatedTime.setText("Tiempo estimado: " + (int)currentTask.getEstimatedTimeHours()
                    + "h " + (int)currentTask.getEstimatedTimeMinutes() + "min");
        } else if (currentTask.getEstimatedTimeHours() != 0){
            textTaskEstimatedTime.setText("Tiempo estimado: " + (int)currentTask.getEstimatedTimeHours()
                    + "h");
        } else {
            textTaskEstimatedTime.setText("Tiempo estimado");
        }
        timePicker.setClickable(false);
        timePicker.setVisibility(View.INVISIBLE);
    }

    private void onClickSubmitAnnotations(){
        String inputContent = inputTextAnnotations.getText().toString();
        inputTextAnnotations.getText().clear();
        if (inputContent != null && !inputContent.trim().isEmpty()) {
            fatherFragment.saveAnnotationInDatabase(currentTask, inputContent, annotationsNumber);
            fatherFragment.updateTaskInDatabase(currentTask);
            newAnnotation(inputContent, annotationsNumber);
            annotationsNumber++;
        } else {
            ((MainActivity)getActivity()).toastMessage("La anotación no puede estar vacía");
        }
    }

    private void onClickClear(){
        subTasksLayout.removeAllViewsInLayout();
        fatherFragment.clearSubTasksInDatabase(currentTask);
        subTasksNumber = 0;
        for (SubTask st: currentTask.getSubTasksList()){
            st.setIdInView(subTasksNumber);
            fatherFragment.updateSubTaskInDatabase(st);
            submit(st.getName(), subTasksNumber, st.isFinished());
            subTasksNumber++;
        }
    }

    private void onDateChanged(int year, int month, int day){
        Calendar date = Calendar.getInstance();
        date.set(year, month, day);
        currentTask.setExpirationDate(date);
        fatherFragment.updateTaskInDatabase(currentTask);
        fatherFragment.clearFinishedTasks();
        calendarDate.setVisibility(View.INVISIBLE);
        calendarDate.setClickable(false);
        textTaskTime.setText("Vencimiento: " + day + "/" + (month+1) + "/" + year);
        getMainActivity().toastMessage("Fecha cambiada");
    }

    private void onClickChangeDate(){
        calendarDate.setVisibility(View.VISIBLE);
        calendarDate.setClickable(true);
    }
    //endregion

}
