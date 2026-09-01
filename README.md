# mybatis-lab

《JAVA框架技术（一）》实验一任务一：开发环境搭建与项目初始化。

## 已准备内容

- Maven 项目及 Java 8 编译目标
- MyBatis 3.5.13、MySQL Connector/J 8.0.33、JUnit 4.13.2、Log4j 1.2.17
- 阿里云 Maven 镜像与工作区本地仓库配置
- `ssm_emp` 数据库、自定义 `emp` 表及五条初始数据脚本
- 自定义字段 `email`、`phone`、`created_at`，以及唯一约束、检查约束和查询索引
- Git 忽略规则
- 依赖环境冒烟测试
- MyBatis 全局配置、`Emp` 实体类、Mapper 接口及 XML 映射
- 连接真实 `ssm_emp` 数据库的查询测试

## 构建命令

```powershell
& 'C:\Users\Lenovo\.m2\wrapper\dists\apache-maven-3.9.6-bin\439sdfsg2nbdob9ciift5h5nse\apache-maven-3.9.6\bin\mvn.cmd' `
  -s .\maven-settings.xml clean test
```

未设置数据库密码时，Maven 会跳过数据库集成测试，但仍会执行依赖环境测试。

## 初始化数据库

```powershell
Copy-Item .\sql\init.sql "$env:TEMP\mybatis-lab-init.sql" -Force
& 'C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe' `
  -u root -p --default-character-set=utf8mb4
```

登录 MySQL 后执行：

```sql
source C:/Users/Lenovo/AppData/Local/Temp/mybatis-lab-init.sql;
```

密码只在 MySQL 提示符中输入，不要写入项目文件或提交到 Git。初始化脚本会重建实验用
`emp` 表，请勿在其中存放需要保留的正式数据。

## 运行 MyBatis 查询测试

在 PowerShell 7 中执行以下命令。密码只保存在当前终端进程的内存中，测试结束后立即清除。

```powershell
$env:MYBATIS_DB_PASSWORD = Read-Host -MaskInput 'MySQL password'
try {
    & 'C:\Users\Lenovo\.m2\wrapper\dists\apache-maven-3.9.6-bin\439sdfsg2nbdob9ciift5h5nse\apache-maven-3.9.6\bin\mvn.cmd' `
      -s .\maven-settings.xml test
} finally {
    Remove-Item Env:MYBATIS_DB_PASSWORD -ErrorAction SilentlyContinue
}
```

`MyBatisIntegrationTest` 会执行两条查询：读取全部 5 名员工，以及按编号读取“顾雪”。

## IDEA 中需要手工完成

1. 打开本目录，选择 `pom.xml` 作为 Maven 项目导入。
2. 在 Maven 设置中选用 `maven-settings.xml`，或把其中配置合并到个人 `settings.xml`。
3. 当前电脑使用 JDK 21，并通过 Maven `release=8` 编译；如需严格匹配指导书，可在 IDEA
   中另外配置 JDK 8 或 JDK 11。
4. AI 插件步骤按本次实验安排跳过。

## Git 仓库

```powershell
git status
git log --oneline
git remote -v
```

项目已经初始化 Git，并推送到 `https://github.com/xwxdh123-prog/mybatis-lab`。
