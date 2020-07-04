import entity.FileEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import util.FileUtil;

import java.io.File;

/**
 * @author yinchao
 * @date 2020/7/4 12:22
 */
@Slf4j
public class FileTest {
    @Test
    public void judeFileType(){
        try {
            System.out.println(FileUtil.judgeFileType("/home/yysir/.zshrc"));
            System.out.println(FileUtil.judgeFileType("/home/yysir/simplescreenrecorder-2020-03-24_20.05.44.mp4"));
            System.out.println(FileUtil.judgeFileType("/tmp/1.jpg"));
            System.out.println(FileUtil.judgeFileType("/home/yysir/program/Java/FTP/pom.xml"));
            System.out.println(FileUtil.judgeFileType("/home/yysir/program/Java/FTP/FTP.iml"));
            System.out.println(FileUtil.judgeFileType("/home/yysir/program/Java/FTP/README.md"));
            System.out.println(FileUtil.judgeFileType("/home/yysir/program/Java/FTP/.gitignore"));
            System.out.println(FileUtil.judgeFileType("/home/yysir/program/Java/FTP/.git"));
//            System.out.println(FileUtil.judgeFileType("/tmp/2020-04-23 22-08-45 的屏幕截图.png".replaceAll(" ","\\\\ ")));
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
