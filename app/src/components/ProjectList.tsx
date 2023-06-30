import React from "react";
import { Project } from "../model";
import Button from "./Button";
import { useNavigate, useParams } from "react-router-dom";


interface ProjectListProps {
  projects: Project[];
}

const ProjectList: React.FC<ProjectListProps> = ({ projects }) => {
  const nav = useNavigate();
  const { id } = useParams<{id: string}>();
  
  if (projects.length > 0)
    return (
      <ul className="space-y-4">
        {projects.map((project) => (
          <li key={project.id} className="bg-white rounded-lg shadow-md p-4 grid grid-cols-3">
            <h4 className="text-xl font-semibold">{project.title}</h4>
            <p>{project.description}</p>
            <Button onClick={() => nav(`/project/${id}/${project.id}`)}>Edit</Button>
          </li>
        ))}
      </ul>
    );
  else return <div>
    <p>No projects</p>
    <Button onClick={() => nav(`/project/${id}`)}>Add Project</Button>
  </div>
};

export default ProjectList;
