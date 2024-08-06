package com.example.qrgenerator;

public class QRCodeType {
    private final String name;
    private final int iconResId;

    public QRCodeType(String name, int iconResId) {
        this.name = name;
        this.iconResId = iconResId;
    }

    public String getName() {
        return name;
    }

    public int getIconResId() {
        return iconResId;
    }
}
