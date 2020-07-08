package client.mode;

import client.gui.ClientFrame;
import client.gui.msg.MessageDialog;
import client.gui.panel.ServerFilePanel;
import client.util.DefaultMsg;
import entity.FileModel;
import entity.Protocol;
import lombok.extern.slf4j.Slf4j;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description : 客户端模式和指令处理的抽象类
 * 初步初始化了客户端连接到服务端遍历客户端文件夹的实现
 * 下载 上传 暂停的处理逻辑还未完成
 * 后续的删除考虑是否添加
 * 完成上传下载
 * 完成删除
 * @date 2020-07-05 22:24
 */
@Slf4j(topic = "showServerDir")
public abstract class Mode {

    public final  String[] tableInfo = {"文件名", "大小", "日期","文件类型"};

    public abstract void showServerDir(Protocol protocolFromSocket, ServerFilePanel serverFilePanel, DefaultTableModel model,ClientFrame clientFrame);

    public void upload(Protocol protocolFromSocket, ClientFrame clientFrame, ArrayList<String[]> data) {
        //上传文件处理
    }

    public void download(Protocol protocolFromSocket, ClientFrame clientFrame, ArrayList<String[]> data){
        //下载文件处理
    }

    public void delete(Protocol protocolFromSocket, ClientFrame clientFrame){
        String delMsg = (String) protocolFromSocket.getData();
        new MessageDialog("提示",delMsg,clientFrame).del();
    }

    public void pause(){

    }
    public void error(ClientFrame clientFrame){
        new MessageDialog("连接失败","登陆密码错误",clientFrame).init();
    }
}
