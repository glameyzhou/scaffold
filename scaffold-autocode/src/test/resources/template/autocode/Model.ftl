package ${classModel.basePackage!''}.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
  * ${classModel.desc!''}<p>
  *
  * @author zhouyang.zhou
  */
public class ${classModel.uname!''}Model implements java.io.Serializable {
<#list propertyModelList as model>
  /**
  * ${model.desc!''}
  */
    private ${model.type!''} ${model.lname!''};

</#list>

<#list propertyModelList as model>
    public ${model.type!''} get${model.uname!''}() {
        return ${model.lname!''};
    }

    public void set${model.uname!''}(${model.type!''} ${model.lname!''}) {
        this.${model.lname!''} = ${model.lname!''};
    }

</#list>

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
