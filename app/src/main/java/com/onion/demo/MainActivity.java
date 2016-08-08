package com.onion.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import com.onion.R;
import com.onion.library.BaseViewHolder;
import com.onion.library.ClazzBean;
import com.onion.library.ScheduleBean;
import com.onion.library.ScheduleAdapter;
import com.onion.library.ScheduleInfo;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv_schedule;
    ScheduleAdapter<ScheduleBean> adapter;
    //课程表中的字色 不是必须的
    //选中单元格字色
    final int textSelectColor = 0xffffffff;
    //未选中单元格字色
    final int textUnSelectColor = 0xff666666;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_schedule = (RecyclerView) findViewById(R.id.rv_schedule);

        adapter = new ScheduleAdapter<ScheduleBean>(R.layout.item_lesson_cell, null);
        adapter.setFullScreen(this);
        //插入标记 表头 索引都可以被标注
        adapter.insertSelectCell(0, 4);
        //插入空课位标记
        ScheduleBean scheduleBean = new ScheduleBean();
        scheduleBean.setBeanTag("3");
        adapter.insertLessonCell(1, 4, scheduleBean);
        //插入课
        scheduleBean = new ScheduleBean();
        scheduleBean.setBeanView("语文");
        adapter.insertLessonCell(1, 5, scheduleBean);
        //插入课与标记  建议继承封装
        scheduleBean = new ScheduleBean();
        scheduleBean.setBeanView("体育");
        scheduleBean.setBeanTag("8");
        adapter.insertSelectCell(6, 3);
        adapter.insertLessonCell(6, 3, scheduleBean);
        for (int i = 0; i < 7; i++) {//插入课
            scheduleBean = new ScheduleBean();
            scheduleBean.setBeanView("数学" + i);
            adapter.insertLessonCell(i + 1, i + 1, scheduleBean);
        }
        //可自定义课程信息显示方式
        adapter.setListener(new ScheduleAdapter.OnConverListener<ScheduleBean>() {
            @Override
            public void onConvert(BaseViewHolder helper, ScheduleInfo<ScheduleBean> item) {
                TextView textView = helper.getView(R.id.tv_info);
                ScheduleBean scheduleBean = item.getTagBean();
                if (scheduleBean != null) {
                    textView.setText(scheduleBean.getBeanView());
                }
                if (adapter.selectCells.contains(helper.getLayoutPosition())) {
                    helper.setTextColor(R.id.tv_info, textSelectColor);
                } else {
                    helper.setTextColor(R.id.tv_info, textUnSelectColor);
                }

            }
        });
        //点击事件  自定义
        adapter.setItemViewClickListener(new ScheduleAdapter.OnItemViewClickListener() {
            @Override
            public void onItemClick(BaseViewHolder helper, ScheduleInfo item) {
                Toast.makeText(MainActivity.this, String.valueOf(item), Toast.LENGTH_SHORT).show();
            }
        });
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(ScheduleAdapter.spanCount, StaggeredGridLayoutManager.HORIZONTAL);
        rv_schedule.setLayoutManager(layoutManager);
        rv_schedule.setAdapter(adapter);
    }
}
