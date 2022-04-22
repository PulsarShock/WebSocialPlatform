package com.zlz.websocialplatform.entity;

public class MyInteger {

    /*
     Mybatis不能把查询到的结果映射为List<Integer>，因为Integer没有setValue()，其他包装类同理，所以只能做这个转换
     还需要说明的是，如果自定义的类的字段和数据库中字段相同，那么不用添加任何除@Select的注解，Mybatis可以自动映射为List。一旦字段不相同，就必须加上下面两条注解
     @ResultType(List.class)
     @Result(column="",property="id")
     这样才能映射为有数据的List
    */
    int id;

    public MyInteger setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

}
