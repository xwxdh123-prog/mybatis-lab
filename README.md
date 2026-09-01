# mybatis-lab

《JAVA框架技术（一）》实验一任务一：开发环境搭建与项目初始化。

## 已准备内容

- Maven 项目及 Java 8 编译目标
- MyBatis 3.5.13、MySQL Connector/J 8.0.33、JUnit 4.13.2、Log4j 1.2.17
- 阿里云 Maven 镜像与工作区本地仓库配置
- `ssm_emp` 数据库、`emp` 表及四条初始数据脚本
- Git 忽略规则
- 依赖环境冒烟测试

## 构建命令

```powershell
& 'C:\Users\Lenovo\.m2\wrapper\dists\apache-maven-3.9.6-bin\439sdfsg2nbdob9ciift5h5nse\apache-maven-3.9.6\bin\mvn.cmd' `
  -s .\maven-settings.xml clean test
```

## 初始化数据库

```powershell
& 'C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe' `
  -u root -p --default-character-set=utf8mb4 `
  --execute="source E:/实验报告/mybatis-lab/sql/init.sql"
```

密码只在 MySQL 提示符中输入，不要写入项目文件或提交到 Git。

## IDEA 中需要手工完成

1. 打开本目录，选择 `pom.xml` 作为 Maven 项目导入。
2. 在 Maven 设置中选用 `maven-settings.xml`，或把其中配置合并到个人 `settings.xml`。
3. 安装并登录通义灵码、GitHub Copilot 或 CodeGeeX 中至少一个插件。
4. 开启代码补全与行内提示，禁止自动应用未经审查的生成代码。

## 首次 Git 提交

首次提交前请使用自己的真实信息配置身份：

```powershell
git config --global user.name "你的姓名拼音"
git config --global user.email "你的学号邮箱@school.edu.cn"
git add .
git commit -m "init: mybatis 环境搭建"
```
