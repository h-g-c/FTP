package server.test;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 16:42
 */
public class MessageHelper {
    public static final String WHITESPACE=" ";
    public static class Message {
        private final String from;
        private final String to;
        private final String body;

        public Message(String from, String to, String body) {
            this.from = from;
            this.to = to;
            this.body = body;
        }

        public String getFrom() {
            return this.from;
        }

        public String getTo() {
            return this.to;
        }

        public String getBody() {
            return this.body;
        }
    }

    // Raw message format: From user1 to user2: message body.
    public static Message parseRawMessage(String raw) {
        raw = raw.trim();
        while(raw.indexOf(WHITESPACE + WHITESPACE)>0) {
            raw = raw.replaceAll(WHITESPACE+WHITESPACE, WHITESPACE);
        }
        String[] arr = raw.split(":");
        String header = arr[0];
        String body = arr[1].trim();
        arr = header.split(WHITESPACE);
        String from = arr[1];
        String to = arr[3];
        return new Message(from, to, body);
    }

    public static String produceRawMessage(Message message) {
        StringBuffer sb = new StringBuffer();
        sb.append("From").append(WHITESPACE).append(message.from).append(WHITESPACE)
                .append("To").append(WHITESPACE).append(message.to).append(":").append(message.body);
        return sb.toString();
    }
}