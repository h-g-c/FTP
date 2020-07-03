package configuration_and_constant;

/**
 * @author yinchao
 * @date 2020/7/2 17:27
 */
public class Constant {
    /**
     * 首部字段表示即将传输命令/数据的大小
     */
    public static final Integer HEAD_BYTE_SIZE = 4;

    /**
     * 命令端口号
     */
    public static final Integer COMMANDPORT = 21;

    /**
     * 命令/数据最大大小: 4G 左右
     */
    public static final Integer MAXIMUM_COMMAND_BUFFER_SIZE = (int)Math.pow(2,32);

}
