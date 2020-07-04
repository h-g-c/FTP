package client.gui.action;

import client.gui.panel.LocalFilePanel;
import client.util.GetUploadFiles;
import lombok.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-04 19:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class FileUpload implements ActionListener {

    private JTable jTable;
    private String filePath;

    @NonNull
    private LocalFilePanel localFilePanel;

    @Override
    public void actionPerformed(ActionEvent e) {
        jTable = localFilePanel.getJTable();
        filePath = localFilePanel.getJTextField().getText();
        String[] fileNames = GetUploadFiles.getName(jTable,filePath);
    }
}