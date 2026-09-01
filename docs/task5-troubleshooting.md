# 任务五：故障复现与排查记录

## 问题一：Mapper 参数名与实体属性不一致

### 复现方法

把 `EmpMapper.xml` 中 `insertCustom` 的员工姓名参数从 `#{empName}` 临时改成
`#{empname}`，然后运行 `MyBatisIntegrationTest`。

### 实际现象

MyBatis 在设置 SQL 参数时中止，并报告：

```text
ReflectionException: There is no getter for property named 'empname'
in 'class com.lab.entity.Emp'
```

### 原因和修复

`Emp` 类提供 `getEmpName()`，没有 `getEmpname()`。MyBatis 按属性名查找 getter，大小写错误会
导致反射失败。把占位符恢复为 `#{empName}` 后，新增测试通过。

## 问题二：新增成功但主键没有回填

### 复现方法

从 `insertCustom` 临时删除 `useGeneratedKeys`、`keyProperty` 和 `keyColumn`，再运行新增测试。

### 实际现象

MySQL 成功插入一行，Mapper 返回值为 `1`，但 `employee.getEmpId()` 仍为 `null`。
JUnit 在 `MyBatisIntegrationTest.java:51` 的主键断言处失败。

### 原因和修复

数据库生成了自增主键，MyBatis 没有收到把该值写回哪个 Java 属性的配置。最终配置为：

```xml
<insert id="insertCustom" parameterType="Emp"
        useGeneratedKeys="true" keyProperty="empId" keyColumn="emp_id">
```

恢复配置后，测试取得非空主键。测试事务在结束时回滚，实验库仍保留五条初始数据。

## 问题三：MyBatis-Plus 初始化缺少 Spring 类

### 实际现象

首次初始化 MyBatis-Plus 时出现 `NoClassDefFoundError`，缺少
`org/springframework/core/GenericTypeResolver`。

### 原因和修复

当前组合需要 Spring Core 提供泛型解析类。项目在 `pom.xml` 中加入
`spring-core 5.3.30`，随后 BaseMapper、条件构造器和分页测试均通过。

## 最终核验

- `EmpMapper.xml` 已恢复正确的属性名和主键回填配置。
- 全部 9 个测试通过，失败数和错误数均为 0。
- 项目文件没有保存数据库密码，测试只从 `MYBATIS_DB_PASSWORD` 读取密码。
