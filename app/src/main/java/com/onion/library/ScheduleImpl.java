package com.onion.library;

import java.util.Date;

/**
 * Created by onion on 2016/8/1.
 */
public class ScheduleImpl implements ScheduleInfo<ScheduleBean> {
    ScheduleBean openScheduleBean;
    Date date;
    int lessonindex;

    public ScheduleImpl() {
    }

    public ScheduleImpl(Date date, int lessonindex) {
        this.date = date;
        this.lessonindex = lessonindex;
    }

    @Override
    public ScheduleBean getTagBean() {
        return openScheduleBean;
    }

    @Override
    public void setTagBean(ScheduleBean bean) {
        this.openScheduleBean = bean;
    }

    @Override
    public Date getTime() {
        return date;
    }

    @Override
    public void setTime(Date date) {
        this.date = date;
    }

    @Override
    public int getLessonIndex() {
        return lessonindex;
    }

    @Override
    public void setLessonIndex(int index) {
        this.lessonindex = index;
    }

    @Override
    public String toString() {
        return "ScheduleImpl{" +
                "openScheduleBean=" + openScheduleBean +
                ", date=" + date +
                ", lessonindex=" + lessonindex +
                '}';
    }
}
