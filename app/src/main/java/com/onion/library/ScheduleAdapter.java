package com.onion.library;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.onion.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by onion on 2016/7/29.
 */
public class ScheduleAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
//    public ScheduleAdapter(List<ScheduleBean> data) {
//        super(data);
//        addItemType(SCHEDULONE, R.layout.item_view_text);
//        addItemType(LESSONINDEX, R.layout.item_view_text);
//        addItemType(WEEKINDEX, R.layout.item_view_text);
//        addItemType(LESSONTODAY, R.layout.item_view_text);
//    }
    //表头自己定义
    public String tableHead = "八月";

    public ArrayList<Integer> selectCells = new ArrayList<>();
    public int selectColum = 3;
    //选中单元格颜色
    public static final int COLOR_SELECTCELL = 0xff48c55c;
    //选中列的颜色
    public static final int COLOR_SELECTCOLUM = 0xfffcf8f5;
    //未选中的颜色
    public static final int COLOR_UNSELETCELL = 0xffffffff;
    //左上第一个table
    public final static int SCHEDULONE = -1;
    //左边第一列节数索引
    public final static int LESSONINDEX = -2;
    //顶部第一行周索引
    public final static int WEEKINDEX = -3;
    //今天的课
    public final static int LESSONTODAY = -4;
    //其他天的课
//    public final static int LESSONOTHER = -5;
    //列数
    public final static int spanCount = 11;

    private float cellWidth = 0;
    private final static int cellHeight = 50;
    private ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("MM-dd");
    private List<Date> dates = new ArrayList<Date>();
    private int mLayoutResId;
    private Context mContext;
    private List<ScheduleInfo> mData = new ArrayList<>();

    public ScheduleAdapter(int layoutResId, @Nullable Date firstDate) {
        this.mLayoutResId = layoutResId;
        initBlank(firstDate);
    }


    public void setFullScreen(Context mContext) {
        cellWidth = ScreenUtils.getScreenWidth(mContext) - ScreenUtils.dip2px(mContext, 25);
        cellWidth = cellWidth / 7;
        params = new ViewGroup.LayoutParams((int) cellWidth, ScreenUtils.dip2px(mContext, cellHeight));
    }

    @Override
    public int getItemViewType(int position) {
        //根据position区分显示内容
        if (position == 0) {
            return SCHEDULONE;
        } else if (position < spanCount) {//第一列
            return LESSONINDEX;
        } else if (position % spanCount == 0) {//第一行
            return WEEKINDEX;
        }
//        return getDefItemViewType(position - spanCount - position / spanCount);
        return LESSONTODAY;
    }

    @Override
    public int getItemCount() {
//        int count = mData.size() + spanCount + mData.size() / spanCount + 1 + (spanCount - 7);
        if (mData.isEmpty()) return 0;
        return 8 * spanCount;
    }

    //创建空课表
    //传入当前时间
    public void initBlank(Date date) {
        if (date == null) {
            calendar.setTime(new Date(System.currentTimeMillis()));
        } else
            this.calendar.setTime(date);
        //创建今天时间
        Calendar todayC = Calendar.getInstance();
        todayC.setTime(new Date(System.currentTimeMillis()));
        final int iMonth = todayC.get(Calendar.MONTH);
        final int iDay = todayC.get(Calendar.DAY_OF_MONTH);
        // 设置成当周第一天，每周第一天为周一
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_MONTH, 2 - day);
        for (int i = 0; i < 7; i++) {
            dates.add(calendar.getTime());
            //判断是不是今天
            if (calendar.get(Calendar.MONTH) == iMonth) {
                if (calendar.get(Calendar.DAY_OF_MONTH) == iDay) {
                    //除去第一列课程索引
                    selectColum = i + 1;
                }
            }
            for (int j = 0; j < spanCount - 1; j++) {
                ScheduleInfo bean = new ScheduleImpl(calendar.getTime(), j);
                mData.add(bean);
            }

            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        dates.add(date);
        //setOnRecyclerViewItemClickListener(listener);
    }
    //处理点击事件
