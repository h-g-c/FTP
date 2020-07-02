package client.action;

import client.util.GetFiles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author LvHao
 * @Description : 表格数据的刷新
 * @date 2020-07-03 2:07
 */
public class Flush implements ActionListener {
    private JTable jTable;
    private JTextField jTextField;
    private DefaultTableModel model;
    private JComboBox jComboBox;
    public Flush(JTable jTable,JTextField jTextField,DefaultTableModel model,JComboBox jComboBox){
        this.jTable = jTable;
        this.jTextField = jTextField;
        this.model = model;
        this.jComboBox = jComboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.setRowCount(0);
        String[][] datas = GetFiles.getFiles(jComboBox);
        String[] tableInfo = {"文件名","大小","日期"};
        model = new DefaultTableModel(datas,tableInfo);
        jTable.setModel(model);
        jTextField.setText(String.valueOf(jComboBox.getSelectedItem()));
    }
}
