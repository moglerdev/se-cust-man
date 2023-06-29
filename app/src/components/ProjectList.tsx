import React from "react";

interface Project {
  id: number;
  name: string;
  description: string;
}

interface ProjectListProps {
  projects: Project[];
}

const ProjectList: React.FC<ProjectListProps> = ({ projects }) => {
  if (projects.length > 0)
    return (
      <ul className="space-y-4">
        {projects.map((project) => (
          <li key={project.id} className="bg-white rounded-lg shadow-md p-4">
            <h4 className="text-xl font-semibold">{project.name}</h4>
            <p>{project.description}</p>
          </li>
        ))}
      </ul>
    );
  else return <p>No projects found.</p>;
};

export default ProjectList;
