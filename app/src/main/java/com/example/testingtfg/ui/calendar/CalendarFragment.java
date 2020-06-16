package com.example.testingtfg.ui.calendar;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.testingtfg.BaseFragment;
import com.example.testingtfg.MainActivity;
import com.example.testingtfg.R;
import com.example.testingtfg.taskOrganizer.CalendarManager;
import com.example.testingtfg.taskOrganizer.Event;
import com.example.testingtfg.taskOrganizer.Task;

import java.util.ArrayList;
import java.util.Calendar;

/*Clase que gestiona el Fragment del Calendario*/
public class CalendarFragment extends BaseFragment {

    //region Parámetros Funcionales
    private CalendarViewModel calendarViewModel;
    private CalendarManager calendarManager;
    private Calendar selectedDate;
    private String currentMonth;
    private final String TITLE_EVENT_PENDING = "Eventos importantes para hoy";
    //endregion
    //region Parámetros Visuales
    CalendarView calendar;
    TextView dateText;
    LinearLayout eventsLayout;
    LinearLayout tasksLayout;
    Button buttonNewEvent;
    EditText textInputEvent;
    //endregion

    //region Métodos Visuales
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        final TextView textView = root.findViewById(R.id.text_calendar);
        calendarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("");
            }
        });
        loadColors(root);
        initObjects();
        initViews(root);
        setDateLayouts();
        notifyEvents();
        return root;
    }

    //Inicializa todas las Views del Fragment
    private void initViews(View v){
        setupBackground(v);
        calendar = (CalendarView) v.findViewById(R.id.calendar_view);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                onCalendarDateChange(year, month, dayOfMonth);
            }
        });
        dateText = (TextView) v.findViewById(R.id.day_text);
        dateText.setTextColor(defaultTextMainColor);
        eventsLayout = (LinearLayout) v.findViewById(R.id.layout_events);
        ((TextView)v.findViewById(R.id.text_events)).setTextColor(defaultTextMainColor);
        tasksLayout = (LinearLayout) v.findViewById(R.id.layout_tasks);
        ((TextView)v.findViewById(R.id.text_tasks)).setTextColor(defaultTextMainColor);
        buttonNewEvent = (Button) v.findViewById(R.id.buttonNewEvent);
        buttonNewEvent.setTextColor(defaultTextSecondaryColor);
        buttonNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNewEvent();
            }
        });
        GradientDrawable drawableNewEvent = (GradientDrawable) buttonNewEvent.getBackground();
        if (gradientMainColorActive){
            drawableNewEvent.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
        } else {
            drawableNewEvent.setColor(defaultMainColor);
        }
        if (bordersActive){
            drawableNewEvent.setStroke(defaultButtonRadius, defaultSecondaryColor);
        } else {
            drawableNewEvent.setStroke(defaultButtonRadius, defaultMainColor);
        }
        GradientDrawable drawableInputText = (GradientDrawable) getMainActivity().getDrawable(R.drawable.button3_shape);
        if (gradientSecondaryColorActive){
            drawableInputText.setColors(new int[]{defaultSecondaryGradientColor1,
                defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
        } else {
            drawableInputText.setColor(defaultSecondaryColor);
        }
        drawableInputText.setStroke(defaultButtonRadius, defaultSecondaryColor);

        textInputEvent = (EditText) v.findViewById(R.id.inputEventName);
        textInputEvent.setBackground(drawableInputText);
        textInputEvent.setTextColor(defaultTextMainColor);
        if (shadowsActive){
            dateText.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            ((TextView)v.findViewById(R.id.text_events)).setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            ((TextView)v.findViewById(R.id.text_tasks)).setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            buttonNewEvent.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            textInputEvent.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
        } else {
            dateText.setShadowLayer(0, 0, 0, defaultTextMainColor);
            ((TextView)v.findViewById(R.id.text_events)).setShadowLayer(0, 0, 0, defaultTextMainColor);
            ((TextView)v.findViewById(R.id.text_tasks)).setShadowLayer(0, 0, 0, defaultTextMainColor);
            buttonNewEvent.setShadowLayer(0, 0, 0, defaultTextSecondaryColor);
            textInputEvent.setShadowLayer(0, 0, 0, defaultTextMainColor);
        }
        initTextSizes();
    }

    //Crea un nuevo evento en el layout
    private void createEvent(Event event){
        TextView eventTitle = new TextView(getMainActivity());
        eventTitle.setText(event.getTitle());
        eventTitle.setTextSize(20);
        eventTitle.setTextSize(eventTitle.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        eventTitle.setTextColor(defaultTextMainColor);
        if (shadowsActive){
            eventTitle.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
        } else {
            eventTitle.setShadowLayer(0, 0, 0, defaultTextMainColor);
        }
        eventsLayout.addView(eventTitle);
    }

    //Crea una nueva tarea en el layout
    private void createTask(Task task){
        TextView taskTitle = new TextView(getMainActivity());
        taskTitle.setText(task.getName());
        taskTitle.setTextSize(20);
        taskTitle.setTextSize(taskTitle.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        taskTitle.setTextColor(defaultTextMainColor);
        if (shadowsActive){
            taskTitle.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
        } else {
            taskTitle.setShadowLayer(0, 0, 0, defaultTextMainColor);
        }
        tasksLayout.addView(taskTitle);
    }

    //Escribe una fecha seleccionada y recoge las tareas y eventos para ese día
    private void setDateLayouts(){
        dateText.setText(selectedDate.get(Calendar.DAY_OF_MONTH) + " de " +
                currentMonth + " del " +
                selectedDate.get(Calendar.YEAR));
        ArrayList<Event> eventsFromDay = calendarManager.getEventsFromDate(selectedDate);
        ArrayList<Task> tasksFromDay = calendarManager.getTasksFromDate(selectedDate);
        for (Event event : eventsFromDay) {
            createEvent(event);
        }
        for (Task task : tasksFromDay) {
            createTask(task);
        }
    }

    //Limpia los layouts de tareas y eventos
    private void clearLayouts(){
        tasksLayout.removeAllViewsInLayout();
        eventsLayout.removeAllViewsInLayout();
    }

    //Inicialización de los tamaños de los textos
    private void initTextSizes(){
        dateText.setTextSize(dateText.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textInputEvent.setTextSize(textInputEvent.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
    }
    //endregion

    //region Métodos de CalendarManager
    //Inicializa los objetos
    private void initObjects(){
        selectedDate = Calendar.getInstance();
        parseCurrentMonth();
        calendarManager = new CalendarManager(getMainActivity());
    }

    //Recoge los meses numéricos y los traduce a palabra
    private void parseCurrentMonth(){
        switch(selectedDate.get(Calendar.MONTH)){
            case 0:
                currentMonth = "Enero";
                break;
            case 1:
                currentMonth = "Febrero";
                break;
            case 2:
                currentMonth = "Marzo";
                break;
            case 3:
                currentMonth = "Abril";
                break;
            case 4:
                currentMonth = "Mayo";
                break;
            case 5:
                currentMonth = "Junio";
                break;
            case 6:
                currentMonth = "Julio";
                break;
            case 7:
                currentMonth = "Agosto";
                break;
            case 8:
                currentMonth = "Septiembre";
                break;
            case 9:
                currentMonth = "Octubre";
                break;
            case 10:
                currentMonth = "Noviembre";
                break;
            case 11:
                currentMonth = "Diciembre";
                break;
            default:
                break;
        }
    }
    //endregion

    //region Eventos
    //Evento que se dispara al seleccionar una fecha en el calendario
    private void onCalendarDateChange(int year, int month, int dayOfMonth){
        selectedDate.set(year, month, dayOfMonth);
        parseCurrentMonth();
        clearLayouts();
        setDateLayouts();
    }
    //Evento que se dispara al pulsar en el botón de nuevo evento
    private void onClickNewEvent(){
        String inputContent = textInputEvent.getText().toString();
        textInputEvent.getText().clear();

        if (inputContent != null && !inputContent.trim().isEmpty()) {
            Event event = new Event(inputContent, selectedDate);
            calendarManager.addEvent(event);
            createEvent(event);
        } else {
            ((MainActivity)getActivity()).toastMessage("El evento debe tener un nombre");
        }
    }
    //Notificación de eventos
    private void notifyEvents(){
        int eventsForToday = calendarManager.getEventsFromDate(Calendar.getInstance()).size();
        if ( eventsForToday > 0){
            getMainActivity().notifyMessage(TITLE_EVENT_PENDING, "Tienes " +
                    eventsForToday + " eventos pendientes para hoy", 0);
        }
    }
    //endregion
}
