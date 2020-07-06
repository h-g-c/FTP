package client.gui.panel;

import client.gui.table.LocalFileTable;
import client.gui.table.ProgressCellRender;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * @author LvHao
 * @Description : 上传下载的任务面板
 * @date 2020-07-03 2:11
 */
public class TaskPanel extends JTabbedPane {
    private String[]  tableInfo = {"文件名","文件大小","传输状态"};
    private String[][] data = null;
    public TaskPanel(){
        init();
    }

    private void init(){
        DefaultTableModel model1=new DefaultTableModel(data, tableInfo);
        JTable jTable1 = new LocalFileTable(model1);
        jTable1.setEnabled(false);

        DefaultTableModel model2=new DefaultTableModel(data, tableInfo);
        JTable jTable2 = new LocalFileTable(model2);
        add("上传队列",new JScrollPane(jTable1));
        add("下载队列",new JScrollPane(jTable2));
    }
}

