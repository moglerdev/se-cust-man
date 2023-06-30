import React from "react";
import Card from "../components/Card";

import { Task } from "../model";
import { Navigate, useNavigate, useParams } from "react-router-dom";
import { useCustomerStore } from "../useCustomerStore";
import {TaskEditView} from "../components/TaskEdit";

export const TaskPage: React.FC = () => {

  // load Project from useProject hook
  // get id from react router
  const {customer_id, project_id, task_id} = useParams<{customer_id: string, project_id: string, task_id: string}>();
  const c_id = Number.parseInt(customer_id!);
  const p_id = Number.parseInt(project_id!);
  const t_id = Number.parseInt(task_id!);
  
  const customer = useCustomerStore((state) => state.customers.find((c) => c.id === c_id));
  const project = customer?.project.find((p) => p.id === p_id);
  const task = project?.tasks.find((t) => t.id === t_id);

  const handleSave = (task: Task) => {

    let projects = customer?.project.map((p) => p.id === p_id ? {
      ...p,
      tasks: p.tasks.map((t) => t.id === t_id ? task : t)
    } : p)

    if (projects) {
      useCustomerStore.getState().setCustomer({...customer!, project: [...projects]});
    }
  };
  if (!project) {
    return <Navigate to={`/customer/${c_id}`} />;
  }

  if (!task) {
    return <Navigate to={`/project/${c_id}/${p_id}`} />;
  }

  return (
    <Card>
      <h1>Task {task.title}</h1>
      <TaskEditView onSave={handleSave} task={task} />
    </Card>
  );
};

export const TaskNewPage: React.FC = () => {
  // new Project
  const task: Task = {
    id: -1,
    title: "",
    description: "",
    priority: 0,
  };

  const {customer_id, project_id} = useParams<{customer_id: string, project_id: string}>();
  const c_id = Number.parseInt(customer_id!);
  const p_id = Number.parseInt(project_id!);
  const customer = useCustomerStore((state) => state.customers.find((c) => c.id === c_id));

  // react router hook
  const nav = useNavigate();

  const handleSave = (task: Task) => {
    let x = customer?.project.map((p) => p.id === p_id ? {
      ...p,
      tasks: [...p.tasks, task]
    } : p)
    useCustomerStore.getState().setCustomer({...customer!, project: [...x!]});
    nav(`/project/${c_id}/${p_id}/`);
  };

  return (
    <Card >
      <h1>New Task</h1>
      <TaskEditView onSave={handleSave} task={task} />
    </Card>
  );
};

export default TaskPage;
