import React from "react";

const TaskList: React.FC = () => {
  // Example task data
  const tasks = [
    { id: 1, title: "Complete sales report", dueDate: "2023-07-10" },
    { id: 2, title: "Follow up with leads", dueDate: "2023-06-30" },
    { id: 3, title: "Schedule client meeting", dueDate: "2023-07-05" },
    // Add more task objects as needed
  ];

  return (
    <div className="task-list">
      <h3>Task List</h3>
      <ul>
        {tasks.map((task) => (
          <li key={task.id}>
            <div>
              <strong>{task.title}</strong>
            </div>
            <div>Due Date: {task.dueDate}</div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TaskList;
