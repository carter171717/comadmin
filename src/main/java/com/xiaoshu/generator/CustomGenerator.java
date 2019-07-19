package com.xiaoshu.generator;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CustomGenerator {
	public static void main(String[] args) throws InterruptedException {
		AutoGenerator mpg = new AutoGenerator();

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setOutputDir("D://tmp/mysql/");
		gc.setFileOverride(true);
		gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
		gc.setEnableCache(false);// XML 二级缓存
		gc.setBaseResultMap(true);// XML ResultMap
		gc.setBaseColumnList(true);// XML columList
		gc.setAuthor("wangly");
		gc.setIdType(IdType.UUID);
		// 自定义文件命名，注意 %s 会自动填充表实体属性！
		gc.setMapperName("%sMapper");
		gc.setXmlName("%sMapper");
		gc.setServiceName("%sService");
		gc.setServiceImplName("%sServiceImpl");
		gc.setControllerName("%sController");

		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();

		dsc.setDbType(DbType.MYSQL);
		dsc.setTypeConvert(new MySqlTypeConvert());

		// oralce 的数据库驱动配置
		/*dsc.setDriverName("oracle.jdbc.driver.OracleDriver");
		dsc.setUrl("jdbc:oracle:thin:@10.102.0.240:1521:orcl");
		dsc.setUsername("vaccination");//guahao_prod,guahao
		dsc.setPassword("vaccination");*/

        // mysql的数据库驱动
		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername("root");
		dsc.setPassword("123456");
		dsc.setUrl("jdbc:mysql://localhost:3306/comadmin?useUnicode=true&characterEncoding=utf8");

		mpg.setDataSource(dsc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		//strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
		strategy.setTablePrefix(new String[] { "im_", "ext_", "api_", "sys_", "pay_", "bs_" });// 此处可以修改为您的表前缀
		strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
		//strategy.setInclude(new String[] { "sys_dept", "sys_dict", "sys_login_log", "sys_menu", "sys_notice", "sys_operation_log", "sys_relation", "sys_role", "sys_user" }); // 需要生成的表
		//strategy.setExclude(new String[] { "DATABASECHANGELOG", "DATABASECHANGELOGLOCK" }); // 排除生成的表
		// 自定义实体父类
		// strategy.setSuperEntityClass("com.ylzinfo.common.base.BaseEntity");
		// 自定义实体，公共字段
		// strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
		// 自定义 mapper 父类
		// strategy.setSuperMapperClass("com.ylzinfo.common.base.BaseMapper");
		// 自定义 service 父类
		// strategy.setSuperServiceClass("com.ylzinfo.common.base.BaseService");
		// 自定义 service 实现类父类
		// strategy.setSuperServiceImplClass("com.ylzinfo.common.base.BaseServiceImpl");
		// 自定义 controller 父类
		// strategy.setSuperControllerClass("com.ylzinfo.common.base.BaseController");
		// 【实体】是否生成字段常量（默认 false）
		// public static final String ID = "test_id";
		// strategy.setEntityColumnConstant(true);
		// 【实体】是否为构建者模型（默认 false）
		// public User setName(String name) {this.name = name; return this;}
		// strategy.setEntityBuilderModel(true);
		mpg.setStrategy(strategy);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setParent("com.xiaoshu.admin");
		pc.setModuleName("");
		mpg.setPackageInfo(pc);

		// 执行生成
		mpg.execute();
	}

}