//    OnRecyclerViewItemClickListener listener = new OnRecyclerViewItemClickListener() {
//        @Override
//        public void onItemClick(View view, int position) {
//            if (position == 0 && headaction != null) {//表头的点击效果
//                headaction.call(tableHead);
//            } else if (position < spanCount && lessonIndexAction != null) {//第一列
//                lessonIndexAction.call(view, null, position);
//            } else if (position % spanCount == 0 && weekIndexAction != null) {//第一行
//                weekIndexAction.call(view, null, position % spanCount);
//            } else if (lessonAction != null) {
//                position = getDataPosition(position);
//                lessonAction.call(view, mData.get(position), position);
//            }
//        }
//    };
//    public Action1<String> headaction;
//    public Action3<View, ScheduleInfo, Integer> weekIndexAction;
//    public Action3<View, ScheduleInfo, Integer> lessonIndexAction;
//    public Action3<View, ScheduleInfo, Integer> lessonAction;

    //插入单元格标记
    public void insertSelectCell(int lessonIndex, int dayofweek) {
        selectCells.add(lessonIndex + dayofweek * spanCount);
    }


    public int getDataPosition(int layoutIndex) {
        int coulmIndex = layoutIndex / spanCount;
        return layoutIndex - spanCount - coulmIndex;
    }


    public void insertLessonCell(int lessonIndex, int dayofweek, T info) {
        dayofweek -= 1;
        lessonIndex -= 1;
        int spanIndex = spanCount - 1;
        if (dayofweek * (spanIndex) + lessonIndex > mData.size() || lessonIndex > spanIndex) {
            throw new RuntimeException("lessonIndex is bigger than list size");
        }
        mData.get(dayofweek * spanIndex + lessonIndex).setTagBean(info);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int positions) {
        int viewType = holder.getItemViewType();
        int coulmIndex = holder.getLayoutPosition() / spanCount;
        int layoutIndex = holder.getLayoutPosition();
        BaseViewHolder baseViewHolder = (BaseViewHolder) holder;

        if (selectCells.contains(layoutIndex)) {
            baseViewHolder.convertView.setBackgroundColor(COLOR_SELECTCELL);
        } else if (layoutIndex / spanCount == selectColum)
            baseViewHolder.convertView.setBackgroundColor(COLOR_SELECTCOLUM);
        else {
            baseViewHolder.convertView.setBackgroundColor(COLOR_UNSELETCELL);
        }
        switch (viewType) {
            case SCHEDULONE:
                baseViewHolder.setText(R.id.tv_name, tableHead);
                break;
            case LESSONINDEX:
                baseViewHolder.setText(R.id.tv_name, layoutIndex + "");
                break;
            case WEEKINDEX:
                baseViewHolder.setText(R.id.tv_name, getWeekInfo(coulmIndex - 1));
                break;
            //先不考虑
          /*  case LOADING_VIEW:
                break;
            case HEADER_VIEW:
                break;
            case EMPTY_VIEW:
                break;
            case FOOTER_VIEW:
                break;*/
            case LESSONTODAY:
            default:
//                position - spanCount - position / spanCount
             final   int position = layoutIndex - spanCount - coulmIndex;
                convert((BaseViewHolder) holder, mData.get(position));
//                onBindDefViewHolder((BaseViewHolder) holder, mData.get(position));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemViewClickListener.onItemClick((BaseViewHolder) holder, mData.get(position));
                    }
                });

                break;
        }
    }

    /**
     * 通过是本周的第几天，返回日期信息
     *
     * @param dayofweek
     * @return
     */
    private String getWeekInfo(int dayofweek) {
        StringBuilder weekInfo = new StringBuilder("\n");
        switch (dayofweek) {
            case 0:
                weekInfo.append("周一");
//                   calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
                break;
            case 1:
                weekInfo.append("周二");
//                   calendar.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
                break;
            case 2:
                weekInfo.append("周三");
//                   calendar.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
                break;
            case 3:
                weekInfo.append("周四");
//                   calendar.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
                break;
            case 4:
                weekInfo.append("周五");
//                   calendar.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
                break;
            case 5:
                weekInfo.append("周六");
//                   calendar.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
                break;
            case 6:
                weekInfo.append("周日");
//                   calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
                break;
        }
        return format.format(dates.get(dayofweek)) + weekInfo.toString();
    }

    //普通adatper实现这个方法
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        return onCreateDefViewHolder(parent, viewType);
    }

    //Base框架实现这个方法
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = null;
        switch (viewType) {
            case SCHEDULONE:
                holder = new BaseViewHolder(View.inflate(mContext, R.layout.item_txt_num, null));
                break;
            case WEEKINDEX:
                holder = new BaseViewHolder(View.inflate(mContext, R.layout.item_txt_weekindex, null));
                holder.convertView.setLayoutParams(params);
                break;
            case LESSONINDEX:
                holder = new BaseViewHolder(View.inflate(mContext, R.layout.item_txt_num, null));
                break;
            default:
            case LESSONTODAY:
                holder = new BaseViewHolder(View.inflate(mContext, mLayoutResId, null));
                holder.convertView.setLayoutParams(params);
        }
        return holder;
    }

    //可继承  可通过listener监听
    protected void convert(BaseViewHolder helper, ScheduleInfo<T> item) {
        if (listener != null) {
            listener.onConvert(helper,item);
        }
    }

    OnConverListener listener;

    public static interface OnConverListener<T> {
        public void onConvert(BaseViewHolder helper, ScheduleInfo<T> item);

    }

    public void setListener(OnConverListener listener) {
        this.listener = listener;
    }

    //点击事件
    public static interface  OnItemViewClickListener<T>{
        public void onItemClick(BaseViewHolder helper, ScheduleInfo<T> item);
    }
    OnItemViewClickListener itemViewClickListener=new OnItemViewClickListener() {
        @Override
        public void onItemClick(BaseViewHolder helper, ScheduleInfo item) {

        }
    };

    public void setItemViewClickListener(OnItemViewClickListener itemViewClickListener) {
        this.itemViewClickListener = itemViewClickListener;
    }
}
