package com.example;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <h1>使用 screw 生成数据库表文档</h1>
 * @author Hedon Wang
 * @create 2022-05-24 10:05 PM
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DBDocTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void buildDBDoc() {
        // 获取数据源
        DataSource dataSourceMySQL = applicationContext.getBean(DataSource.class);
        // 生成文件配置
        EngineConfig engineConfig = EngineConfig.builder()
                .fileOutputDir("/Users/hedon-/IdeaProjects/e-commerce-springcloud")
                .openOutputDir(false)
                .fileType(EngineFileType.MD)
                .produceType(EngineTemplateType.freemarker)
                .build();

        // 生成文档配置，包含自定义版本号、描述等等
        // 名称：数据库名_description_version.md
        Configuration configuration = Configuration.builder()
                .version("1.0.0")
                .description("e-commerce-springcloud")
                .dataSource(dataSourceMySQL)
                .engineConfig(engineConfig)
                .produceConfig(getProduceConfig())
                .build();

        new DocumentationExecute(configuration).execute();
    }


    /**
     * <h2>配置想要生成和想要忽略的数据表</h2>
     */
    private ProcessConfig getProduceConfig() {

        List<String> ignoreTableName = Collections.singletonList("undo_log");

        List<String> ignorePrefix = Arrays.asList("a", "b");

        List<String> ignoreSuffix = Arrays.asList("_test", "_Test");

        return ProcessConfig.builder()
                .designatedTableName(Collections.emptyList())       // 根据名称指定表生成
                .designatedTablePrefix(Collections.emptyList())     // 根据前缀指定表生成
                .designatedTableSuffix(Collections.emptyList())     // 根据后缀指定表生成
                .ignoreTableName(ignoreTableName)
                .ignoreTablePrefix(ignorePrefix)
                .ignoreTableSuffix(ignoreSuffix)
                .build();
    }
}
