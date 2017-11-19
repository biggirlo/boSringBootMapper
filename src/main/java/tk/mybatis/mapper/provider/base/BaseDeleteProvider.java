/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package tk.mybatis.mapper.provider.base;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

/**
 * BaseDeleteMapper实现类，基础方法实现类
 *
 * @author liuzh
 */
public class BaseDeleteProvider extends MapperTemplate {

    public BaseDeleteProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 通过条件删除
     *
     * @param ms
     * @return
     */
    public String delete(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.whereAllIfColumns(entityClass, isNotEmpty()));
        return sql.toString();
    }

    /**
     * 通过主键删除
     * @param ms
     * @return
     */
    public String deleteByPrimaryKeyList(MappedStatement ms){
        final Class<?> entityClass = this.getEntityClass(ms);
        //获取表的各项属性
        EntityTable table = EntityHelper.getEntityTable(entityClass);

        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ");
        sql.append(table.getName());
        sql.append("<where>");
        sql.append(" <foreach collection=\"array\" item=\"record\" separator=\"or\" >");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        for (EntityColumn column : columnList) {
            if ( column.isId()) {
                sql.append(" " + column.getColumn() + "=#{record} ");
                break;
            }
        }
        sql.append("</foreach>");
        sql.append("</where>");
        return sql.toString();
    }

    /**
     * 根据批量条件删除，用or链接
     * @param ms
     * @return
     */
    public String deleteList(MappedStatement ms){
        final Class<?> entityClass = this.getEntityClass(ms);
        //获取表的各项属性
        EntityTable table = EntityHelper.getEntityTable(entityClass);

        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ");
        sql.append(table.getName());
        sql.append("<where>");
        sql.append(" <foreach collection=\"list\" item=\"record\" separator=\" or \" >");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            sql.append(SqlHelper.getIfNotNull("record" ,column, column.getColumn() + "=" + column.getColumnHolder("record"),isNotEmpty()));
        }
        sql.append("</foreach>");
        sql.append("</where>");
        System.out.print(sql.toString());
        return sql.toString();
    }

    /**
     * 通过主键删除
     *
     * @param ms
     */
    public String deleteByPrimaryKey(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        return sql.toString();
    }
}
