package client.action;

import client.util.GetFiles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author LvHao
 * @Description : 当前目录里的文件和目录展示
 * @date 2020-07-03 2:06
 */
public class LocalFileChange implements ItemListener {

    private JComboBox jComboBox;
    private DefaultTableModel model;
    private JTable jTable;
    private JTextField jTextField;

    public LocalFileChange(JComboBox jComboBox,DefaultTableModel model,JTable jTable,JTextField jTextField){
        this.jComboBox = jComboBox;
        this.model = model;
        this.jTable = jTable;
        this.jTextField = jTextField;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED){
            model.setRowCount(0);
            String[][] datas = GetFiles.getFiles(jComboBox);
            String[] tableInfo = {"文件名","大小","日期"};
            model = new DefaultTableModel(datas,tableInfo);
            jTable.setModel(model);
            jTextField.setText(String.valueOf(jComboBox.getSelectedItem()));
        }
    }
}

