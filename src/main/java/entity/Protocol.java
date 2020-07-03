package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 客户端和服务端通信协议
 * @author yinchao
 * @date 2020/7/2 10:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Protocol implements Serializable {
    // 目标ip
    String serviceIp;
    // 发送方ip
    String clientIp;
    // 传输类型
    TransmissionType transmissionType;
    // 传输信息
    String message;
    // 命令端口号
    Integer commandPort;
    // 数据传输端口号
    Integer dataPort;
}
