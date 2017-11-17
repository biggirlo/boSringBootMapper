package tk.mybatis.mapper.common.base.delete;

import org.apache.ibatis.annotations.DeleteProvider;
import tk.mybatis.mapper.provider.base.BaseDeleteProvider;

import java.util.List;

/**
 * 根据多个条件or 批量删除
 */
public interface DeleteListMapper<T> {
    @DeleteProvider(type = BaseDeleteProvider.class, method = "dynamicSQL")
    int deleteList(List<T> keys);
}
