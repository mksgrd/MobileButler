package com.maksg.mobilebutler;

public class TaskDTO {
    private String title, time, settings;

    public TaskDTO(String title, String time, String settings) {
        this.title = title;
        this.time = time;
        this.settings = settings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }
}
