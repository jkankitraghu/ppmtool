package in.codeblog.ppmapi.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class Project {
	@Id
	@GeneratedValue
   private Long id;
   @NotBlank(message="projectName is required")
   private String projectName;
   @NotBlank(message="projectIdentifier is required") 
   @Size(min=4, max=5,message="please use 4 to 5 character")
   @Column(updatable = false,unique=true)
   private String projectIdentifier;
   @NotBlank(message="description is required")
   private String description;
   @JsonFormat(pattern="yyyy-MM-dd")
   private Date start_date;
   @JsonFormat(pattern="yyyy-MM-dd")
   private Date end_date;   
   @JsonFormat(pattern="yyyy-MM-dd")
   private Date created_At;
   @JsonFormat(pattern="yyyy-MM-dd")
   private Date updated_At;
   
   //one to one with backlog
   @OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL,mappedBy="project")
   @JsonIgnore
   private Backlog backlog; 
   
 public Backlog getBacklog() {
	return backlog;
}
public void setBacklog(Backlog backlog) {
	this.backlog = backlog;
}
public Project() {
	super();
   }
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getProjectName() {
	return projectName;
}
public void setProjectName(String projectName) {
	this.projectName = projectName;
}
public String getProjectIdentifier() {
	return projectIdentifier;
}
public void setProjectIdentifier(String projectIdentifier) {
	this.projectIdentifier = projectIdentifier;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public Date getStart_date() {
	return start_date;
}
public void setStart_date(Date start_date) {
	this.start_date = start_date;
}
public Date getEnd_date() {
	return end_date;
}
public void setEnd_date(Date end_date) {
	this.end_date = end_date;
}

@PrePersist
protected void onCreate() {
	this.created_At=new Date();
}
@PreUpdate
protected void onUpdate() {
	this.updated_At=new Date();
}
}



