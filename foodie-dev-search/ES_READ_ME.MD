不建议使用ES Template 对索引进行管理
索引就跟数据库的表一样， 我们是不会通过java 代码频删除或修改数据库
我们只针对数据做CRUD

只用ES Template 对数据做CRUD


**ES 对索引进行管理有以下缺点**：

1. 属性 （FieldType）类型不灵活
2. 主分片和副本分片无法设置（replicas,shards 无法设置 参考stu 类）
