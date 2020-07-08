package client.mode;

import client.gui.ClientFrame;
import client.gui.panel.ServerFilePanel;
import client.util.DefaultMsg;
import entity.FileModel;
import entity.Protocol;
import lombok.extern.slf4j.Slf4j;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description : 被动模式下对服务端命令的处理
 * @date 2020-07-05 22:17
 */
@Slf4j(topic = "PsaaiveModeCli")

public class PassiveMode extends Mode {
    private final  String[] tableInfo = {"文件名", "大小", "日期","文件类型"};

    public void showServerDir(Protocol protocolFromSocket, ServerFilePanel serverFilePanel, DefaultTableModel model, ClientFrame clientFrame) {
        log.info(clientFrame.getPsvdataSocket().toString());
        if(clientFrame.getPsvdataSocket().isConnected()){
            log.info("被动数据端口已连接");
        }
    }

}
