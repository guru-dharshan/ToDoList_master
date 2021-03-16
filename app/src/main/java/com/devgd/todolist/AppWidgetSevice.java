package com.devgd.todolist;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.ArrayList;
import java.util.List;

import static com.devgd.todolist.NewAppWidget.EXTRA_POS;

public class AppWidgetSevice extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetFactory(getApplication(),intent);
    }

    class WidgetFactory implements RemoteViewsFactory{
        private List<TodoTable> todoTableList= new ArrayList<>();
       // private TodoRoom db;
        int appwidid;
        private Context context;
        private TaskDao taskDao;
        public WidgetFactory(Context context,Intent intent){
            this.context=context;
            //db=TodoRoom.getInstance(context);
            //taskDao=db.taskDao();
            this.appwidid=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,AppWidgetManager.INVALID_APPWIDGET_ID);
        }


        @Override
        public void onCreate() {
            todoTableList=MainActivity.getwidgetlist();
        }

        @Override
        public void onDataSetChanged() {
            todoTableList=MainActivity.getwidgetlist();


        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return todoTableList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.i("service class", String.valueOf(appwidid));
            TodoTable todoTable = todoTableList.get(position);
            RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widgetlistviewitem);
            views.setTextViewText(R.id.widget_item_task,todoTableList.get(position).getTask());
            views.setTextViewText(R.id.widget_item_date,todoTableList.get(position).getDate());
            if(todoTableList.get(position).getCheckk()==1) {
                views.setInt(R.id.layout, "setBackgroundResource", R.color.completed);
            }
            if(todoTableList.get(position).getCheckk()==0) {
                views.setInt(R.id.layout, "setBackgroundResource", R.color.white);
            }

            Intent fillintent = new Intent();
            fillintent.putExtra(EXTRA_POS,position);

            fillintent.putExtra("task",todoTable.getTask());
            fillintent.putExtra("priority",todoTable.getPriority());
            fillintent.putExtra("date",todoTable.getDate());
            fillintent.putExtra("id",todoTable.getId());
            fillintent.putExtra("year",todoTable.getYear());
            fillintent.putExtra("month",todoTable.getMonth());
            fillintent.putExtra("day",todoTable.getDay());
            views.setOnClickFillInIntent(R.id.widget_item_task,fillintent);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
