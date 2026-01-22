# To do list：

- [ ] 一个http请求的一生
- [ ] 记录项目如何启动，如何内网穿透
- [ ] 添加一个新的功能：登录界面
- [ ] 优化UI设计。
- [ ] 添加一个网站，设置AI心情查询只是其中的一个功能。
- [ ] 完整的一套启动流程
- [ ] 前后端不同功能开放不同的端口
- [ ] Linux命令别名撰写

# 一些额外的知识
## latex
不同的latex编译器有着不同的功能。
| 名称                   | 核心用途一句话               |
| -------------------- | --------------------- |
| **pdfLaTeX**         | 传统、稳定、只适合英文           |
| **LaTeX**            | 生成 DVI 的老方式（基本不用）     |
| **LuaLaTeX**         | 现代 + 可编程 + Unicode    |
| **XeLaTeX**          | 中文 / Unicode / 系统字体首选 |
| **XeTeX**            | XeLaTeX 的底层引擎（很少直接用）  |
| **ConTeXt**          | 另一套宏系统（不是 LaTeX）      |
| **ConTeXt (LuaTeX)** | ConTeXt 的主流方式         |
| **ConTeXt (pdfTeX)** | 老版 ConTeXt            |
| **BibTeX**           | 文献数据库工具（不是编译器）        |



## tree命令
```bash
tree #展示所有子文件和子文件夹
tree -L 2 # 展示子二级文件夹
tree -h   # 以人类可读的方式展示文件夹
tree -I "node_modules" # 忽略node_modules文件夹
```





## Linux中bash别名撰写
打开~/.bashrc文件，添加以下内容：
```bash
alias cas='conda activate stack'
alias cab='conda activate base'
```
类似这样的就可以添加代码的别名了

## 复制文件
```bash
cp 源文件 目标文件 #复制文件
cp -r 源文件夹 目标文件夹 #复制文件夹
cp -i 源文件 目标文件 #表示交互式输入，确保正确
cp -rv src/ src_backup/ #复制文件夹，同时打印调试信息
```
相关参数翻译：
cp:copy       #复制
cp -r:recursive #递归复制
cp -i:interactive #交互式输入
cp -v:verbose #打印调试信息

## 查询文件信息
```bash
ls -lh 文件名   #查看文件信息(ls表示展示，-l表示详细信息(竖着写)，-h表示人类可读)
ls -l --time=birth 文件名 #查看文件的创建时间
ls -ls      # 表示将文件夹中的文件按照大小排列
ls -ld      # 表示只查看文件夹的信息
ls -R       # 表示递归查看文件夹中的文件
stat 文件名  #查看文件的详细信息(stat)
```
## API
API 的全称是 Application Programming Interface(应用程序编程接口)。其主要目的是前后端分离。
一个大型项目会有非常多的业务，API主要是对于这些业务进行分类之后放在不同的接口，比如
`src/api/auth.js `这个用来传输登录和注册的请求。
`src/api/blog.js `这个用来传输博客的请求。
`src/api/game.js `这个用来传输游戏的请求。
然后这些都写在`api`文件夹中，

| 你在干嘛        | 选哪个          |
| ----------- | ------------ |
| 英文论文        | **pdfLaTeX** |
| 中文 / 中英混排   | **XeLaTeX**  |
| README / 笔记 | **XeLaTeX**  |
| 字体 / 自动化    | LuaLaTeX     |
| 学术期刊强制要求    | 按模板          |







# 项目结构：

(base) zzn16@DrZhao:~/Full_stack_study/mood-station$ tree
.
├── README.md
├── backend-java
│   ├── data
│   │   ├── alice.json
│   │   └── zzn16.json
│   ├── pom.xml
│   └── src
│       └── main
│           └── java
│               └── com
│                   └── example
│                       └── demo
│                           ├── DemoApplication.java
│                           ├── controller
│                           │   └── MoodController.java
│                           ├── model
│                           │   └── MoodRecord.java
│                           └── utils
│                               └── FileService.java
├── backend-python
│   ├── __pycache__
│   │   └── main.cpython-310.pyc
│   ├── main.py
│   └── requirements.txt
├── clean.ps1
├── frontend
│   ├── README.md
│   ├── index.html
│   ├── src
│   │   ├── App.vue
│   │   └── main.js
│   └── vite.config.js
└── 我的总的学习指南和一些杂谈.md

15 directories, 18 files

# 一些常用的指令
```bash
lsof -i TCP:8080 #查看8080端口正在listen的进程
lsof -c nginx -i #查看nginx正在listen的端口


```





# 内网穿透(cpolar)

```bash
cd ~/Full_stack_study/mood-station/backend-python #进入目录
uvicorn main:app --host 0.0.0.0 --port 8000 --reload #启动服务器
cpolar http 8000 #内网穿透，相当于把本地的8000端口映射到公网
# 之后仔细看输出，等待Forwarding  https://35744ccd.r30.cpolar.top -> http://localhost:8000 这是内网穿透后的公网地址，之后可以直接使用https://35744ccd.r30.cpolar.top就可以
# 至此，就可以获得内网的公网通行证。
```

