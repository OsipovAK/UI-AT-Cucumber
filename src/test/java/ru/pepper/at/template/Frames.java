package ru.pepper.at.template;

public enum Frames {
    TOP_WINDOW(null),
    NEW_FRAME("//iframe[1]");

    final String xpath;
    Frames(String xpath){
        this.xpath = xpath;
    }

    public String getXpath(){
        return xpath;
    }
}
