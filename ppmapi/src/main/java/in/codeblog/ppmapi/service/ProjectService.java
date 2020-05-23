package in.codeblog.ppmapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.codeblog.ppmapi.domain.Project;
import in.codeblog.ppmapi.exception.ProjectIdException;
import in.codeblog.ppmapi.repository.ProjectRepository;

@Service
public class ProjectService {
	
@Autowired	
 private ProjectRepository projectRepository;
public Project saveoOrUpdate(Project project) {
	try {
		project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
		return projectRepository.save(project);
		
	} catch (Exception e) {
		// TODO: handle exception
		throw new ProjectIdException("project Id"+ project.getProjectIdentifier().toUpperCase()+" already exist");
	}
}

public Project findByProjectIdentifier(String projectId) {
	Project project=projectRepository.findByProjectIdentifier(projectId.toUpperCase());
	if(project==null) {
		throw new ProjectIdException("project id"+ projectId.toUpperCase() + "does not exist");
}
return project;
}

public Iterable<Project> findAllProjects(){
	return projectRepository.findAll();
}

public void deleteProjectByIdentifier(String projectId) {
	Project project=projectRepository.findByProjectIdentifier(projectId.toUpperCase());
	
	if(project==null) {
		throw new ProjectIdException("can not delete project with id "+projectId.toUpperCase()+" this project does not exist");
		
	}
    projectRepository.delete(project);
}


}
