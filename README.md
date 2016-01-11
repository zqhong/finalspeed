# finalspeed
FinalSpeed是高速双边加速软件,可加速所有基于tcp协议的网络服务,在高丢包和高延迟环境下,仍可达到90%的物理带宽利用率,即使高峰时段也能轻松跑满带宽.

### 安装教程
[客户端安装说明](http://www.d1sm.net/thread-7-1-1.html)
[服务端安装说明](http://www.d1sm.net/thread-8-1-1.html)


### 命令行版本的客户端
默认是图形化版本的客户端，不方便部署在服务器。如果需要命令行版本的，请在 [Releases](https://github.com/zqhong/finalspeed/releases) 中下载。

使用方法：`sudo java -jar client.jar`。如果在 Windows 下，则需要使用管理员运行。

配置文件 - clien_config.json
```json
{
    // 下载速度，单位是 B，字节。这里换算起来就是 11MB。请把这里改成本机的下载速度
    "download_speed": 11200698, 
    // 协议：tcp 或 udp。注意：服务端如果是 OpenVZ 架构的话，则只支持 udp。
    "protocal": "udp", 
    // 服务器地址
    "server_address": "1.2.3.4", 
    // 一般不需要更改，保持默认即可。
    "server_port": 150, 
    // 不需要更改，保持默认即可。
    "socks5_port": 1083, 
    // 上传速度，单位是 B，字节。
    "upload_speed": 357469
}
```

配置文件 - port_map.json
```json
{
    "map_list": [
        {
            // 要加速的服务器端口
            "dst_port": 12345, 
            // 本地端口
            "listen_port": 1099, 
            // 备注信息
            "name": "ss"
        }, 
        {
            "dst_port": 23456, 
            "listen_port": 2200, 
            "name": "ssh"
        }
    ]
}
```

需要注意的是，这两个配置文件不能删除，不能可能会弹出配置文件的窗口。（没有完全去掉图形化界面的缘故）


### 其他信息
项目运行需要安装libpcap,windows下为winpcap.

客户端启动类: net.fs.client.FSClient

服务端启动类: net.fs.server.FSServer

论坛 http://www.d1sm.net/forum-44-1.html

#### 感谢
该项目 fork 自 [dlsm/finalspeed](https://github.com/d1sm/finalspeed)，感觉作者的付出。
