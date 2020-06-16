package com.example.testingtfg.ui.task;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.testingtfg.BaseFragment;
import com.example.testingtfg.MainActivity;
import com.example.testingtfg.R;
import com.example.testingtfg.taskOrganizer.SubTask;
import com.example.testingtfg.taskOrganizer.Task;
import com.example.testingtfg.taskOrganizer.TaskManager;

import java.io.Serializable;

/*Clase que gestiona el Fragment de las tareas*/
public class TasksFragment extends BaseFragment implements Serializable {

    //region Constantes
    private TasksViewModel tasksViewModel;
    private final String TITLE_PENDING_TASKS = "Tienes tareas atrasadas";
    private final String TITLE_TODAY_TASKS = "Tienes tareas que hacer hoy";
    //endregion
    //region Parámetros Visuales
    private Button newTaskButton;
    private Button dateTaskButton;
    private Button clearTasksButton;
    private EditText newTaskInputField;
    private LinearLayout todayLayout;
    private LinearLayout tomorrowLayout;
    private LinearLayout weekLayout;
    private LinearLayout laterLayout;
    private LinearLayout expiredLayout;
    private CalendarView newTaskCalendar;
    private View root;
    //endregion
    //region Parámetros funcionales
    private TaskManager taskManager;
    private int tasksNumber = 0;
    //endregion

    //region Métodos Visuales
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tasksViewModel = ViewModelProviders.of(this).get(TasksViewModel.class);
        root = inflater.inflate(R.layout.fragment_tasks, container, false);
        taskManager = new TaskManager(getActivity());
        tasksNumber = taskManager.getTasksListSize();
        loadColors(root);
        setupLists(root);
        newTaskSetup(root);
        initTaskViews();
        updateTaskViews();
        notifyPendingTasks();
        return root;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    //Inicializa las Views de las tareas
    private void initTaskViews(){
        for (Task t : taskManager.getTasksList()){
            submit(t.getName(), t.getIdInView(), t.getTimeForTask(), t.isFinished());
        }
    }

    //Cambia una tarea en el layout cuando se hace un cambio
    public void updateTaskViews(){
        for (Task task : taskManager.getTasksList()){
            TextView textToChange = (TextView) root.findViewById(task.getIdInView());
            if (textToChange == null){
                submit(task.getName(), task.getIdInView(), task.getTimeForTask(), task.isFinished());
            } else {
                textToChange.setText(task.getName());
            }
        }
    }

