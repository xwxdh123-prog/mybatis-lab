# 任务五：Apifox 接口调试记录

## 环境

- Apifox 版本：2.8.45
- 团队：个人团队
- 项目：`mybatis-lab`
- 项目类型：通用项目，以可视化界面维护接口

## 请求记录

| 项目 | 内容 |
| --- | --- |
| 方法 | GET |
| URL | `https://jsonplaceholder.typicode.com/todos/1` |
| 响应状态 | 200 |
| 响应耗时 | 773 ms（本次实测） |
| 响应格式 | JSON |

本次响应正文：

```json
{
  "userId": 1,
  "id": 1,
  "title": "delectus aut autem",
  "completed": false
}
```

## 复现步骤

1. 打开 Apifox，进入个人团队下的 `mybatis-lab` 项目。
2. 在“接口管理”页面选择“快捷请求”。
3. 保持方法为 GET，输入上面的 URL。
4. 点击“发送”，在返回响应区域检查状态码和 JSON 正文。

该接口来自公开测试服务，与本地 MySQL 数据库无关，不会提交账号、邮箱或数据库密码。
