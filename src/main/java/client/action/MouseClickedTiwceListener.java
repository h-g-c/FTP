package client.action;

import client.util.GetFiles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author LvHao
 * @Description : 表格鼠标点击两次事件
 * @date 2020-07-03 2:05
 */
public class MouseClickedTiwceListener extends MouseAdapter {
    private static  boolean flag = false;
    private static int clickNum = 1;
    private JTable jTable;
    private JTextField jTextField;
    private DefaultTableModel model;
    public MouseClickedTiwceListener(JTable jTable,JTextField jTextField,DefaultTableModel model){
        this.jTable = jTable;
        this.jTextField = jTextField;
        this.model = model;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        final MouseEvent me = e;
        MouseClickedTiwceListener.flag= false;
        if (MouseClickedTiwceListener.clickNum==2) {
            //鼠标点击次数为2调用双击事件
            this.mouseClickedTwice(me);
            //调用完毕clickNum置为1
            MouseClickedTiwceListener.clickNum=1;
            MouseClickedTiwceListener.flag=true;
            return;
        }
        //新建定时器，双击检测间隔为500ms
        java.util.Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            //指示定时器执行次数
            int num = 0;
            @Override
            public void run() {
                // 双击事件已经执行，取消定时器任务
                if(MouseClickedTiwceListener.flag) {
                    num=0;
                    MouseClickedTiwceListener.clickNum=1;
                    this.cancel();
                    return;
                }
                //定时器再次执行，调用单击事件，然后取消定时器任务
                if (num==1) {
                    mouseClickedOnce(me);
                    MouseClickedTiwceListener.flag=true;
                    MouseClickedTiwceListener.clickNum=1;
                    num=0;
                    this.cancel();
                    return;
                }
                clickNum++;
                num++;
            }
        },new Date(), 500);
    }
    protected void mouseClickedOnce(MouseEvent me) {
        // 单击事件
    }
    private void mouseClickedTwice(MouseEvent me) {
        // 双击事件
        int row = jTable.rowAtPoint(me.getPoint());
        String fileName = jTextField.getText()+jTable.getValueAt(row,0).toString();
        if(!new File(fileName).isFile()){
            model.setRowCount(0);
            String[][] datas = GetFiles.getFiles(fileName);
            String[] tableInfo = {"文件名","大小","日期"};
            model = new DefaultTableModel(datas,tableInfo);
            jTable.setModel(model);
            jTextField.setText(fileName+"\\");
        }
    }

}