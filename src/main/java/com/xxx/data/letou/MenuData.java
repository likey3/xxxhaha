package com.xxx.data.letou;

public class MenuData {
    public String menuCode;
    public String itemCode;

    public MenuData(String menuCode) {
        this.menuCode = menuCode;
        this.itemCode = "01";
    }

    @Override
    public String toString() {
        return "MenuData{" +
                "menuCode='" + menuCode + '\'' +
                ", itemCode='" + itemCode + '\'' +
                '}';
    }
}
