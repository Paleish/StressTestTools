# StressTestTools  
java -jar -client StressTestTool-0.1-SNAPSHOT.jar 2 1 2000  
启动参数为3个  
第1个参数是ai设别号，为了多台PC同时测试，取到不同ID的AI，设置不同的ai设别号，对应数据库kys_player表，is_ai字段，设置不重复且大于1小于128的数字。  
  
第2个参数是测试模式，0为自动模式，1为手动模式。  
  
第3个参数为自动模式下，开启的ai数量。  

自动模式下不用后续操作，但是第一次需要开启手动模式，生成其使用的ai设别号的ai记录。  
  
手动模式：  
需要在前台执行以输入命令，命令与参数之间用分号;隔开。  
1．init;$     参数为初始化的ai数  
2. login       让所有初始化的ai登入游戏  
3. join        让所有登入游戏的ai加入匹配队列,同时ai会自动进行抽奖和兑换  
4.wincup;$   让所有登入的ai进行抽奖，参数为抽奖等级1-3，11-13  
5．exc;$      让所有登入的ai进行兑换，兑换id见数据库convert_goods表  
6. create;$     以启动jar时输入的ai识别号创建参数个ai  
  
由于测试工具尚不完善，建议单机启动不超过两千个。  
