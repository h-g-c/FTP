import entity.FileEnum;
import entity.PassiveMode;
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
    public void judgeFileType() {
        final FileUtil fileUtil = new FileUtil();
        assert (FileEnum.BINARY == fileUtil.judgeFileType("/tmp/Big_Blue.jpg"));
        assert (FileEnum.BINARY == fileUtil.judgeFileType("/home/yysir/simplescreenrecorder-2020-03-24_20.05.44.mp4"));
        assert (FileEnum.BINARY == fileUtil.judgeFileType("/tmp/1.jpg"));
        assert (FileEnum.TEXT == fileUtil.judgeFileType("/home/yysir/program/Java/FTP/pom.xml"));
        assert (FileEnum.TEXT == fileUtil.judgeFileType("/home/yysir/program/Java/FTP/FTP.iml"));
        assert (FileEnum.TEXT == fileUtil.judgeFileType("/home/yysir/program/Java/FTP/README.md"));
        assert (FileEnum.BINARY == fileUtil.judgeFileType("/home/yysir/program/Java/FTP/.git"));
//            System.out.println(FileUtil.judgeFileType("/tmp/2020-04-23 22-08-45 的屏幕截图.png".replaceAll(" ","\\\\ ")));
    }

    @Test
    public void judeFileTypeGenerally() {
        try {
            final FileUtil fileUtil = new FileUtil();
            assert (FileEnum.BINARY == fileUtil.judgeFileTypeGenerally("/tmp/Big_Blue.jpg"));
            assert (FileEnum.BINARY == fileUtil.judgeFileTypeGenerally("/home/yysir/simplescreenrecorder-2020-03-24_20.05.44.mp4"));
            assert (FileEnum.BINARY == fileUtil.judgeFileTypeGenerally("/tmp/1.jpg"));
            assert (FileEnum.TEXT == fileUtil.judgeFileTypeGenerally("/home/yysir/program/Java/FTP/pom.xml"));
            assert (FileEnum.TEXT == fileUtil.judgeFileTypeGenerally("/home/yysir/program/Java/FTP/FTP.iml"));
            assert (FileEnum.TEXT == fileUtil.judgeFileTypeGenerally("/home/yysir/program/Java/FTP/README.md"));
            assert (FileEnum.BINARY == fileUtil.judgeFileTypeGenerally("/home/yysir/program/Java/FTP/.git"));
//            System.out.println(FileUtil.judgeFileType("/tmp/2020-04-23 22-08-45 的屏幕截图.png".replaceAll(" ","\\\\ ")));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Test
    public void deleteFile(){
        new PassiveMode().delete(new File("/tmp/test1/123.txt"));
    }
}
