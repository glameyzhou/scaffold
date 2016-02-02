package ${classModel.basePackage!''}.service;

import ${classModel.basePackage!''}.dao.${classModel.uname!''}Dao;
import ${classModel.basePackage!''}.model.${classModel.uname!''}Model;
import ${classModel.basePackage!''}.service.${classModel.uname!''}Service;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * ${classModel.desc!''} 操作<p>
 *
 * @author zhouyang.zhou
 *
 */
@Service
public class ${classModel.uname!''}Service{

    private Logger logger = LoggerFactory.getLogger(${classModel.uname!''}Service.class);

    @Resource
    private ${classModel.uname!''}Dao ${classModel.lname!''}Dao;

    public int save(${classModel.uname!''}Model model) {
        if(model == null){
            logger.info("保存${classModel.desc!''}失败，id = {}", -1);
            return -1;
        }
        ${classModel.lname!''}Dao.save(model);
        logger.info("保存${classModel.desc!''}成功，id={}", model.getId());
        return model.getId();
    }

    public void saveBatch(List${r'<'}${classModel.uname!''}Model${r'>'} list) {
       if(CollectionUtils.isEmpty(list)){
            logger.info("批量保存${classModel.desc!''}失败，size={}", 0);
            return;
        }
        ${classModel.lname!''}Dao.saveBatch(list);
        logger.info("批量保存${classModel.desc!''}成功，size={}", list.size());
    }

    public boolean delete(int id) {
        if(id <= 0){
            logger.info("删除${classModel.desc!''}失败，id={},count={}", 0, 0);
            return false;
        }
        int count = ${classModel.lname!''}Dao.delete(id);
        logger.info("删除${classModel.desc!''}成功，id={}, count={}", id, count);
        return count > 0;
    }

    public boolean deleteByIds(List${r'<Integer>'} list){
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(list), "参数不合法");
        if(CollectionUtils.isEmpty(list)){
            logger.info("删除${classModel.desc!''}失败,count={}", 0);
            return false;
        }
        int count = ${classModel.lname!''}Dao.deleteByIds(list);
        logger.info("删除${classModel.desc!''}成功,count={}", count);
        return count == list.size();
    }

    public boolean update(${classModel.uname!''}Model model) {
        if(model == null || model.getId() <= 0){
            logger.info("更新${classModel.desc!''}失败，参数不合法");
            return false;
        }
        int count = ${classModel.lname!''}Dao.update(model);
        logger.info("更新${classModel.desc!''}成功，id={},count={}", model.getId(), count);
        return count > 0;
    }

    public ${classModel.uname!''}Model getById(int id){
        Preconditions.checkArgument(id > 0, "参数不合法");
        if(id <= 0){
            logger.info("根据id获取${classModel.desc!''}失败，id={}", id);
            return null;
        }
        ${classModel.uname!''}Model model = ${classModel.lname!''}Dao.getById(id);
        logger.info("根据id获取${classModel.desc!''}成功，id={}", id);
        return model;
    }

    public List${r'<'}${classModel.uname!''}Model${r'>'}  getModelListByPaginate(Map<String, Object> parameters, Integer offset, Integer rowsPerPage) {
        Preconditions.checkArgument(offset >= 0 && rowsPerPage > 0,"参数不合法");
        List${r'<'}${classModel.uname!''}Model${r'>'} modelList = ${classModel.lname!''}Dao.getModelListByPaginate(parameters, offset, rowsPerPage);
        logger.info("分页获取${classModel.desc!''}数据列表，parameters={},offset={},rowsPerPage={}", parameters, offset, rowsPerPage);
        return modelList;
    }

    public Integer getModelCountByPaginate(Map<String, Object> parameters) {
        Integer count = ${classModel.lname!''}Dao.getModelCountByPaginate(parameters);
        logger.info("分页获取${classModel.desc!''}数据总量，parameters={},count={}", parameters, count);
        return count == null ? 0 : count;
    }

}