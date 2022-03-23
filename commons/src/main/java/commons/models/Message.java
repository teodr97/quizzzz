package commons.models;

public class Message {

    private MessageType msgType;
    private String content;
    private String username;


    public Message(){

    }

    /** Constructtor of the message object
     * @param type message type
     * @param username username of the person sending message
     * @param content content of the message object
     */
    public Message(String type, String username, String content){
        this.msgType = MessageType.valueOf(type);
        this.username = username;
        this.content = content;
    }


    public MessageType getMsgType() {return msgType;}
    public void setMsgType(MessageType msgType) {this.msgType = msgType;}


    public String getUsername() {return username;}
    public void setNickname(String nickname) {this.username = nickname;}
    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}

    public String toString()
    {
        return this.username + "(" + this.msgType.toString() + "): " + this.content;
    }



}
