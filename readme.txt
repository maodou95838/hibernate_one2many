hibernate一对多关联映射（双向Classes<--->Student）

采用一对多双向关联映射的目的主要是为了主要是为了解决一对多单向关联的缺陷
而不是需求驱动的

一对多双向关联的映射方式：
	* 在一的一端的集合上采用<key>标签，在多的一端加入一个外键
	* 在多的一端采用<many-to-one>标签
	
！！！注意：<key>标签和<many-to-one>标签加入的字段保持一直，否则会产生数据混乱

inverse属性：
	* inverse属性可以用在一对多和多对多双向关联上，inverse属性默认为false，为
	false表示本端可以维护关系，如果inverse为true，则本端不能维护关系，会交给另一端
	维护关系，本端失效。
	
	所以一对多关联映射我们通常在多的一端维护关系，让一的一端失效，所以设置为inverse为true
	
inverse和cascade
	* inverse是控制方向上的反转，只影响存储
	* cascade是操作上的连锁反映
