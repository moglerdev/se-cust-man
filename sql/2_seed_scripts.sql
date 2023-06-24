-- Random data for the customer table
INSERT INTO customer (name, email, phone, address) VALUES
('John Doe', 'john.doe@example.com', '555-1234', '123 Main St'),
('Alice Smith', 'alice.smith@example.com', '555-5678', '456 Elm St'),
('Bob Johnson', 'bob.johnson@example.com', '555-9012', '789 Oak St');

-- Random data for the project table
INSERT INTO project (customer_id, title, description) VALUES
(1, 'Website Redesign', 'Redesigning the company website'),
(1, 'Mobile App Development', 'Developing a mobile app for iOS and Android'),
(2, 'Marketing Campaign', 'Planning and executing a marketing campaign'),
(3, 'Product Launch', 'Launching a new product line');

-- Random data for the task table
INSERT INTO task (project_id, title, description) VALUES
(1, 'Wireframe Design', 'Creating wireframes for the website redesign'),
(1, 'Frontend Development', 'Developing the frontend of the website'),
(2, 'UI/UX Design', 'Designing the user interface and experience for the mobile app'),
(2, 'Backend Development', 'Developing the backend functionality for the mobile app'),
(3, 'Market Research', 'Conducting market research for the marketing campaign'),
(3, 'Content Creation', 'Creating compelling content for the marketing materials'),
(4, 'Product Design', 'Designing the physical product'),
(4, 'Manufacturing', 'Overseeing the manufacturing process');

commit;