# 端口
## 端口分类
在一个计算机上，端口一共有**2^16-1**个，即**65535**个。（由于端口是由16位二进制数表示的，所以有 $2^{16}-1$个也就是从**0000000000000000**到**1111111111111111**）
在端口的世界，我们有一些约定俗成的规定：
| 范围          | 名称                             | 通常用途                             |
| ----------- | ------------------------------ | -------------------------------- |
| 0–1023      | **知名端口（Well-Known）**           | 公认服务，如 Web、FTP、邮件等核心协议。需管理员权限绑定。 |
| 1024–49151  | **注册端口（Registered）**           | 注册给特定应用程序，程序可以申请使用。              |
| 49152–65535 | **动态/私有端口（Dynamic / Private）** | 系统或客户端临时使用，用于短期连接等。              |
## 通信协议：TCP vs UDP
**TCP（Transmission Control Protocol）——“可靠、有序、面向连接”**
TCP协议保证数据必须百分百传达：
1. 保证数据的完整性：双方传输之前要先建立连接，保证双方状态一致，丢失之后会重新传输。
2. 保证数据的可靠性：丢了会重传；有确认机制
3. 保证数据的顺序性：按发送顺序交给应用层（不会乱序给你）
4. 流量控制和阻塞控制：根据对方的网络状况，接受能力实时调整发送速度
使用场景：必须百分百准确，比如网页，文件传输，数据库连接，ssh等。
**UDP（User Datagram Protocol）——“轻量、无连接、尽力而为”**
1. 无连接：不需要建立连接，直接发送数据包。
2. 尽力而为：不保证数据的完整性，丢了不重传。
3. 轻量：没有确认机制，没有流量控制和阻塞控制。
使用场景：不介意丢包，比如视频，游戏，直播，语音通话等。


## 开发过程中常用的端口
我把全栈项目中常见的端口分为四大类：门面（Web服务）、核心（后端应用）、金库（数据库/存储）、后勤（运维/中间件）。
| 分类             | 作用         | 常见端口号                 |
| -------------- | ---------- | --------------------- |
| **门面（Web 服务）** | 给用户 / 前端访问 | 80、443、8080、3000、5173 |
| **核心（后端应用）**   | 业务逻辑服务     | 8080、8081、9000、7001   |
| **金库（数据库/存储）** | 数据持久化      | 3306、5432、6379、27017  |
| **后勤（中间件/运维）** | 支撑系统运行     | 5672、9092、9200、5601   |
---
**前端端口(用户可以直接访问，暴露在公网)**
| 端口       | 用途       | 说明              |
| -------- | -------- | --------------- |
| **80**   | HTTP     | 默认 Web 端口       |
| **443**  | HTTPS    | 生产环境标准          |
| **8080** | HTTP（替代） | 开发/测试最常见        |
| **3000** | 前端开发     | React / Next.js |
| **5173** | 前端开发     | Vite 默认         |
---
**后端端口(一般不会直接暴露在公网)**
| 端口              | 场景                    |
| --------------- | --------------------- |
| **8080**        | Spring Boot / Java 后端 |
| **8081 / 8082** | 多服务区分                 |
| **9000**        | 内部服务（很常见）             |
| **7001**        | 传统 Java 应用            |
---
**数据库端口(字面意思，存放数据)**
| 端口        | 系统            |
| --------- | ------------- |
| **3306**  | MySQL         |
| **5432**  | PostgreSQL    |
| **6379**  | Redis         |
| **27017** | MongoDB       |
| **9200**  | Elasticsearch |
---
**后勤端口(中间内容，运维内容)**
| 端口       | 系统        |
| -------- | --------- |
| **5672** | RabbitMQ  |
| **9092** | Kafka     |
| **2181** | Zookeeper |
| **5601** | Kibana    |
| **9411** | Zipkin    |
---















# git的提交规范(commit format)

| type         | 含义     | 使用场景         |
| ------------ | ------ | ------------ |
| **feat**     | 新功能    | 新增功能         |
| **fix**      | 修复 Bug | 修 bug        |
| **docs**     | 文档     | README / 注释  |
| **style**    | 代码格式   | 空格、分号、不影响逻辑  |
| **refac：tor** | 重构     | 不新增功能、不修 bug |
| **test**     | 测试     | 单元测试、e2e     |
| **chore**    | 杂项     | 构建、脚本、依赖     |

格式举例：
```bash
git commit -m "feat(user): 新增用户登录功能"
git commit -m "fix(order): 修复订单金额计算错误"
git commit -m "style(user): 格式化用户模块代码"
git commit -m "refactor(auth): 重构权限校验逻辑"
git commit -m "docs(readme): 补充项目启动说明"
git commit -m "test(order): 增加订单模块单元测试"
git commit -m "chore(deps): 升级 axios 到 1.6.0"
git commit -m "perf(list): 优化列表渲染性能"
git commit -m "ci(github): 添加 GitHub Actions 自动部署"
```













# uvicorn的使用方法

