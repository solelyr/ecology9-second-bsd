# EcologyInitTemp

# 泛微EC9二次开发示例
EC9技术站: [https://e-cloudstore.com/e9/index0.html](https://e-cloudstore.com/e9/index0.html)  
后端开发环境搭建:  [https://e-cloudstore.com/doc.html?appId=c6a9ae6e47b74d4da04c935ed51d177a](https://e-cloudstore.com/doc.html?appId=c6a9ae6e47b74d4da04c935ed51d177a)  
前端开发(_ecode代码编辑器_): [https://e-cloudstore.com/doc.html](https://e-cloudstore.com/doc.html)

_**两大需求场景:**_  
**流程表单前端控制**    直达[https://e-cloudstore.com/doc.html?appId=98cb7a20fae34aa3a7e3a3381dd8764e](https://e-cloudstore.com/doc.html?appId=98cb7a20fae34aa3a7e3a3381dd8764e)   
**异构系统调用EC9接口** 直达[https://e-cloudstore.com/ec/api/applist/index.html](https://e-cloudstore.com/ec/api/applist/index.html)

_**其他参考资料**_
EC9.0支持的可配置化的短信接口[查看](https://l1utaihong.gitee.io/custom/doc/SMS_INTERFACE.html)   
泛微OA产品部署HTTPS[查看](http://note.youdao.com/s/L8Qg8BFk)  
OA服务器运维脚本[查看](http://note.youdao.com/s/JhfblPlf)  
页面跳转支持传递登录人相关信息[查看](http://note.youdao.com/s/QYzRL8aK)

>#### 三、开发环境搭建

## 开发环境搭建

1. 通过 Git 导入到本地开发工具中
  - 项目地址：https://github.com/solelyr/ecology9-second-demo.git
2. 设置项目目录
  - 建议在 `com.engine` 路径下再设置自定义的项目目录
3. 删除非必要的pom.xml，以下部分内容为个人私有使用，可自行删除（19-31行）。
   ```
   <distributionManagement>
   <repository>
   <id>nexus-solelyr-public</id>
   <url>https://nexus.solelyz.cn/repository/maven-public/</url>
   </repository>
   </distributionManagement>

    <dependencies>
        <dependency>
            <groupId>ecology9.second</groupId>
            <artifactId>weaver</artifactId>
            <version>9.00.2601.01</version>
        </dependency>
        <dependency>
            <groupId>ecology9.second</groupId>
            <artifactId>_solelyr</artifactId>
            <version>26.09.0.1</version>
        </dependency>
    </dependencies>
   ```
4. 拷贝必要的 JAR 包依赖
   这个很重要，需要与客户环境的KB补丁包保持一致
  - 进入服务器上的 `/weaver/ecology/classbean` 目录，执行 `jar -cvf local-ecology.jar ./` 生成对应的 JAR 包，将 JAR 包拷贝到本地项目中
  - 拷贝服务器上的 `/weaver/ecology/WEB-INF/lib` 目录到本地项目中或通过软链设置`mklink /d 需要生成的软链目录路径 源文件目录路径`
  - 需要额外引入 JUnit 4 的 JAR 包才能支持单元测试

5. OA 不启动服务测试方案
  - 复制`weaver.properties.temp`并重命名为`weaver.properties`修改为实际的数据库连接地址
  - 执行test目录下RecordSetTest项目测试本地运行是否可以正常
  - 如有问题请检查BaseTest输出的`sysPath`是否正确

6. OA 自定义日志输出路径
  - 修改配置文件路径：`/weaver/ecology/WEB-INF/log4jinit.properties`，在最后面增加以下内容，请自行修改custom为自定义的日志名称，建议使用ctrl+f替换操作
    ```
    #自定义开发日志文件
    log4j.logger.custom=INFO,ERROR,custom
    log4j.appender.custom=org.apache.log4j.DailyRollingFileAppender
    log4j.appender.custom.DatePattern='_'yyyyMMdd'.log'
    #@custom为日志目录名称，custom.log为日志名称
    log4j.appender.custom.File=@custom/custom.log
    log4j.appender.custom.layout=org.apache.log4j.PatternLayout
    log4j.appender.custom.layout.ConversionPattern=%d{yyyy-MM-dd HH\:mm\:ss,SSS} %-5p [Thread\:%t] %m%n
    log4j.additivity.custom=false
    ```
7. 部署至客户环境
  - 运行maven package 命令，生成对应jar包，将jar包放入客户环境WEB-INF/lib目录下
