package in.codeblog.ppmapi.service;

import java.util.List;

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
			if(projectTask.getPriority()==0 || projectTask.getPriority()==null) {
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
	//make sure we are searching on an existing backlog
    Backlog backlog=backlogRepository.findByProjectIdentifier(backlog_id);
    if(backlog==null) {
    	throw new ProjectNotFoundException("project with id:"+backlog_id+"does not exist");
    }
	//make sure that project task exist
    ProjectTask projectTask=projectTaskRepository.findByProjectSequence(pt_id);
    if(projectTask==null) {
    	throw new ProjectNotFoundException("project task with :"+pt_id+"does not exist");
    }
    //make sure that the backlogid / project_id in the path corresponds to the right project.
    if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
    	throw new ProjectNotFoundException("project task:"+pt_id+"does not exist in project"+backlog_id+"");
    }
	
return projectTask;
}


public  ProjectTask updateByProjectSequence(ProjectTask updateTask,String backlog_id,String pt_id) {
	//find existing project task
	ProjectTask projectTask=findPTByProjectSequence(backlog_id, pt_id);
	//replace it with update task
	projectTask=updateTask;
	//Save projectTassk
	return projectTaskRepository.save(projectTask);
}

public void deletePTByProjectSequence(String backlog_id,String pt_id) {
	ProjectTask projectTask=findPTByProjectSequence(backlog_id,pt_id);
	Backlog backlog=projectTask.getBacklog();
	List<ProjectTask> pts=backlog.getProjectTasks();
	pts.remove(projectTask);
	backlogRepository.save(backlog);
	projectTaskRepository.delete(projectTask);
}
}
