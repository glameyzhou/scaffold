package ${classModel.basePackage!''}.service;

import ${classModel.basePackage!''}.model.${classModel.uname!''}Model;

import java.util.List;
import java.util.Map;

/**
 * ${classModel.desc!''} 业务接口<p>
 *
 * @author zhouyang.zhou
 *
 */
public interface ${classModel.uname!''}Service {

    /**
    * 保存${classModel.desc!''}数据
    *
    * @param model  ${classModel.desc!''}
    * @return 主键ID
    */
    int save${classModel.uname!''}Model(${classModel.uname!''}Model model);

    /**
    * 批量保存${classModel.desc!''}数据
    *
    * @param list  ${classModel.desc!''}
    */
    void save${classModel.uname!''}ModelBatch(List${r'<'}${classModel.uname!''}Model${r'>'} list);

    /**
    * 删除${classModel.desc!''}数据
    *
    * @param id 指定ID
    * @return 是否删除成功
    */
    boolean delete${classModel.uname!''}Model(int id);

    /**
    * 批量删除${classModel.desc!''}数据
    *
    * @param list     指定的ID集合
    * @return boolean 是否删除成功
    */
    boolean delete${classModel.uname!''}ModelByIds(List${r'<Integer>'} list);

    /**
    * 更新${classModel.desc!''}数据
    *
    * @param model  ${classModel.desc!''}
    * @return 是否更新成功
    */
    boolean update${classModel.uname!''}Model(${classModel.uname!''}Model model);

    /**
    * 根据id获取${classModel.desc!''}数据
    *
    * @param id
    * @return ${classModel.uname!''}Model
    */
    ${classModel.uname!''}Model  get${classModel.uname!''}ModelById(int id);

    /**
    * 分页获取${classModel.desc!''}数据
    *
    * @param parameters      查询参数
    * @param offset   偏移量
    * @param rowsPerPage 每次查询的条数
    * @return {@link java.util.List<${classModel.uname!''}Model>}
    */
    List${r'<'}${classModel.uname!''}Model${r'>'}  get${classModel.uname!''}ModelsForPaginate(Map<String, Object> parameters, Integer offset, Integer rowsPerPage);
   /**
    * 分页获取项目方用户表数据总量
    *
    * @param parameters      查询参数
    * @return {@link List<${classModel.uname!''}Model>}
    */
    Integer get${classModel.uname!''}ModelCountForPaginate(Map<String, Object> parameters);

}