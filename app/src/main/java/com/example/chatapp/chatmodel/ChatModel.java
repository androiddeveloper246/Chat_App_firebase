package com.example.chatapp.chatmodel;

public class ChatModel {


    private String sender;
    private String chat;
    private String timeAndDate;
    private int readReceiptFlag;
    private String imagePath;
    private int isImageCategory;

    public int getIsImageCategory() {
        return isImageCategory;
    }

    public void setIsImageCategory(int isImageCategory) {
        this.isImageCategory = isImageCategory;
    }

    public ChatModel() { }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setTimeAndDate(String timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public void setReadReceiptFlag(int readReceiptFlag) {
        this.readReceiptFlag = readReceiptFlag;
    }

    public ChatModel(String chat, String currentTime, int readReceiptFlag, String sender,String imagePath,int category) {
        this.chat = chat;
        this.timeAndDate = currentTime;
        this.readReceiptFlag = readReceiptFlag;
        this.sender = sender;
        this.imagePath = imagePath;
        this.isImageCategory = category;
    }

    public String getSender() {
        return sender;
    }
    public String getTimeAndDate() {
        return timeAndDate;
    }
    public int getReadReceiptFlag(){return readReceiptFlag;}
    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }
}