```bash
uvicorn main:app --host 0.0.0.0 --port 8000 --reload --workers 4 --log-level debug --limit-concurrency 100
```

以这个指令为例我们来进行学习。

**uvicorn**：是一个Python的ASGI服务器，用于运行Python的Web应用。
**main:app**：表示从main.py文件中导入app对象，main来自main.py是文件名，app是对象名。表示运行main.py文件中的app对象。app = FastAPI()这个定义了app对象，app这个名字从这里诞生。@app.post("/analyze")这里注册了这个app这个名字在FastAPI里面，最后这个app这个名字在uvicorn里面被使用。

```plaintxt
在这里，我们讲解三个内容：端口号，监听和host的含义。
    互联网相当于地球村，有些是商铺，有些是小区，每一个商铺的地址就是公网IP，每一个小区的地址就是私有IP。私有IP是路由器分配给每个设备的IP地址，只能在内网访问。这个私有IP相当于小区里面的门牌号，公网IP相当于小区的门牌号。小区会有一个人来站岗，这个叫做网关（路由器），负责将所有消息进行转发。
    在商铺中，人们进来可以到不同的柜台选择不同的商铺，这些柜台就是端口，每个端口对应不同的服务。IP 地址只负责把客人带到商场门口，而端口号负责把客人带到具体的业务柜台。如果柜台营业，那么这个端口就叫做正在监听（listen）。反之则是不营业。
    在网络中，分成公网IP和私有IP，公网IP是可以在互联网上访问的IP地址，私有IP是只能在内网访问的IP地址。
    监听表示服务器正在等待客户端的连接请求，如果监听的端口被占用，那么服务器就无法启动。
    host表示服务器监听的IP地址，0.0.0.0表示监听所有IP地址。IP地址一共有三种类型，0.0.0.0表示监听本机的所有网络接口。127.0.0.1表示监听本机的回环地址，只能在本机访问。正常的那种10.90.56.175则表示监听指定的网络接口。一个设备可以有很多个这种正常的网络IP地址。
```

**--host 0.0.0.0**：表示服务器监听的IP地址，0.0.0.0表示监听所有IP地址。
**--port 8000**：表示服务器监听的端口号，8000表示监听8000端口。
**--reload**：表示启用热重载，当代码发生变化时，服务器会自动重启。(方便直接进行调试)
**--workers 4**：表示启用4个工作进程，用于处理请求。
**--log-level debug**：表示设置日志级别为debug。
**--limit-concurrency 100**：表示限制并发连接数为100。

整个指令翻译过来就相当于：
**使用uvicorn服务器，运行main.py文件中的app对象，监听所有IP地址的8000端口，启用热重载，启用4个工作进程，设置日志级别为debug，限制并发连接数为100。**

# `.gitignore`的语法格式
`.gitignore`文件用于指定哪些文件或目录应该被Git忽略，不纳入版本控制。
**注意：.gitignore这个文件只能对没有经过git跟踪的文件进行忽略，对已经跟踪的文件无效。**
首先，`.gitignore`中一行表示一个规则，空行会被忽略，注释用 `#`表示。
常用的规则：
```gitignore
#=======================================================
#1，精准文件名匹配（只能精准忽略特定名字的文件）
#=======================================================
source.env   
# 表示能够精准忽略所有名字叫做source.env的文件,其他任何名字都不能进行忽略。

#=======================================================
#2，配通符*
#=======================================================
*.env
# 表示能够忽略所有名字以.env结尾的文件，但是不能忽略其他名字的文件。*类似一个占位符，可以匹配任意长度的字符。如果写成.env*，则可以表示.env开头的文件。同理，如果写成*.env*，则可以表示.env中间的文件。总而言之这个东西就是一个占位符，可以表示任意的0或者任意长度的任意字符。





#=======================================================
#3，反向规则！(一般是两个连着使用)
#=======================================================
source.env.*
!source.env.example
# 表示能够忽略所有名字以source.env开头的文件，但是可以保留source.env.example文件。这个算子是可以与*算子进行叠加。

#=======================================================
# 四、目录忽略（以 / 结尾）
#=======================================================
node_modules/
# 表示能够忽略node_modules目录下的所有文件。


#=======================================================
# 五、单字符通配符 ?（匹配一个字符）
#=======================================================
file?.txt
#表示能够忽略file1.txt，file2.txt，file3.txt等文件，但是不能忽略file.txt文件以及file10.txt文件。*是任意长度的占位符，？是任意一个字符的占位符。


#=======================================================
# 六、字符范围通配符 [ ]（匹配一个字符）
#=======================================================
file[123].txt
*.py[cod]
# 等价于：*.pyc  *.pyo  *.pyd   [] 表示“多个字符中的一个”，常用于精简写法,?表示任意一个字符。,[]表示[]中的多个字符中的一个。


#=======================================================
# 七、任意层级目录匹配 **
#=======================================================
**/node_modules
# 表示能够忽略node_modules目录下的所有文件。**表示从整个文件中寻找所有目录中包含node_modules的目录。比如：src/node_modules,backend/node_modules等。
**/*.tmp
# 表示能够忽略项目中所有 .tmp 文件。注意这个和*.tmp是一致的。
# 同时 **是任意层级的占位符，*是任意文件的占位符
``` 

