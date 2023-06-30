export interface Task {
  id: number,
  title: string,
  description: string,
  priority: number
}

export interface Project {
  id: number,
  title: string,
  description: string,
  deadline: string
  tasks: Task[]
}

export interface Customer {
  id: number;
  name: string;
  email: string;
  phone: string;
  address: string;
  project: Project[]
}

