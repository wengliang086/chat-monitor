# 聊天内容监控接口

## 1、接口地址
* http://x.x.x.x:8080/chat/

## 2、请求方式
* GET

## 3、请求参数

| 参数名称 | 类型 | 说明
| ------------ | ------------ |
| mobile | String | 手机号码
| productId | Integer | 产品ID
| accessToken | String | 使用MD5加密 productId+"."+requestTime+"."+productKey 返回十六进制32位小写字符串
| requestTime | Long | 发出请求时的时间，单位毫秒。当服务器接受请求时，requestTime应该在当前时间的前五分钟之内， 否则请求失效。

## 4、返回值


## 5、错误码

