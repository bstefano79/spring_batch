package com.xantrix.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SalutiJob 
{
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job createSalutiJob() 
	{
		/*
		 
		  Date date = new Date();  
        Timestamp ts=new Timestamp(date.getTime());  
		return this.jobBuilderFactory.get("SalutiJob"+ts)
				.start(salutiStep())
				.build();
		 
		 */
		return this.jobBuilderFactory.get("SalutiJob").incrementer(new RunIdIncrementer())
				.start(salutiStep()).next(altroStep())
				.build();
	}
	
	//STEP 1
	@Bean
	public Step salutiStep() 
	{
		return this.stepBuilderFactory.get("SalutiStep").tasklet(new Tasklet() 
		{
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
					throws Exception 
			{
				System.out.println("Saluti, sono il tuo primo Step del Job SalutiJob "+chunkContext.getStepContext().getJobParameters().get("articolo").toString());
				return RepeatStatus.FINISHED;
			}
		}).build(); 
	}
	
	@Bean
	public Step altroStep() 
	{
		return this.stepBuilderFactory.get("altroStep").tasklet(new Tasklet() 
		{
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
					throws Exception 
			{
				System.out.println("ciao sono il secondo step");
				return RepeatStatus.FINISHED;
			}
		}).build(); 
	}
	
}
