package com.onion.library;


import java.util.Date;

/**
 * Created by onion on 2016/7/29.
 */
public interface ScheduleInfo<T> {
    //课程单元格可绑定的对象
    public T getTagBean();

    public void setTagBean(T bean);

    public Date getTime();

    public void  setTime(Date date);

    public int getLessonIndex();

    public void setLessonIndex(int index);

}
