
import React from 'react';
import { Task } from '../model';
import Input from './Input';
import Button from './Button';


interface TaskEditViewProps {
    task: Task;
    onSave: (Task: Task) => void;
  }
  
export const TaskEditView: React.FC<TaskEditViewProps> = ({ task, onSave}) => {
    const [title, setTitle] = React.useState(task.title);
    const [description, setDescription] = React.useState(task.description);
    const [priority, setPriority] = React.useState(task.priority);
  
    React.useEffect(() => {
      setTitle(task.title);
      setDescription(task.description);
      setPriority(task.priority);
    }
    , [task]);
  
    const handletitleChange = (value: string) => {
      setTitle(value);
    };
  
    const handleDescriptionChange = (value: string) => {
      setDescription(value);
    };
  
    const handlepriorityChange = (value: string) => {
      setPriority(Number.parseInt(value));
    };
  
    const handleSave = () => {
      onSave({
        ...task,
        title,
        description,
        priority,
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
          <Input value={priority.toString()} onChange={handlepriorityChange} />
        </div>
        <div>
          <Button onClick={handleSave} className="bg-blue-500 hover:bg-blue-600">Save</Button>
        </div>
      </div>
    );
  };

