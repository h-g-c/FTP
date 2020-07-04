package client.action;

import client.gui.panel.LocalFilePanel;
import client.util.GetFiles;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author LvHao
 * @Description : 表格数据的刷新
 * @date 2020-07-03 2:07
 */
@Data
@RequiredArgsConstructor
public class Flush implements ActionListener {

    private String[][] data = null;
    private final String[] tableInfo = {"文件名","大小","日期"};

    @NonNull
    private LocalFilePanel localFilePanel;
    @NonNull
    private DefaultTableModel model;

    @Override
    public void actionPerformed(ActionEvent e) {
        model.setRowCount(0);
        data = GetFiles.getFiles(localFilePanel.getJComboBox());
        model = new DefaultTableModel(data,tableInfo);
        localFilePanel.getJTable().setModel(model);
        localFilePanel.getJTextField().setText(String.valueOf(localFilePanel.getJComboBox().getSelectedItem()));
    }
}
