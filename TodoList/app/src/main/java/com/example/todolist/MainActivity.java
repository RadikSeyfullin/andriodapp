package com.example.todolist;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.scalified.fab.ActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends ListActivity {

    private CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/OpenSans-Light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        setContentView(R.layout.activity_main);

        final String[] projectsArray = new String[]{"Семья", "Работа", "Прочее"};
        ArrayList<Todo> todos = new ArrayList<>();
        todos.add(new Todo("Заплатить за квартиру", false, 0));
        todos.add(new Todo("Купить продукты", false, 0));
        todos.add(new Todo("Забрать обувь с ремонта", true, 0));
        todos.add(new Todo("Заполнить отчет", false, 1));
        todos.add(new Todo("Отправить документы", false, 1));
        todos.add(new Todo("Позвонить заказчику", false, 1));
        todos.add(new Todo("Позвонить другу", false, 2));
        todos.add(new Todo("Подготовиться к поездке", false, 2));


        ActionButton fab = (ActionButton) findViewById(R.id.action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddTodo.class);
                intent.putExtra("projects", projectsArray);
                startActivity(intent);
            }
        });
        mAdapter = new CustomAdapter(this);
        mAdapter.itemCount(todos.size());
        int check = 0;
        for (int i =0; i < todos.size(); i++) {
            if (todos.get(i).projectRef == check)
            {
                mAdapter.addSectionHeaderItem(projectsArray[check]);
                check++;
            }
            mAdapter.addItem(todos.get(i).text, todos.get(i).isCompleted, i);
        }
        setListAdapter(mAdapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