# curl的使用方法

```bash
curl -v -i -X POST "http://127.0.0.1:8000/analyze" \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer my_secret_key" \
     -H "User-Agent: MoodBot/1.0" \
     -d '{"text": "Learning Linux is cool", "user_id": 101}' \
     -o response.json \
     -L \
     --connect-timeout 10 \
     --retry 3 \
     -k
```

以这个指令为例，我们进行学习。
**curl**表示使用curl命令行工具发送HTTP请求。curl本质上是一个没有界面的浏览器，可以直接通过命令行向服务器发送HTTP请求。
**-v**：显示详细的请求和响应信息，包括请求头、响应头、状态码等。
**-i**：显示响应头信息。(include)
**-X**：指定代理服务器。(proxy)****注意这个X一定要大写****
**POST**：表示使用POST方法发送请求。与其对应的还有GET、PUT、DELETE等方法。
POST指的是向服务器发送数据，GET指的是从服务器获取数据。PUT指的是更新服务器上的数据，DELETE指的是删除服务器上的数据。

```bash
-v -i -X POST "http://127.0.0.1:8000/analyze" \
```

表示的是
**我要用 POST 的方式，
向我本机 8000 端口上的 /analyze 接口发送请求，
并且请你把请求是怎么发的、服务器是怎么回的、
包括响应头在内，统统详细地打印出来给我看。**
**-H**：表示HTTP请求头信息。三个-H分别表示：
Content-Type: application/json：表示请求体是JSON格式。              (设置请求头)
Authorization: Bearer my_secret_key：表示请求头中包含认证信息。     (设置认证头)
User-Agent: MoodBot/1.0：表示请求头中包含用户代理信息。             (设置用户代理头)
**-d**：表示请求体数据，也就是要发送给服务器的数据。
{"text": "Learning Linux is cool", "user_id": 101}：表示请求体数据是JSON格式，包含text和user_id两个字段。(设置请求体数据)
**-o**：表示将响应内容保存到文件中。(output)
response.json：表示将响应内容保存到response.json文件中。(设置输出文件)
**-L**：表示跟随重定向。(follow redirects)，如果在设定的地址中没有找到资源，会自动重定向到其他地址。(如果服务器返回301、302等重定向状态码，curl会自动跟随重定向到新的URL)如果没有这个，curl不会自动跟随重定向，会直接返回重定向状态码。
**--connect-timeout 10**：表示连接超时时间。(connect timeout)，如果在10秒内无法连接到服务器，curl会自动放弃连接。(如果服务器响应时间过长，curl会自动放弃连接)
**--retry 3**：表示重试次数。(retry)，如果请求失败，curl会自动重试3次。(如果服务器响应时间过长，curl会自动重试)
**-k**：表示不验证SSL证书。(insecure)，如果服务器使用了SSL证书，curl会自动验证SSL证书。(如果服务器使用了自签名证书，curl会自动放弃连接)

在一般情况下，我们不需要使用这些参数，除非服务器有特殊要求。一般我们个人开发的时候，curl只需要用到-v和-d即可。

## 日常常用的curl指令

```bash
# 查看服务器是否运行
curl -v "http://127.0.0.1:8000/"  
# 向服务器发送数据（POST到服务器，然后返回结果）
curl -X POST "http://127.0.0.1:8000/analyze" \
     -H "Content-Type: application/json" \
     -d '{"text": "调试代码真开心"}'
# 包含身份验证的请求
curl -X GET "http://127.0.0.1:8000/users/me" \
     -H "Authorization: Bearer 你的Token字符串"

# 常用的万能模板
curl -v -X POST "http://目标地址" \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer <如果有Token填这里>" \
     -d '{
           "字段1": "值1",
           "字段2": "值2"
         }'
```

# 请求和响应

请求和相应是http通信中非常重要的一部分内容。整体来说，一个请求包含**请求行**、**请求头**、**请求体**三个部分，一个响应包含**状态行**、**响应头**、**响应体**三个部分。

## 请求

一个完整的http请求可以分成三部分：**请求行**、**请求头**、**请求体**，一个请求相当于往服务器发送了一个快递。
**请求行**：(相当于快递的核心指令)包含请求方法、请求路径、HTTP版本 比如 `GET /api/users HTTP/1.1`表示使用GET方法请求/api/users路径，HTTP版本为1.1，`POST /api/users HTTP/1.1`表示使用POST方法请求/api/users路径，HTTP版本为1.1
**请求头**：(相当于快递的详细信息)包含请求的元数据，如Content-Type、Authorization等，比如`Content-Type: application/json`表示请求体是JSON格式，`Authorization: Bearer 你的Token字符串`表示请求需要身份验证
**请求体**：(相当于快递的内容)包含请求的数据，如JSON、XML等，比如`{"name": "张三", "age": 18}`

## 响应

