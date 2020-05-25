package in.codeblog.ppmapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.codeblog.ppmapi.domain.Backlog;
import in.codeblog.ppmapi.domain.ProjectTask;
import in.codeblog.ppmapi.repository.BacklogRepository;
import in.codeblog.ppmapi.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	@Autowired
	private BacklogRepository backlogRepository;
	@Autowired
	private ProjectTaskRepository projectTaskRepository; 
	
	public ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask) {
		
		
		//Exceptions: project not fond
		//projectTasks to be added to specific project,projet!=null,Backlog Exist
		Backlog backlog= backlogRepository.findByProjectIdentifier(projectIdentifier);
		//set the Backlog to project task
		projectTask.setBacklog(backlog);
		//we want our project sequence be like this IDPRO-1 IDPRO-2
		Integer backLogSequence=backlog.getPTSequence();
		//update bacllog sequence
		backLogSequence++;
		backlog.setPTSequence(backLogSequence);
        //add baclklogsequence to ProjectTask
		projectTask.setProjectSequence(projectIdentifier+"-"+backLogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);
		//initial priority when priorioty is null
		if(projectTask.getPriority()==null) {
			projectTask.setPriority(3);
			
		}
		//initial status when status is null
		if(projectTask.getStatus()=="" || projectTask.getStatus()==null);{
		 projectTask.setStatus("TO_DO");
        
		}
		return projectTaskRepository.save(projectTask);
	}
    
}
