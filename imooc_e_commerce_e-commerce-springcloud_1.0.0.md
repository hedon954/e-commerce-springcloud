# 数据库设计文档

**数据库名：** imooc_e_commerce

**文档版本：** 1.0.0

**文档描述：** e-commerce-springcloud
| 表名                  | 说明       |
| :-------------------- | :--------- |
| [t_ecommerce_user](#t_ecommerce_user) | 用户表 |
**表名：** <a id="t_ecommerce_user">t_ecommerce_user</a>

**说明：** 用户表

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   bigint   | 19 |   0    |    N     |  Y   |       | 自增主键  |
|  2   | username |   varchar   | 64 |   0    |    N     |  N   |       | 用户名  |
|  3   | password |   varchar   | 256 |   0    |    N     |  N   |       | MD5加密之后的密码  |
|  4   | extra_info |   varchar   | 1024 |   0    |    N     |  N   |       | 额外的信息  |
|  5   | create_time |   datetime   | 19 |   0    |    N     |  N   |   0000-01-0100:00:00    | 创建时间  |
|  6   | update_time |   datetime   | 19 |   0    |    N     |  N   |   0000-01-0100:00:00    | 更新时间  |
