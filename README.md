## 项目说明

- 项目用到的技术栈：

    - Java 相关： 线程池，日志 Slf4J 规范 log4J 日志框架，GUI Swing，Socket 网络通信等
    
    - 面向对象的设计思想
    
    - 设计模式：单例模式，策略模式等
    
    - 自定义协议完成数据的读写通信

    - 项目使用 Lombok 插件简化开发，git 进行版本管理，完成代码合并
    
    - 根据文件头判断文件真实类型，调用shell命令,也有采用判断 0 的办法区分，但是需要读取整个文件，速度会很慢

## 遇到的问题：

1. 在Linux 下，FTP 被动模式的 21 端口连接报错：Bind Error，需要 root 权限才能正常连接

## 需要完成的

- [x] 建立连接

- [ ] 被动模式主要逻辑

- [ ] windows下的cmd交互
