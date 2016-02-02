${r'<?xml version="1.0" encoding="UTF-8"?>'}
${r'<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">'}
${r'<mapper namespace="'}${classModel.basePackage!''}.dao.${classModel.uname!''}Dao"${r'>'}
    ${r'<insert id="save'}${r'" parameterType="'}${classModel.uname!''}${r'Model" useGeneratedKeys="true" keyProperty="id">'}
        INSERT INTO ${classModel.schema!''}.${classModel.tableName!''}
        (
        <#list propertyModelList as model>
            ${model.columnName!''}<#if model_has_next>,</#if>
        </#list>
        )VALUES(
        <#list propertyModelList as model>
            ${r'#{'}${model.lname!''}${r'}'}<#if model_has_next>,</#if>
        </#list>
        )
    ${r'</insert>'}

    ${r'<insert id="save'}${r'Batch" parameterType="java.util.List">'}
        INSERT INTO ${classModel.schema!''}.${classModel.tableName!''}
        (
        <#list propertyModelList as model>
            ${model.columnName!''}<#if model_has_next>,</#if>
        </#list>
        )VALUES
        ${r'<foreach collection="list" item="item"  separator="," >'}
        (<#list propertyModelList as model>
            ${r'#{item.'}${model.lname!''}${r'}'}<#if model_has_next>,</#if>
        </#list> )
        ${r'</foreach>'}
    ${r'</insert>'}

    ${r'<delete id="delete'}${r'" parameterType="Integer">'}
        DELETE FROM ${classModel.schema!''}.${classModel.tableName!''} ${r'WHERE id = #{id}'}
    ${r'</delete>'}

    ${r'<delete id="delete'}${r'ByIds"'}${r' parameterType="java.util.List">'}
        DELETE FROM ${classModel.schema!''}.${classModel.tableName!''} ${r'WHERE id IN('}
        ${r'<foreach collection="list" item="item"  separator="," >'}
            ${r'#{item}'}
        ${r'</foreach>)'}
    ${r'</delete>'}

    ${r'<update id="update'}${r'" parameterType="'}${classModel.uname!''}${r'Model">'}
        UPDATE
            ${classModel.schema!''}.${classModel.tableName!''}
        SET
        <#list propertyModelList as model>
            ${model.columnName!''} = ${r'#{'}${model.lname!''}${r'}'}<#if model_has_next>,</#if>
        </#list>
        ${r'WHERE id = #{id}'}
    ${r'</update>'}

    ${r'<select id="get'}${r'ById" parameterType="Integer" resultType="'}${classModel.uname!''}${r'Model">'}
        SELECT
        <#list propertyModelList as model>
            ${model.columnName!''} as ${model.lname!''} <#if model_has_next>,</#if>
        </#list>
        FROM
            ${classModel.schema!''}.${classModel.tableName!''}
        ${r'WHERE id = #{id}'}
    ${r'</select>'}

    ${r'<select id="get'}${r'ModelListByPaginate" resultType="'}${classModel.uname!''}${r'Model">'}
        SELECT
            <#list propertyModelList as model>
            ${model.columnName!''} as ${model.lname!''} <#if model_has_next>,</#if>
            </#list>
        FROM
            ${classModel.schema!''}.${classModel.tableName!''}
        ORDER BY id
        LIMIT ${r'#{offset},#{rowsPerPage}'}
    ${r'</select>'}

    ${r'<select id="get'}${r'ModelCountByPaginate" parameterType="java.util.Map" resultType="java.lang.Integer">'}
        SELECT
        count(*)
        FROM
        ${classModel.schema!''}.${classModel.tableName!''}
    ${r'</select>'}
${r'</mapper>'}