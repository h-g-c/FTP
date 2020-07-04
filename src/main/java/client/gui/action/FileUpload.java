package client.gui.action;

import client.gui.panel.LocalFilePanel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class FileUpload implements ActionListener {

    private LocalFilePanel localFilePanel;

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
