# EcologyInitTemp

# 泛微EC9二次开发示例
EC9技术站: [https://e-cloudstore.com/e9/index0.html](https://e-cloudstore.com/e9/index0.html)  
后端开发环境搭建:  [https://e-cloudstore.com/doc.html?appId=c6a9ae6e47b74d4da04c935ed51d177a](https://e-cloudstore.com/doc.html?appId=c6a9ae6e47b74d4da04c935ed51d177a)  
前端开发(_ecode代码编辑器_): [https://e-cloudstore.com/doc.html](https://e-cloudstore.com/doc.html)

_**两大需求场景:**_  
**流程表单前端控制**    直达[https://e-cloudstore.com/doc.html?appId=98cb7a20fae34aa3a7e3a3381dd8764e](https://e-cloudstore.com/doc.html?appId=98cb7a20fae34aa3a7e3a3381dd8764e)   
**异构系统调用EC9接口** 直达[https://e-cloudstore.com/ec/api/applist/index.html](https://e-cloudstore.com/ec/api/applist/index.html)

_**其他参考资料**_
EC9.0支持的可配置化的短信接口[查看](https://l1utaihong.gitee.io/solelyrSecond/doc/SMS_INTERFACE.html)   
泛微OA产品部署HTTPS[查看](http://note.youdao.com/s/L8Qg8BFk)  
OA服务器运维脚本[查看](http://note.youdao.com/s/JhfblPlf)  
页面跳转支持传递登录人相关信息[查看](http://note.youdao.com/s/QYzRL8aK)

>#### 三、开发环境搭建

## 开发环境搭建

1. 通过 Git 导入到本地开发工具中
  - 项目地址：https://github.com/solelyr/ecology9-second-demo.git
2. 设置项目目录
  - 建议在 `com.engine` 路径下再设置自定义的项目目录
3. 使用 Gradle 导入项目
  - IDEA 中通过根目录的 `build.gradle` 关联 Gradle 项目，不要在 `Modules → Dependencies` 中手工添加本地 Library
  - `ecology/WEB-INF/lib` 下的 JAR 会由 Gradle 自动加入项目 classpath
  - 迁移验证期间保留 `pom.xml` 作为回退，但 IDEA 中不要同时关联 Maven 和 Gradle 两套构建模型
  - 项目使用 Gradle Wrapper 8.14.4，运行 `gradlew.bat tasks` 可验证环境
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
  - 修改配置文件路径：`/weaver/ecology/WEB-INF/log4jinit.properties`，在最后面增加以下内容，请自行修改solelyrSecond为自定义的日志名称，建议使用ctrl+f替换操作
    ```
    #自定义开发日志文件
    log4j.logger.solelyrSecond=INFO,ERROR,solelyrSecond
    log4j.appender.solelyrSecond=org.apache.log4j.DailyRollingFileAppender
    log4j.appender.solelyrSecond.DatePattern='_'yyyyMMdd'.log'
    #@solelyrSecond为日志目录名称，solelyrSecond.log为日志名称
    log4j.appender.solelyrSecond.File=@solelyrSecond/solelyrSecond.log
    log4j.appender.solelyrSecond.layout=org.apache.log4j.PatternLayout
    log4j.appender.solelyrSecond.layout.ConversionPattern=%d{yyyy-MM-dd HH\:mm\:ss,SSS} %-5p [Thread\:%t] %m%n
    log4j.additivity.solelyrSecond=false
    ```
7. 部署至客户环境
  - 推荐运行 `gradlew clean build -x test`，生成的 JAR 位于 `build/libs`，将其放入客户环境 `WEB-INF/lib` 目录下
  - 需要执行本地集成测试时运行 `gradlew test`；这些测试可能访问数据库和外部接口
  - Gradle 迁移验证期间暂时保留 `pom.xml` 作为 Maven 回退方案，IDEA 中只关联一种构建模型
