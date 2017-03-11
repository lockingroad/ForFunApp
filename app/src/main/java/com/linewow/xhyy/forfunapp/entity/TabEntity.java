package com.linewow.xhyy.forfunapp.entity;

import com.linewow.xhyy.forfunapp.view.CustomTabEntity;

/**
 * Created by LXR on 2017/1/16.
 */

public class TabEntity implements CustomTabEntity {
    private int selectedIcon;
    private int unSelectedIcon;
    private String title;
    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }

    public TabEntity(int selectedIcon, int unSelectedIcon, String title) {
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
        this.title = title;
    }
}
