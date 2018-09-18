package com.example.demo.config;

import com.example.demo.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
// ------------------------------ FIELDS ------------------------------

    private JobRepository jobRepository;
    private ApplicationContext ctx;

// --------------------------- CONSTRUCTORS ---------------------------

    public SpringBatchConfig(JobRepository jobRepository, ApplicationContext ctx) {
        this.jobRepository = jobRepository;
        this.ctx = ctx;
    }

// -------------------------- OTHER METHODS --------------------------

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemProcessor<User, User> itemProcessor,
                   ItemWriter<User> itemWriter) {
        Step step = stepBuilderFactory.get("stepETL")
                .<User, User>chunk(100)
                .reader(reader(null))
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return jobBuilderFactory.get("jobETL")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public JobLauncher jobLauncherAsync() {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        try {
            jobLauncher.afterPropertiesSet();
        } catch (Exception ex) {
            // do something
        }
        return jobLauncher;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<User> reader(
            @Value("#{jobParameters[fileName]}") String pathToFile) {
        FlatFileItemReader<User> itemReader = new FlatFileItemReader<>();
        itemReader.setName("CSV-Reader");
        itemReader.setLinesToSkip(1);
        itemReader.setResource(ctx.getResource(pathToFile));
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    public LineMapper<User> lineMapper() {
        DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "name", "dept", "salary");

        BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(User.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
