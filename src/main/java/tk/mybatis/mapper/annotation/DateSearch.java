/**
 * **********************************************************
 * 该项目仅用于学习
 * 有任何疑问或者建议请致邮件于 email:645614025@qq.com
 * **********************************************************
 * **********************************************************
 */
package tk.mybatis.mapper.annotation;

import tk.mybatis.mapper.code.DateSearchType;

import java.lang.annotation.*;

/**
 * 时间搜索注解类
 * @author 王雁欣
 * create on 2017/11/18 23:08 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateSearch {
    //查询字段名称
    String name() default "";

    //查询类型
    DateSearchType type() default DateSearchType.equal;
}
