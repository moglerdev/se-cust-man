import React from "react";
import { Project } from "../model";
import Input from "./Input";
import Button from "./Button";

interface ProjectEditViewProps {
  project: Project;
  onSave: (project: Project) => void;
}

export const ProjectEditView: React.FC<ProjectEditViewProps> = ({ project, onSave}) => {
  const [title, setTitle] = React.useState(project.title);
  const [description, setDescription] = React.useState(project.description);
  const [deadline, setDeadline] = React.useState(project.deadline);

  React.useEffect(() => {
    setTitle(project.title);
    setDescription(project.description);
    setDeadline(project.deadline);
  }
  , [project]);

  const handletitleChange = (value: string) => {
    setTitle(value);
  };

  const handleDescriptionChange = (value: string) => {
    setDescription(value);
  };

  const handledeadlineChange = (value: string) => {
    setDeadline(value);
  };

  const handleSave = () => {
    onSave({
      ...project,
      title,
      description,
      deadline,
    });
  };

  return (
    <div className="grid grid-cols-2 gap-4">
      <div>
        <p className="font-bold text-xl">title:</p>
        <Input value={title} onChange={handletitleChange} />
      </div>
      <div>
        <p className="font-bold text-xl">Description:</p>
        <Input value={description} onChange={handleDescriptionChange} />
      </div>
      <div>
        <p className="font-bold text-xl">Start Date:</p>
        <Input value={deadline} onChange={handledeadlineChange} />
      </div>
      <div>
        <Button onClick={handleSave} className="bg-blue-500 hover:bg-blue-600">Save</Button>
      </div>
    </div>
  );
};

const ProjectView: React.FC<{ project: Project }> = ({ project}) => {
  return (
    <div className="grid grid-cols-2 gap-4">
      <div>
        <p className="font-bold text-xl">title:</p>
        <p>{project.title}</p>
      </div>
      <div>
        <p className="font-bold text-xl">Description:</p>
        <p>{project.description}</p>
      </div>
      <div>
        <p className="font-bold text-xl">Start Date:</p>
        <p>{project.deadline}</p>
      </div>
    </div>
  );
};

export default ProjectView;
