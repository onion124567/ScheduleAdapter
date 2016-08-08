package com.onion.library;


/**
 * Created by onion on 2016/8/4.
 */
public class ScheduleBean {
    //所标记的 tagbean  有需要拓展
    String beanTag="3";

    //所显示的 viewbean 可拓展
    String beanView="";

    public String getBeanTag() {
        return beanTag;
    }

    public void setBeanTag(String beanTag) {
        this.beanTag = beanTag;
    }

    public String getBeanView() {
        return beanView;
    }

    public void setBeanView(String beanView) {
        this.beanView = beanView;
    }

    @Override
    public String toString() {
        return "ScheduleBean{" +
                "beanTag='" + beanTag + '\'' +
                ", beanView='" + beanView + '\'' +
                '}';
    }
}