一个完整的http响应可以分成三部分：**状态行**、**响应头**、**响应体**，一个响应相当于服务器返回给客户端的一个快递。
**状态行**：(相当于快递的状态)包含状态码、状态消息、HTTP版本，比如`200 OK`表示请求成功，`404 Not Found`表示请求的资源不存在
**响应头**：(相当于快递的详细信息)包含响应的元数据，如Content-Type、Authorization等，比如`Content-Type: application/json`表示响应体是JSON格式，`Authorization: Bearer 你的Token字符串`表示响应需要身份验证
**响应体**：(相当于快递的内容)包含响应的数据，如JSON、XML等，比如`{"name": "张三", "age": 18}`

举一个例子：

---

```http
POST /analyze HTTP/1.1 

Host: 127.0.0.1:8000
User-Agent: curl/7.81.0
Accept: */*
Content-Type: application/json
Content-Length: 68

{"text": "今天学到了很多新知识，感觉非常充实！"}
```

**请求行**：`POST /analyze HTTP/1.1`
这个表示向http://127.0.0.1:8000/analyze 路径发送一个POST请求
**请求头**：`Host: 127.0.0.1:8000 User-Agent: curl/7.81.0 Accept: */* Content-Type: application/json Content-Length: 68`
host表示服务器地址，User-Agent表示客户端信息，Accept表示接受的响应格式，Content-Type表示请求体格式，Content-Length表示请求体长度
**请求体**：`{"text": "今天学到了很多新知识，感觉非常充实！"}`
请求体内容为{"text": "今天学到了很多新知识，感觉非常充实！"}

```http
HTTP/1.1 200 OK

Date: Tue, 13 Jan 2026 08:45:12 GMT
Server: uvicorn
Content-Length: 135
Content-Type: application/json

{
  "score": 98,
  "lucky_color": "天蓝色",
  "ai_comment": "知识带来的充实感是无可替代的，今天的你闪闪发光！"
}
```

**响应行**：`HTTP/1.1 200 OvueK`
这个表示服务器返回了一个状态码为200的响应
**响应头**：`Date: Tue, 13 Jan 2026 08:45:12 GMT Server: uvicorn Content-Length: 135 Content-Type: application/json`
date表示响应时间，server表示服务器信息，content-length表示响应体长度，content-type表示响应体格式
**响应体**：`{"score": 98, "lucky_color": "天蓝色", "ai_comment": "知识带来的充实感是无可替代的，今天的你闪闪发光！"}`
表示服务器发送给客户端的数据内容

---

# URL

url指的是统一资源定位符，用于标识网络上的资源位置，比如`http://127.0.0.1:8000/analyze`表示http协议，127.0.0.1是服务器地址，8000是端口，/analyze是路径。注意这里采用的是动态web服务，URL 是“资源”的虚拟逻辑地址，而不是物理地址。中间采用Dynamic Routing技术，根据路径参数动态生成响应内容。@app.post("/analyze")中的/analyze就是路径参数，表示POST请求的路径。通过这个东西的注册来实现的。本质上这个是一个触发器，当客户端发送POST请求到/analyze路径时，服务器会调用对应的处理函数。

# vue

为了掌握前端开发，首先我们要学习vue的相关内容，vue相当于一个框架，我们在进行开发的时候只需要在vue这个框架里进行填充就可以。

如果要撰写原生的js，每次修改都是局部的，同时变量也是局部的，修改的时候必须一次修改很多，如果忘了任何一处的修改都不行。同时你还要负责页面渲染等等脏活累活。

但是vue是一个申明式的框架，你只需要申明你想要什么，然后vue会自动帮你完成渲染，你只需要关注业务逻辑即可。同时也可以将一个一个的功能进行分割，

## .vue文件的结构

### 核心概念

一个.vue文件可以分成三个部分：

```txt
<template> 是(骨架/HTML)：

<script> 是(大脑/JS)：

<style> 是(衣服/CSS)：
```

### 常用语法

| 正式名字 | 别名 / 缩写 | 类型 | 作用 | 记忆口诀 / 比喻 |
|---------|-------------|------|------|----------------|
| 插值表达式 {{ 变量 }} | {{ }} | 模板语法 | 在模板中显示变量内容 | 公告板，变量变它就变 |
| v-bind | : | 指令 | 绑定 HTML 属性为动态值 | 贴标签，让属性活起来 |
| v-on | @ | 指令 | 绑定事件监听 | 门铃 / 遥控器按钮，一按就办事 |
| v-model | v-model | 指令 | 数据双向绑定 | 照镜子，输入框和变量同步 |
| v-if | v-if | 指令 | 条件渲染（控制是否存在） | 隐身斗篷，真出现，假消失 |
| v-for | v-for | 指令 | 列表循环渲染 | 复印机，有一份数据印一份 |
| computed | computed | 计算属性 | 基于已有数据计算新值（有缓存） | 自动计算器，不变不算 |
| watch | watch | 侦听器 | 监听数据变化并执行操作 | 监控摄像头，变了就报警 |
| 生命周期钩子 | lifecycle hooks | 机制 | 控制组件不同阶段的行为 | 人生流程：出生 → 成长 → 销毁 |
| 组件 | component | 架构 | 拆分 UI，复用页面结构 | 积木块，拼页面 |
| 路由 | router | 架构 | 页面跳转与 URL 管理 | 地图导航，指路用 |

