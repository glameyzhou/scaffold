package ${classModel.basePackage!''}.dao;

import ${classModel.basePackage!''}.model.${classModel.uname!''}Model;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
  * ${classModel.desc!''} DAO数据接口<p>
  *
  * @author zhouyang.zhou
  */
@Repository
public interface ${classModel.uname!''}Dao {

    /**
    * 保存${classModel.desc!''}数据
    *
    * @param model  ${classModel.desc!''}
    * @return 影响行数
    */
    int save(${classModel.uname!''}Model model);

    /**
    * 批量保存${classModel.desc!''}数据
    *
    * @param list ${classModel.desc!''}
    */
    void saveBatch(List<${classModel.uname!''}Model> list);

    /**
    * 删除${classModel.desc!''}数据
    *
    * @param id 指定ID
    * @return 影响行数
    */
    int delete(int id);

    /**
    * 批量删除${classModel.desc!''}数据
    *
    * @param list 指定的ID集合
    * @return     影响的总行数
    */
    int deleteByIds(List<Integer> list);

    /**
    * 更新${classModel.desc!''}数据
    *
    * @param model  ${classModel.desc!''}
    * @return       影响行数
    */
    int update(${classModel.uname!''}Model model);

    /**
    * 根据id获取${classModel.desc!''}数据
    *
    * @param id 指定ID
    * @return   ${classModel.desc!''}
    */
    ${classModel.uname!''}Model getById(int id);

    /**
    * 分页获取${classModel.desc!''}数据列表
    *
    * @param parameters         查询参数
    * @param offset      数据偏移量
    * @param rowsPerPage 查询的总条数
    * @return    符合条件的数据列表
    */
    List${r'<'}${classModel.uname!''}Model${r'>'} getModelListByPaginate(@Param("parameters") Map<String, Object> parameters, @Param("offset") Integer offset, @Param("rowsPerPage") Integer rowsPerPage);

    /**
    * 获取${classModel.desc!''}数据总量
    *
    * @param parameters       查询参数
    * @return          符合条件的总行数
    */
    Integer getModelCountByPaginate(Map<String, Object> parameters);

}