    //Prepara las listas
    private void setupLists(View v) {
        setupBackground(v);
        Window window = getMainActivity().getWindow();
        window.setStatusBarColor(defaultSecondaryColor);
        getMainActivity().getNavView().setBackgroundColor(defaultSecondaryColor);
        getMainActivity().getNavView().setItemIconTintList(ColorStateList.valueOf(defaultMainColor));
        getMainActivity().getNavView().setItemTextColor(ColorStateList.valueOf(defaultMainColor));
        getMainActivity().getSupportActionBar().setBackgroundDrawable(new ColorDrawable(defaultMainColor));

        todayLayout = (LinearLayout) v.findViewById(R.id.todayList);
        tomorrowLayout = (LinearLayout) v.findViewById(R.id.tomorrowList);
        weekLayout = (LinearLayout) v.findViewById(R.id.weeklyList);
        laterLayout = (LinearLayout) v.findViewById(R.id.laterList);
        expiredLayout = (LinearLayout) v.findViewById(R.id.expiredList);
        ImageView todayImg = (ImageView) v.findViewById(R.id.img_task_today);
        ImageView tomorrowImg = (ImageView) v.findViewById(R.id.img_task_tomorrow);
        ImageView weekImg = (ImageView) v.findViewById(R.id.img_task_week);
        ImageView laterImg = (ImageView) v.findViewById(R.id.img_task_later);
        ImageView lateImg = (ImageView) v.findViewById(R.id.img_task_late);
        ((Drawable)todayImg.getBackground()).setTint(defaultTextMainColor);
        ((Drawable)tomorrowImg.getBackground()).setTint(defaultTextMainColor);
        ((Drawable)weekImg.getBackground()).setTint(defaultTextMainColor);
        ((Drawable)laterImg.getBackground()).setTint(defaultTextMainColor);
        ((Drawable)lateImg.getBackground()).setTint(defaultTextMainColor);

        GradientDrawable buttonDrawable = (GradientDrawable) getActivity().getDrawable(R.drawable.button2_shape);
        if (gradientMainColorActive){
            buttonDrawable.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
        } else {
            buttonDrawable.setColor(defaultMainColor);
        }
        if (bordersActive){
            buttonDrawable.setStroke(defaultButtonRadius, defaultSecondaryColor);
        } else {
            buttonDrawable.setStroke(defaultButtonRadius, defaultMainColor);
        }
        ((TextView)v.findViewById(R.id.todayTasks)).setTextColor(defaultTextMainColor);
        ((TextView)v.findViewById(R.id.tomorrowTasks)).setTextColor(defaultTextMainColor);
        ((TextView)v.findViewById(R.id.weeklyTasks)).setTextColor(defaultTextMainColor);
        ((TextView)v.findViewById(R.id.laterTasks)).setTextColor(defaultTextMainColor);
        ((TextView)v.findViewById(R.id.lateTasks)).setTextColor(defaultTextMainColor);
        ((TextView)v.findViewById(R.id.todayTasks)).setTextSize(((TextView)v.findViewById(R.id.todayTasks))
                .getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        ((TextView)v.findViewById(R.id.tomorrowTasks)).setTextSize(((TextView)v.findViewById(R.id.tomorrowTasks))
                .getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        ((TextView)v.findViewById(R.id.weeklyTasks)).setTextSize(((TextView)v.findViewById(R.id.weeklyTasks))
                .getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        ((TextView)v.findViewById(R.id.laterTasks)).setTextSize(((TextView)v.findViewById(R.id.laterTasks))
                .getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        ((TextView)v.findViewById(R.id.lateTasks)).setTextSize(((TextView)v.findViewById(R.id.lateTasks))
                .getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        if (shadowsActive){
            ((TextView)v.findViewById(R.id.todayTasks)).setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            ((TextView)v.findViewById(R.id.tomorrowTasks)).setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            ((TextView)v.findViewById(R.id.weeklyTasks)).setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            ((TextView)v.findViewById(R.id.laterTasks)).setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            ((TextView)v.findViewById(R.id.lateTasks)).setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
        } else {
            ((TextView)v.findViewById(R.id.todayTasks)).setShadowLayer(0,0,0, defaultTextMainColor);
            ((TextView)v.findViewById(R.id.tomorrowTasks)).setShadowLayer(0,0,0, defaultTextMainColor);
            ((TextView)v.findViewById(R.id.weeklyTasks)).setShadowLayer(0,0,0, defaultTextMainColor);
            ((TextView)v.findViewById(R.id.laterTasks)).setShadowLayer(0,0,0, defaultTextMainColor);
            ((TextView)v.findViewById(R.id.lateTasks)).setShadowLayer(0,0,0, defaultTextMainColor);
        }
    }

    //Inicializa todas las Vies para el menú de nueva tarea
    private void newTaskSetup(View v) {
        newTaskButton = (Button) v.findViewById(R.id.button_addTask);
        dateTaskButton = (Button) v.findViewById(R.id.button_taskDate);
        clearTasksButton = (Button) v.findViewById(R.id.buttonRemoveTasks);
        newTaskCalendar = (CalendarView) v.findViewById(R.id.calendar_task);
        GradientDrawable buttonNewTaskDrawable = (GradientDrawable) newTaskButton.getBackground();
        GradientDrawable buttonDateTaskDrawable = (GradientDrawable) dateTaskButton.getBackground();
        GradientDrawable buttonClearTasksDrawable = (GradientDrawable) clearTasksButton.getBackground();
        final GradientDrawable calendarDrawable = (GradientDrawable) newTaskCalendar.getBackground();
        GradientDrawable inputTextDrawable = (GradientDrawable) getActivity().getDrawable(R.drawable.button3_shape);
        if (gradientMainColorActive){
            buttonNewTaskDrawable.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
            buttonDateTaskDrawable.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
            buttonClearTasksDrawable.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
            calendarDrawable.setColors(new int[]{defaultMainGradientColor1,
                    defaultMainGradientColor2, defaultMainGradientColor3});
        } else {
            buttonNewTaskDrawable.setColor(defaultMainColor);
            buttonDateTaskDrawable.setColor(defaultMainColor);
            buttonClearTasksDrawable.setColor(defaultMainColor);
            calendarDrawable.setColor(defaultMainColor);
        }
        if (bordersActive){
            buttonNewTaskDrawable.setStroke(defaultButtonRadius, defaultSecondaryColor);
            buttonDateTaskDrawable.setStroke(defaultButtonRadius, defaultSecondaryColor);
            buttonClearTasksDrawable.setStroke(defaultButtonRadius, defaultSecondaryColor);
        } else {
            buttonNewTaskDrawable.setStroke(defaultButtonRadius, defaultMainColor);
            buttonDateTaskDrawable.setStroke(defaultButtonRadius, defaultMainColor);
            buttonClearTasksDrawable.setStroke(defaultButtonRadius, defaultMainColor);
        }
        calendarDrawable.setStroke(defaultButtonRadius, defaultSecondaryColor);
        newTaskCalendar.setBackground(calendarDrawable);
        newTaskCalendar.setVisibility(View.GONE);

        if (gradientSecondaryColorActive){
            inputTextDrawable.setColors(new int[]{defaultSecondaryGradientColor1,
                    defaultSecondaryGradientColor2, defaultSecondaryGradientColor3});
        } else {
            inputTextDrawable.setColor(defaultSecondaryColor);
        }

        inputTextDrawable.setStroke(defaultButtonRadius, defaultSecondaryColor);

        newTaskInputField = (EditText) v.findViewById(R.id.inputTaskName);
        newTaskInputField.setBackground(inputTextDrawable);
        newTaskInputField.setTextColor(defaultTextMainColor);
        newTaskInputField.setTextSize(newTaskInputField.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        newTaskButton.setBackground(buttonNewTaskDrawable);
        newTaskButton.setTextColor(defaultTextSecondaryColor);
        newTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                newTaskInputField.clearFocus();
                newTaskButton.requestFocus();
                createNewTask();
                taskManager.resetDate();
            }
        });
        dateTaskButton.setBackground(buttonDateTaskDrawable);
        dateTaskButton.setTextColor(defaultTextSecondaryColor);
        dateTaskButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newTaskCalendar.setVisibility(View.VISIBLE);
            }
        });
        newTaskCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                taskManager.stablishDate(year, month, dayOfMonth);
                newTaskCalendar.setVisibility(View.GONE);
            }
        });
        clearTasksButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                taskManager.clearFinishedTasks();
                clearFinishedTasks();
                getMainActivity().toastMessage("Tienes " + getMainActivity().getVirtualPetManager().getPlayPoints() + " fichas de juego");
            }
        });
        clearTasksButton.setBackground(buttonClearTasksDrawable);
        clearTasksButton.setTextColor(defaultTextSecondaryColor);
        if (shadowsActive){
            newTaskInputField.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
            newTaskButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            dateTaskButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
            clearTasksButton.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextSecondaryColor);
        } else {
            newTaskInputField.setShadowLayer(0,0,0, defaultTextMainColor);
            newTaskButton.setShadowLayer(0,0,0, defaultTextSecondaryColor);
            dateTaskButton.setShadowLayer(0,0,0, defaultTextSecondaryColor);
            clearTasksButton.setShadowLayer(0,0,0, defaultTextSecondaryColor);
        }
    }

    //Cuando una nueva tarea se crea, se añade al layout
    private void submit(String content, int index, Task.timeTag timeTag, boolean finished) {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView textView = new TextView(getActivity());
        textView.setTextSize(18);
        textView.setTextSize(textView.getTextSize()/getResources().getDisplayMetrics().scaledDensity+defaultTextSizeChange);
        textView.setTextColor(defaultTextMainColor);
        if (shadowsActive){
            textView.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
        } else {
            textView.setShadowLayer(0,0,0, defaultTextMainColor);
        }
        textView.setText(content);
        textView.setId(index);
        textView.setPadding(25, 0, 0, 0);
        textView.setClickable(true);
        textView.setFocusableInTouchMode(false);
        final int id = index;
        final String ct = content;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToEditTask(id);
            }
        });

        CheckBox checkBox = new CheckBox(getActivity());
        checkBox.setTextColor(defaultTextMainColor);
        checkBox.setShadowLayer(defaultShadowRadius, defaultShadowDx, defaultShadowDy, defaultTextMainColor);
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

        switch (timeTag){
            case TODAY:
                todayLayout.addView(linearLayout);
                todayLayout.invalidate();
                break;
            case TOMORROW:
                tomorrowLayout.addView(linearLayout);
                tomorrowLayout.invalidate();
                break;
            case THIS_WEEK:
                weekLayout.addView(linearLayout);
                weekLayout.invalidate();
                break;
            case LATER:
                laterLayout.addView(linearLayout);
                laterLayout.invalidate();
                break;
            case EXPIRED:
                expiredLayout.addView(linearLayout);
                expiredLayout.invalidate();
                break;
            default:
                break;
        }
    }

    //Limpia las listas de tareas
    public void clearFinishedTasks(){
        todayLayout.removeAllViewsInLayout();
        tomorrowLayout.removeAllViewsInLayout();
        weekLayout.removeAllViewsInLayout();
        laterLayout.removeAllViewsInLayout();
        expiredLayout.removeAllViewsInLayout();
        tasksNumber = 0;
        taskManager.updateTasksTimeTag();
        for (Task t : taskManager.getTasksList()){
            t.setIdInView(tasksNumber);
            updateTaskInDatabase(t);
            submit(t.getName(), tasksNumber, t.getTimeForTask(), t.isFinished());
            tasksNumber++;
        }
    }
    //endregion

    //region Gestión de Tareas
    //Añade una nueva tarea a la lista
    private void createNewTask() {
        String inputContent = newTaskInputField.getText().toString();
        newTaskInputField.getText().clear();
        if (inputContent != null && !inputContent.trim().isEmpty()) {
            taskManager.createNewTask(inputContent, tasksNumber);
            taskManager.updateTasksTimeTag();
            submit(inputContent, tasksNumber, taskManager.getTaskById(tasksNumber).getTimeForTask(), false);
            tasksNumber++;
        } else {
            ((MainActivity)getActivity()).toastMessage("La tarea debe tener un nombre");
        }
    }
    //Finaliza una tarea: cambia su boolean y la marca
    private void finishTask(int idOfTask, boolean finished){
        Task taskToEdit = taskManager.getTaskById(idOfTask);
        taskToEdit.setFinished(finished);
        updateTaskInDatabase(taskToEdit);
        TextView taskText = (TextView) getView().findViewById(idOfTask);
        if (finished){
            taskText.setPaintFlags(taskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            taskText.setPaintFlags(taskText.getPaintFlags() & ~ Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
    //endregion

    //region Navegación
    //Abre el menú de edición de tareas
    private void jumpToEditTask(int index){
        Fragment fragmentEdit = new TaskEditorFragment();//New fragment
        Bundle args = new Bundle();//Bundle that contains the data to send to new Fragment
        Task taskToEdit = taskManager.getTaskById(index);//Task to edit
        args.putSerializable("currentTask", taskToEdit);//Fill bundle with task
        args.putSerializable("fragmentReference", this);
        fragmentEdit.setArguments(args);//Send bundle
        FragmentTransaction fragTran = getActivity().getSupportFragmentManager().beginTransaction();
        fragTran.add(R.id.testingEditor, fragmentEdit, "TASK__EDITOR_FRAGMENT");
        fragTran.addToBackStack(null);
        fragTran.commit();
    }
    //Notificaciones
    private void notifyPendingTasks(){
        if (taskManager.getNumberOfTasks(Task.timeTag.EXPIRED) > 0){
            getMainActivity().notifyMessage(TITLE_PENDING_TASKS, "Hay "
            + taskManager.getNumberOfTasks(Task.timeTag.EXPIRED) + " tareas atrasadas", 0);
        }
        if (taskManager.getNumberOfTasks(Task.timeTag.TODAY) > 0){
            getMainActivity().notifyMessage(TITLE_TODAY_TASKS, "Hay "
                    + taskManager.getNumberOfTasks(Task.timeTag.TODAY) + " tareas que hacer hoy", 1);
        }
    }
    //endregion

    //region Gestión de Base de Datos
    public void updateTaskInDatabase(Task task){
        taskManager.editTask(task);
    }

    public void saveSubTaskInDatabase(Task task, String name, int indexView){
        taskManager.createNewSubTask(task, name, indexView);
    }

    public void saveAnnotationInDatabase(Task task, String content, int indexView){
        taskManager.createNewAnnotation(task, content, indexView);
    }

    public void getSubTaskList(Task task){
        taskManager.retrieveSubTaskList(task);
    }

    public void getAnnotationList(Task task){taskManager.retrieveAnnotationLists(task);}

    public void updateSubTaskInDatabase(SubTask subTask){
        taskManager.editSubTask(subTask);
    }

    public void deleteSubTaskInDatabase(SubTask subTask){
        taskManager.removeSubTask(subTask);
    }

    public void clearSubTasksInDatabase(Task task){
        taskManager.clearFinishedSubTasks(task);
    }
    //endregion
}
