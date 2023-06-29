import React from "react";
import CustomerList from "./CustomerList";
import TaskList from "./TaskList";
import ProjectList from "./ProjectList";
import Card from "./Card";

const projects = [
  { id: 1, name: "Project A", description: "Lorem ipsum dolor sit amet." },
  { id: 2, name: "Project B", description: "Consectetur adipiscing elit." },
  {
    id: 3,
    name: "Project C",
    description:
      "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
  },
];

const Dashboard: React.FC = () => {
  return (
    <div className="w-full grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 p-4">
      <Card title="Customer">
        <CustomerList />
      </Card>
      <Card title="Project">
        <ProjectList projects={projects} />
      </Card>
      <Card title="Task">
        <TaskList />
      </Card>
    </div>
  );
};

export default Dashboard;
