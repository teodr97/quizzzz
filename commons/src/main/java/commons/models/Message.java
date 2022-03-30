package commons.models;

public class Message {

    private MessageType msgType;
    private String content;
    private String username;


    public Message(){

    }

    /** Constructtor of the message object
     * @param type message type as string
     * @param username username of the person sending message
     * @param content content of the message object
     */
    public Message(String type, String username, String content){
        this.msgType = MessageType.valueOf(type);
        this.username = username;
        this.content = content;
    }

    /** Constructtor of the message object
     * @param type message type as type type
     * @param username username of the person sending message
     * @param content content of the message object
     */
    public Message(MessageType type, String username, String content)
    {
        this.msgType = type;

        this.username = username;
        this.content = content;
    }


    /**
     * @return Getter for the message type returns it as a Messagetype object
     */
    public MessageType getMsgType() {return msgType;}


    /**
     * @param msgType Setter for the msg type
     */
    public void setMsgType(MessageType msgType) {this.msgType = msgType;}


    /**
     * @return returns the username of the person that sends the message as a string
     */
    public String getUsername() {return username;}


    /**
     * @param nickname sets the username of the message
     */
    public void setNickname(String nickname) {this.username = nickname;}


    /**
     * @return returns the content of the messge as a string
     */
    public String getContent() {return content;}


    /**
     * @param content Sets the content of the messgage
     */
    public void setContent(String content) {this.content = content;}


    /**
     * @return returns the message object as a readable string
     */
    public String toString()
    {
        return this.username + "(" + this.msgType.toString() + "): " + this.content;
    }



}
