# AndroidHttpLib
一个对OKHttp3再次封装的一个公司级的网络请求框架

## 用法
没打jar包.此项目主要是为将要封装okhttp的开发者提供思路.当然此项目也可直接使用.本司已经将此库打包上传至maven,开发了两款成熟app.

## 目前已有的功能/亮点
* get,post,patch,delete请求.
* 文件/多文件上传 文件下载,并均有进度回调.
* 简化的网络请求.
* 返回数据的实体类化.
* 完整的请求 主线程回调.
* 优化过的网络数据log.
* 支持请求类型(form/raw等)设置.
* 支持https.
* 支持添加自定义拦截器.
* 支持自定义数据转换(请求参数转换/请求到的数据转换).
* 面向接口开发,当你想使用其他网络请求框架代替okhttp,只需改动很少的代码便可实现替换.
* 稳定的对外api,底层库的替换,也不会影响到业务项目的开发.

##  

