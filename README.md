# mybatis-lab

《JAVA框架技术（一）》实验一任务一至任务五代码与记录。项目包含 MyBatis 基础 CRUD、
动态 SQL、MyBatis-Plus 和 Apifox 接口调试。

## 已准备内容

- Maven 项目及 Java 8 编译目标
- MyBatis-Plus 3.5.3.1、Spring Core 5.3.30、MySQL Connector/J 8.0.33
- JUnit 4.13.2、Log4j 1.2.17
- 阿里云 Maven 镜像与工作区本地仓库配置
- `ssm_emp` 数据库、自定义 `emp` 表及五条初始数据脚本
- 自定义字段 `email`、`phone`、`created_at`，以及唯一约束、检查约束和查询索引
- Git 忽略规则
- 依赖环境冒烟测试
- MyBatis 全局配置、`Emp` 实体类、Mapper 接口及 XML 映射
- `MyBatisUtil` 统一创建 `SqlSessionFactory` 和 `SqlSession`
- 查询、新增、修改、删除及自增主键回填
- `if`、`where`、`set`、`foreach`、`choose` 动态 SQL
- 批量新增、批量删除和按优先级查询
- MyBatis-Plus BaseMapper、QueryWrapper、LambdaQueryWrapper 和分页
- 可直接运行的 `MyBatisDemo` 和真实数据库 JUnit 测试
- CRUD 测试执行后回滚，不改变五条初始员工数据
- 两个 MyBatis 故障的真实复现、修复与回归测试记录
- Apifox `mybatis-lab` 项目及公开 GET 请求调试记录

## 构建命令

```powershell
& 'C:\Users\Lenovo\.m2\wrapper\dists\apache-maven-3.9.6-bin\439sdfsg2nbdob9ciift5h5nse\apache-maven-3.9.6\bin\mvn.cmd' `
  -s .\maven-settings.xml clean test
```

未设置数据库密码时，Maven 会跳过数据库集成测试，但仍会执行依赖环境测试。传入密码时共
执行 9 个测试。

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

## 运行全部测试

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

测试覆盖基础 CRUD、动态 SQL、批处理、MyBatis-Plus 条件构造器和分页。数据库测试使用事务
回滚，数据库仍保留五条初始数据。

## 任务五记录

- [故障复现与排查](docs/task5-troubleshooting.md)
- [AI 应用记录](docs/AI应用记录.md)
- [Apifox 接口调试](docs/task5-apifox.md)

## 运行演示程序

在 IDEA 中打开 `src/main/java/com/lab/demo/MyBatisDemo.java`，设置
`MYBATIS_DB_PASSWORD` 环境变量后运行 `main` 方法。控制台会输出员工总数、员工对象及
MyBatis SQL 日志。

## IDEA 中需要手工完成

1. 打开本目录，选择 `pom.xml` 作为 Maven 项目导入。
2. 在 Maven 设置中选用 `maven-settings.xml`，或把其中配置合并到个人 `settings.xml`。
3. 当前电脑使用 JDK 21，并通过 Maven `release=8` 编译；如需严格匹配指导书，可在 IDEA
   中另外配置 JDK 8 或 JDK 11。
4. AI 插件步骤按用户要求跳过。

## Git 仓库

```powershell
git status
git log --oneline
git remote -v
```

项目已经初始化 Git，并推送到 `https://github.com/xwxdh123-prog/mybatis-lab`。
