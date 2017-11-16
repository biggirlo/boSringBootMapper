package tk.mybatis.mapper.common.base.delete;

import org.apache.ibatis.annotations.DeleteProvider;
import tk.mybatis.mapper.provider.base.BaseDeleteProvider;

import java.util.List;

/**
 * **********************************************************
 * 该项目仅用于学习
 * 有任何疑问或者建议请致邮件于 email:645614025@qq.com
 * **********************************************************
 * **********************************************************
 *
 * @author 王雁欣
 * create on 2017/11/17 0:16
 */
public interface DeleteByPrimaryKeyListMapper<T> {
    @DeleteProvider(type = BaseDeleteProvider.class, method = "dynamicSQL")
    int deleteByPrimaryKeyList(Object[] keys);
}
