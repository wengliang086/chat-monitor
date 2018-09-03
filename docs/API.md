# 聊天内容监控接口

## 1、接口地址
* http://x.x.x.x:8080/chat/

## 2、请求方式
* GET

## 3、请求参数

| 参数名称 | 类型 | 说明 |
| ----- | ----- | ----- |
| gameId | Long | 游戏ID |
| gameUid | String | 游戏用户ID |
| msg | String | 聊天内容 |

## 4、返回值

* 示例：

```
// 成功
{"code":1,"group":"hException","msg":"成功","value":"ok"}
// 失败
{"group":"hException","code":3,"msg":"存在敏感词汇","value":null
```

## 5、错误码

| code  | 说明 |
| :-----:  | ----- |
| 1  | 聊天内容合法 |
| 2  | UID 不存在 |
| 3  | 存在敏感词汇 |
| 4  | 该用户已经被冻结 |
