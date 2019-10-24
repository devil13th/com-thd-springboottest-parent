<#assign get="#{" />
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

    <#if enableCache>
        <!-- 开启二级缓存 -->
        <cache type="org.mybatis.caches.ehcache.LoggingEhcache"/>

    </#if>
    <#if baseResultMap>
        <!-- 通用查询映射结果 -->
        <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
    <#list table.fields as field>
    <#if field.keyFlag><#--生成主键排在第一位-->
            <id column="${field.name}" property="${field.propertyName}" />
    </#if>
    </#list>
    <#list table.commonFields as field><#--生成公共字段 -->
        <result column="${field.name}" property="${field.propertyName}" />
    </#list>
    <#list table.fields as field>
    <#if !field.keyFlag><#--生成普通字段 -->
            <result column="${field.name}" property="${field.propertyName}" />
    </#if>
    </#list>
        </resultMap>

    </#if>

    <sql id="BASE_TABLE">
        ${table.name}
    </sql>

    <#if baseColumnList>
        <!-- 通用查询结果列   -->
        <sql id="Column_List">
    <#list table.commonFields as field>
            ${field.name},
    </#list>
            ${table.fieldNames}
        </sql>
    </#if>

	<!-- 查询条件 -->
    <sql id="Where_Clause">
    <where>
    <trim prefixOverrides="and" >
        <#list table.fields as field>
            <#if field.type == "date" ||  field.type == "datetime">
                <if test="${field.propertyName} != null ">
                 and ${field.name} = ${get}${field.propertyName}} 
                </if>
            <#else>
                <if test="${field.propertyName} != null and ${field.propertyName} != ''">
                 and ${field.name} = ${get}${field.propertyName}} 
                </if>
            </#if>
        </#list>
    </trim>
    </where>
    </sql>


	<!-- like查询条件 -->
    <sql id="Like_Where_Clause">
    <where>
    <trim prefixOverrides="and" >
        <#list table.fields as field> 
            <#if field.type == "date" ||  field.type == "datetime">
                <if test="${field.propertyName} != null ">
                and ${field.name} = ${get}${field.propertyName}} 
                </if>
            <#else>
                <if test="${field.propertyName} != null and ${field.propertyName} != ''">
                and ${field.name} like CONCAT('%',${get}${field.propertyName}},'%') 
                </if>
            </#if>
        </#list>
    </trim>
    </where>
    </sql>


    <!-- 插入记录 -->
    <insert id="save" parameterType="Object">
        insert into <include refid="BASE_TABLE" />
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list table.fields as field>${field.name},</#list>
        </trim>
        values
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list table.fields as field>${get}${field.propertyName}},</#list>
        </trim>
    </insert>

    <!-- 编辑记录 -->
    <update id="update" parameterType="Object">
        update  <include refid="BASE_TABLE" /> set 
        <trim  suffixOverrides="," >
            <#list table.fields as field>
                <if test="${field.propertyName} != null ">
                    ${field.name} = ${get}${field.propertyName}},
                </if>
            </#list>
        </trim>
        <where>
        <trim  prefixOverrides="and" >
        <#list table.fields as field>
	    <#if field.keyFlag><!--where主键条件-->
	    	<!-- ${get}${field.propertyName}}对应 ${table.mapperName}.delete(String id)中的${field.propertyName} -->
	        and ${field.name}=${get}${field.propertyName}} 
	    </#if>
	    </#list>
	    </trim>
	    </where>
    </update>

    <!-- 删除记录 -->
    <delete id="deleteById">
        delete from  <include refid="BASE_TABLE" />         
        <where>
        <trim prefixOverrides="and" >
        <#list table.fields as field>
	    <#if field.keyFlag><!--where主键条件-->
	        <!-- ${get}${field.propertyName}}对应 ${table.mapperName}.delete(String id)中的${field.propertyName} -->
	        and ${field.name}=${get}${field.propertyName}} 
	    </#if>
	    </#list>
	    </trim>
	    </where>
    </delete>

    <!-- 根据id查询对象 -->
    <select id="queryById" resultMap="BaseResultMap">
        select
        <include refid="Column_List" />
        from <include refid="BASE_TABLE" />
        <where>
        <trim prefixOverrides="and"  >
        <#list table.fields as field>
	    <#if field.keyFlag><!--where主键条件-->
	    	<!-- ${get}${field.propertyName}}对应 ${table.mapperName}.delete(String id)中的${field.propertyName} -->
	        and ${field.name}=${get}${field.propertyName}} 
	    </#if>
	    </#list>
	    </trim>
	    </where>
    </select>


    <!-- 查询列表 -->
    <select id="queryList" resultMap="BaseResultMap" parameterType="Object">
        select 
        <include refid="Column_List" />
        from <include refid="BASE_TABLE" /> 
        <include refid="Where_Clause" />
        
    </select>
    
    <!-- 查询列表 -->
    <select id="queryListByLike" resultMap="BaseResultMap"
        parameterType="Object">
        select 
        <include refid="Column_List" />
        from <include refid="BASE_TABLE" /> 
        <include refid="Like_Where_Clause" />
    </select>
</mapper>
