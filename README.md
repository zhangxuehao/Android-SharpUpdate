首先在感谢开源社区作者afollestad提供的兼容性material风格项目[material-dialogs](https://github.com/afollestad/material-dialogs),这个风格的Dialog更适合Android机型的界面更新。

首先如果你的app想要进行更新，你需要提供的是以下接口。

|        | 必选 | 类型 | 说明 |
|:-----: |:-----------------|:------|:---------|
| lastAppVersion | 是   | String | 最新的app版本号|
| downloadUrl | 否   | String | 下载地址，不需要可不提供 |
| updateDescription|否 | String|  下载说明|
|isImposed |否|-|是否强制更新|

加上本地可以根据包名来获取我们需要更新的APP版本号的信息，我们就可以来完成更新的功能。**这里强调一点：此项目针对于版本名称versionName（manifest或gradle中）的正则为 数字和点 拼接的版本号名称，如果您的APP版本名称versionName为其他特殊字符串，此项目并不适用，非常抱歉。**