### 操作步骤

首先，我们撰写.vue文件之后，这个是对于人类友好的一个界面，但是流=计算机并不认识这些内容，所以哟啊使用有一个工具来翻译这个语言，这个工具就是vite。我们可以把vite理解成前端编译器+本地服务器。vite可以做4件事情：

- 读取`package.json`文件
- 加载vue编译器
- 把`.vue`翻译成js文件
- 启动一个本地服务器(localhost:5173)
  注意：package.json是前端工程的身份证+说明书，里面记录了前端工程的依赖和配置信息。里面至少有三件事情：

<span style="color: red;">

```json
{
  "name": "frontend",
  "scripts": {
    "dev": "vite"
  },
  "dependencies": {
    "vue": "^3.x",
    "axios": "^1.x"
  }
}
```

第一个说明了我要使用vue
第二个说明了我要使用axios
第三个说明了`npm run dev`相当于运行vite
第四个说明了`npm run build`相当于运行vite build

</span>

# node

node是一个可以在本地运行JS的工具，同时也类似一个工具包，可以更加方便的进行前后端的测试。

## vite和axios

**node**是一个运行环境，本来浏览器的css,js,html是无法运行的，但是通过**node**的环境就可以让这些内容来运行。(更加准确的，JavaScript无法在电脑上运行，Node就是运行JS的环境)通过node更加方便的进行运行。

在**node**中，有两个最重要的工具：一个是**vite**，另一个是**axios**。**vite**可以编译`.vue`文件，让`.vue`文件转换成浏览器认识的css,js,html内容。

**axios**可以发送http请求，让后端返回的数据可以被前端接收。
如果用python来类比，可以理解成：**vite**相当于python main.py，**axios**相当于requests.get()

但是我们又该如何调用这两个工具呢？我们可以使用`package.json`来调用。

## 核心文件`package.json`

首先，我们来举一个具体的例子：

```json
{
  "name": "mood-station",
  "version": "1.0.0",
  "description": "我的心情气象站前端",
  "author": "zzn16",
  "license": "MIT",
  "homepage": "https://github.com/zzn16/mood-station",
  "scripts": {
    "dev": "vite",
    "build": "vite build",
    "preview": "vite preview",
    "lint": "eslint .",
    "test": "vitest"
  },
  "dependencies": {
    "vue": "^3.3.4",
    "axios": "^1.6.0",
    "pinia": "^2.1.0",
    "vue-router": "^4.2.0"
  },
  "devDependencies": {
    "vite": "^5.1.0",
    "eslint": "^8.0.0",
    "prettier": "^3.0.0",
    "vitest": "^1.2.0"
  },
  "private": true,
  "engines": {
    "node": ">=18"
  }
}
```

`package.json`是前端工程的**身份证**+**说明书**+**遥控器**，我们挑选一些重要的具体功能如下：

---

- **身份证**：记录记录项目名称、版本、作者等基本信息

```json
"name": "mood-station",
"version": "1.0.0",
"description": "我的心情气象站前端"
```

这里的`name`表示项目名称，`version`表示项目版本，`description`表示项目描述。这个没有什么需要重点说明的。

---

- **依赖说明书**：记录项目依赖和配置信息

```json
"dependencies": {
  "vue": "^3.3.4",
  "axios": "^1.6.0"
},
"devDependencies": {
  "vite": "^5.1.0"
}
```

这里的`dependencies`表示项目依赖，`devDependencies`表示开发依赖。在这里，`vue`表示使用vue框架，`axios`表示使用axios工具，`vite`表示使用vite工具。后面的内容表示版本号，`^`表示可以使用最新版本。

---

- **遥控器**：记录项目脚本命令

```json
"scripts": {
  "dev": "vite",
  "build": "vite build",
  "preview": "vite preview"
}
```

这里的`scripts`表示脚本命令，`dev`表示开发环境，`build`表示构建环境，`preview`表示预览环境。其中，`vite`表示调用vite工具，`build`表示调用vite build工具，`preview`表示调用vite preview工具。

---

总而言之：

- 身份证：告诉别人项目是谁、干什么
- 说明书：告诉 Node / npm 运行这个项目需要哪些库
- 遥控器：告诉 npm / Node 你可以用哪些命令快速执行任务

# Java

Java是后端开发的主流语言，他是一种编译的语言，python是解释性的，java,C++都是编译性的。
java比起其他语言，有以下优势：
1，**java非常之规范**，java的代码设计有80%来自C++，一切变量都要进行申明，比如 `int a = 10;`这一点比python的 `a = 10`要规范得多。
2，**java牺牲了一部分内存管理**，换来了工程稳定性，java的内存管理由JVM来管理，可以实现一次编写，到处运行。同时避免了C和C++的野指针问题。强类型，强约束，非常适用于工程管理。
3，**java可维护性很强**，对于C来说维护极其困难，但是java就会方便很多。

