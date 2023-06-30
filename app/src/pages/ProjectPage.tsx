import React from "react";
import Card from "../components/Card";

import { Project } from "../model";
import { Navigate, useNavigate, useParams } from "react-router-dom";
import { useCustomerStore } from "../useCustomerStore";
import { ProjectEditView } from "../components/ProjectView";
import Button from "../components/Button";

const ProjectPage: React.FC = () => {

  // load Project from useProject hook
  // get id from react router
  const {customer_id, project_id} = useParams<{customer_id: string, project_id: string}>();
  console.log("ProjectPage", customer_id, project_id);
  const c_id = Number.parseInt(customer_id!);
  const p_id = Number.parseInt(project_id!);
  
  const nav = useNavigate();

  const customer = useCustomerStore((state) => state.customers.find((c) => c.id === c_id));
  const project = customer?.project.find((p) => p.id === p_id);

  const handleSave = (Project: Project) => {
    let x = customer?.project.filter((p) => p.id !== Project.id)
    if (x) {
      x.push(Project)
      useCustomerStore.getState().setCustomer({...customer!, project: x});
    }
  };

  if (!project) {
    return <Navigate to={`/customer/${c_id}`} />;
  }

  return (
    <Card>
      <h1>Project {project.title}</h1>
      <ProjectEditView onSave={handleSave} project={project} />
      <hr className="my-10"/>
      <h2>Tasks</h2>
      <ul className="space-y-4">
        {project.tasks.map((task) => (
          <li key={task.id} className="bg-white rounded-lg shadow-md p-4 grid grid-cols-3">
            <h4 className="text-xl font-semibold">{task.title}</h4>
            <p>{task.description}</p>
            <Button onClick={() => nav(`/project/${c_id}/${p_id}/${task.id}`)}>Edit</Button>
          </li>
        ))}
      </ul>
      <Button onClick={() => nav(`/project/${c_id}/${p_id}/new`)}>Add Task</Button>
    </Card>
  );
};

export const ProjectNewPage: React.FC = () => {
  // new Project
  const project: Project = {
    id: -1,
    title: "",
    description: "",
    priority: 0,
    deadline: "",
    tasks: []
  };

  const {customer_id,} = useParams<{customer_id: string}>();
  const c_id = Number.parseInt(customer_id!);
  const customer = useCustomerStore((state) => state.customers.find((c) => c.id === c_id));

  // react router hook
  const nav = useNavigate();

  const handleSave = (project: Project) => {
    project.id = (customer?.project.length ?? 0) + 1
    customer?.project.push(project)
    nav(`/project/${c_id}/${project.id}`);
  };

  return (
    <Card >
      <h1>New Project</h1>
      <ProjectEditView onSave={handleSave} project={project} />
    </Card>
  );
};

export default ProjectPage;
