package client.gui.panel;

import client.action.Flush;
import client.action.LocalFileChange;
import client.action.MouseClickedTwiceListener;
import client.gui.MyGridBagConstraints;
import client.gui.table.LocalFileTable;
import client.util.GetFiles;
import lombok.Data;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

/**
 * @author LvHao
 * @Description : 本地文件遍历的实现
 * @date 2020-07-03 2:01
 */
@Data
public class LocalFilePanel extends JPanel {

    private JLabel jLabel = new JLabel("本地文件",JLabel.CENTER);
    private JButton jButton1 = new JButton(" 上传 ");
    private JButton jButton2 = new JButton("   刷新  ");
    private JTextField jTextField = new JTextField(" ",10);
    private JComboBox jComboBox;
    private JTable jTable;
    private final String[] tableInfo = {"文件名","大小","日期"};
    private String[][] data = null;

    public LocalFilePanel(){
        init();
    }

    private void init(){
        setLayout(new GridBagLayout());

        jTextField.setEditable(false);
        jComboBox = new JComboBox();
        File[] roots = File.listRoots();
        for(int i = 0;i < roots.length;i++){
            jComboBox.addItem(roots[i]);
        }
        jTextField.setText(String.valueOf(jComboBox.getSelectedItem()));
        data = GetFiles.getFiles(jComboBox);
        DefaultTableModel model=new DefaultTableModel(data, tableInfo);
        jTable = new LocalFileTable(model);
        jTable.addMouseListener(new MouseClickedTwiceListener(this,model));
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jComboBox.addItemListener(new LocalFileChange(this,model));
        jButton2.addActionListener(new Flush(this,model));

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new GridBagLayout());

        jPanel1.add(jButton1,new MyGridBagConstraints(0,0,1,1).init2());
        jPanel1.add(jButton2,new MyGridBagConstraints(1,0,1,1).init2());
        jPanel1.add(jComboBox,new MyGridBagConstraints(2,0,1,1).init2());

        add(jLabel,new MyGridBagConstraints(0,0,1,1).init1());

        add(jPanel1,new MyGridBagConstraints(0,1,1,1).init1());

        add(jScrollPane,new MyGridBagConstraints(0,2,10,10).init2());

        add(jTextField,new MyGridBagConstraints(0,3,1,1).init1());
    }
}