## 设计规范：**分层架构**（MVC）

java设计会有很多的规范，比如`Model + Service + Controller + Application`，
- Model（数据定义层）
这一个层专门对于数据进行处理，**统一数据结构的标准格式**
`backend-java/src/main/java/com/example/demo/model/MoodRecord.java`



- Service（业务逻辑层）
这一个层专门对于业务逻辑进行处理，**统一业务逻辑的标准格式**，相当于撰写详细的算法，来进行业务逻辑的处理
`backend-java/src/main/java/com/example/demo/utils/FileService.java`



- Controller（接口/控制层）
接请求、调别人、回结果。相当于只负责复杂数据的来回交互
`backend-java/src/main/java/com/example/demo/controller/MoodController.java`


- Application（启动入口）
启动整个系统，相当于启动一个服务器
`backend-java/src/main/java/com/example/demo/DemoApplication.java`


- pom.xml（项目配置文件）
`backend-java/pom.xml`
这个文件记录了项目依赖和配置信息。










# JavaScript(ES6+)

# HTML / CSS

# 全栈构建流程



首先，为了完成这个全栈项目，我们一定要有一个方法流程。我们可以采用这样的方法：

1，需求分析与数据定义 (定规矩)

2，独立微服务开发 (Python) (造大脑)

3，核心业务后端开发 (Java) (造躯干)

4，接口联调测试 (API Testing) (神经测试)

5，前端页面开发 (Vue) (造皮肤)

6，最终集成与验收 (合体)
整体来说，可以理解成先写一个小功能，然后把API接口写好，然后让这个功能注册，然后在前端进行修改，然后测试，然后集成到一起，最后验收。
**需求 → API → 服务 → 调用 → 页面 → 验证 → 合并**

所以在这个任务中，我要先来搭建一个简单的前端界面，然后写一个按钮，撰写按钮之后，将后端绑定到这个按钮上(这个时候我可以撰写后端相关的代码，然后将其进行通讯)，然后测试，然后集成到一起，最后验收。

## 前端搭建

1，撰写`package.json`来规定相关规则
2，撰写`vite.config.js`和`index.html`作为vite的说明书和入口文件
3，撰写`src/main.js`作为vue的入口文件(挂载Vue实例)
4，撰写`src/App.vue`作为vue的主组件
5，运行`npm install`来安装依赖
6，运行`npm run dev`来启动vite

至此，一个简单的前端就基本搭建完成。

## 后端搭建

在我的代码中，采用了双后端的方法，一个后端是python，另一个后端是java。
在这两个代码中，python负责处理AI的逻辑，java负责处理文件的逻辑。
python我撰写了`main.py`，java我撰写了`DemoApplication.java`，`MoodController.java`，`FileService.java`，`MoodRecord.java`，`pom.xml`这几个文件。

其中
`main.py`是python的启动文件
`DemoApplication.java`是java的启动文件
`MoodController.java`是java的接口文件
`FileService.java`是java的业务逻辑文件
`MoodRecord.java`是java的数据定义文件
`pom.xml`是java的项目配置文件

## 所有代码的路径：

### 前端代码
[前端代码App.vue](../frontend/src/App.vue)
[前端代码main.js](../frontend/src/main.js)
[前端代码index.html](../frontend/index.html)
[前端代码vite.config.js](../frontend/vite.config.js)
[前端代码package.json](../frontend/package.json)

### 后端代码(Java)

[后端代码DemoApplication.java](../backend-java/src/main/java/com/example/demo/DemoApplication.java)

[后端代码MoodController.java](../backend-java/src/main/java/com/example/demo/controller/MoodController.java)

[后端代码FileService.java](../backend-java/src/main/java/com/example/demo/utils/FileService.java)

[后端代码MoodRecord.java](../backend-java/src/main/java/com/example/demo/model/MoodRecord.java)

[后端代码pom.xml](../backend-java/pom.xml)

### 后端代码(Python)

[后端代码main.py](../backend-python/main.py)

[后端代码requirements.txt](../backend-python/requirements.txt)



# 全栈启动+内网穿透


# 一个http请求的一生
接下来，我们举一个非常具体的例子来解释一个请求的一生：

---
1. 用户首先撰写内容，然后点击发送按钮。
2. 浏览器做DNS解析，具体算法可以查阅，大致原理就是将目标域名`6ea2367d.r30.cpolar.top`转换成cpolar的IP地址`**.*.*.*`。
3. 浏览器于cpolar服务器建立TCP连接，做TLS握手，然后发送Http请求：
```http
POST /api/mood HTTP/1.1
Host: 6ea2367d.r30.cpolar.top
Content-Type: application/json
Content-Length: 23

{"username": "zzn16", "text": "今天天气真好！"}
```
4. cpolar服务器接收到请求后，将请求转发到我的电脑的8080端口。(`http://localhost:8080`)
5. 通过8080端口找到正在listen的进程：`java    32236 zzn16   36u  IPv6 16615863      0t0  TCP *:http-alt (LISTEN)`，这个是一个java进程。
从现在开始，请求正式进入了我的服务器。(请求行，请求头，请求体)
---

