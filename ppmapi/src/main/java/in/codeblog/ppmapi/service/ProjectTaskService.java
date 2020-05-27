package in.codeblog.ppmapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.codeblog.ppmapi.domain.Backlog;
import in.codeblog.ppmapi.domain.Project;
import in.codeblog.ppmapi.domain.ProjectTask;
import in.codeblog.ppmapi.exception.ProjectNotFoundException;
import in.codeblog.ppmapi.repository.BacklogRepository;
import in.codeblog.ppmapi.repository.ProjectRepository;
import in.codeblog.ppmapi.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {
	@Autowired
	private BacklogRepository backlogRepository;
	@Autowired
	private ProjectTaskRepository projectTaskRepository; 
	@Autowired
	private ProjectRepository projectRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask) {
		
		
		try {
			
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
			
		} catch (Exception e) {
			throw new ProjectNotFoundException("project not found");
		}
		
		
	}
    
	
	

public Iterable<ProjectTask> findBacklogById(String id){
	Project project=projectRepository.findByProjectIdentifier(id);
	if(project==null) {
		throw new ProjectNotFoundException("projetc with "+id+"does not exist");
	}
	
	return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
			
}

public ProjectTask findPTByProjectSequence(String backlog_id,String pt_id) {
	//make sure we are searching on the 
return  projectTaskRepository.findByProjectSequence(pt_id);
}
}