6. 进入Java：Tomcat开始干活：
[后端代码DemoApplication.java](../backend-java/src/main/java/com/example/demo/DemoApplication.java)
请求进入之后，在这里的
```java
@SpringBootApplication
public class DemoApplication {
  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
```
这一段内容启动内置 Tomcat，并把 Spring MVC 挂上去。在接收到请求之后，Tomcat会解析对应的请求，把他变成：
```plaintxt
请求行：POST /api/mood HTTP/1.1

headers：Content-Type: application/json

body：{"username":"zzn16","text":"..."}
```
然后回生成类似这样的对象：
```plaintext
HttpServletRequest req
HttpServletResponse resp
```
7. 进入spring
Tomcat解析之后，会把请求交给DispatcherServlet这个总调度。通过
```plaintxt
Filter（过滤器）
→ DispatcherServlet（总调度）
→ HandlerMapping（找谁处理）
→ HandlerAdapter（怎么调用）
→ 参数解析（JSON变对象）
→ 调用 Controller 方法
→ 返回值序列化成 JSON
→ 写回响应
```

8. spring再进行一次路由匹配
[后端代码MoodController.java](../backend-java/src/main/java/com/example/demo/controller/MoodController.java)
Spring 会做一次“路由匹配”：

`/api` + `/mood` → `/api/mood`
`HTTP` 方法是 `POST`
所以命中 `submitMood(...)`

9. 和python进行通讯
[后端代码MoodController.java](../backend-java/src/main/java/com/example/demo/controller/MoodController.java)
```java
    @PostMapping("/mood")
    public MoodRecord submitMood(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String text = payload.get("text");
        System.out.println("收到前端请求: " + text);
        // 1. 呼叫 Python 小弟 (FastAPI)
        RestTemplate restTemplate = new RestTemplate();
        String pythonUrl = "http://localhost:8000/analyze";
        // 准备发给 Python 的数据
        Map<String, String> pythonPayload = Map.of("text", text);
        // 拿到 Python 的结果
        Map aiResult = restTemplate.postForObject(pythonUrl, pythonPayload, Map.class);
        // 2. 数据转换与封装
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        MoodRecord newRecord = new MoodRecord(
            now,
            text,
            (Integer) aiResult.get("score"),           
            (String) aiResult.get("lucky_color"),      
            (String) aiResult.get("ai_comment")        
        );
```
字面意思，这个直接可以与python进行通讯，
由于`uvicorn main:app --host 0.0.0.0 --port 8000`，所以python的地址是`http://localhost:8000/analyze`，然后就可以将数据与python进行交互，交互完成之后python会返回
```json
{
  "score": 85,
  "lucky_color": "blue",
  "ai_comment": "今天心情不错"
}
```
10. 保存到硬盘
[后端代码FileService.java](../backend-java/src/main/java/com/example/demo/utils/FileService.java)
```java
// 3. 保存到硬盘
fileService.saveRecord(username, newRecord);
```

11. 返回给Vue
[后端代码MoodController.java](../backend-java/src/main/java/com/example/demo/controller/MoodController.java)
```java
// 4. 返回给 Vue
return newRecord;
```

---
这个就是一个http请求的一生，也就是这个内容到了我的服务器之后如何来回运动的。

# 启动方法
项目启动有两种方法，一种是本地进行调试，一种是进行发布。

## 本地调试
1，启动python服务，FastAPI服务。
2，启动java后端。
3，启动前端服务。
然后打开之后就可以访问了。
```bash
### 启动python服务，FastAPI服务
conda activate stack
cd ~/Full_stack_study/mood-station/backend-python
uvicorn main:app --host 0.0.0.0 --port 8000
```

```bash
### 启动java后端
cd ~/Full_stack_study/mood-station/backend-java
mvn spring-boot:run
```

```bash
### 启动前端服务
cd ~/Full_stack_study/mood-station/frontend
npm run dev
```

## 发布
1，启动python服务，FastAPI服务。
2，启动java后端。
3，用生产构建 + Spring Boot 托管前端
4，使用cpolar进行推送。

```bash
### 启动python服务，FastAPI服务
conda activate stack
cd ~/Full_stack_study/mood-station/backend-python
uvicorn main:app --host 0.0.0.0 --port 8000
```

```bash
### 启动java后端
cd ~/Full_stack_study/mood-station/backend-java
mvn spring-boot:run
```

```bash
### 启动前端服务
cd ~/Full_stack_study/mood-station/frontend
npm run build
cd ~/Full_stack_study/mood-station
rm -rf backend-java/src/main/resources/static/*
cp -r frontend/dist/* backend-java/src/main/resources/static/
cd ~/Full_stack_study/mood-station/backend-java
mvn spring-boot:run
```

```bash
### 使用cpolar进行推送
cpolar http 8080
```
# 添加功能(这个非常重要，主要学习项目的维护和重构)






























