-- Seed Data for Placement Management System (Idempotent for H2 & MySQL)

-- 1. Skills
INSERT INTO skills (name, description) SELECT 'Java', 'Core Java, OOPs, Collections, Multi-threading' WHERE NOT EXISTS (SELECT 1 FROM skills WHERE name = 'Java');
INSERT INTO skills (name, description) SELECT 'Spring Boot', 'REST APIs, Spring Data JPA, Microservices' WHERE NOT EXISTS (SELECT 1 FROM skills WHERE name = 'Spring Boot');
INSERT INTO skills (name, description) SELECT 'MySQL', 'Relational Database Design, SQL, JPQL' WHERE NOT EXISTS (SELECT 1 FROM skills WHERE name = 'MySQL');
INSERT INTO skills (name, description) SELECT 'React.js', 'Frontend Framework, State Management, Hooks' WHERE NOT EXISTS (SELECT 1 FROM skills WHERE name = 'React.js');
INSERT INTO skills (name, description) SELECT 'Python', 'Data Structures, Machine Learning, Scripting' WHERE NOT EXISTS (SELECT 1 FROM skills WHERE name = 'Python');
INSERT INTO skills (name, description) SELECT 'AWS', 'Cloud Computing, EC2, S3, Deployment' WHERE NOT EXISTS (SELECT 1 FROM skills WHERE name = 'AWS');

-- 2. Companies
INSERT INTO companies (name, industry, email, phone, website, address, status) SELECT 'Google India', 'Information Technology', 'recruitment@google.com', '9876543210', 'https://careers.google.com', 'Bangalore, Karnataka', 'ACTIVE' WHERE NOT EXISTS (SELECT 1 FROM companies WHERE email = 'recruitment@google.com');
INSERT INTO companies (name, industry, email, phone, website, address, status) SELECT 'TCS', 'IT Services & Consulting', 'careers@tcs.com', '9876543211', 'https://tcs.com', 'Mumbai, Maharashtra', 'ACTIVE' WHERE NOT EXISTS (SELECT 1 FROM companies WHERE email = 'careers@tcs.com');
INSERT INTO companies (name, industry, email, phone, website, address, status) SELECT 'Infosys', 'IT Services', 'talent@infosys.com', '9876543212', 'https://infosys.com', 'Pune, Maharashtra', 'ACTIVE' WHERE NOT EXISTS (SELECT 1 FROM companies WHERE email = 'talent@infosys.com');
INSERT INTO companies (name, industry, email, phone, website, address, status) SELECT 'Microsoft India', 'Software Product', 'jobs@microsoft.com', '9876543213', 'https://careers.microsoft.com', 'Hyderabad, Telangana', 'ACTIVE' WHERE NOT EXISTS (SELECT 1 FROM companies WHERE email = 'jobs@microsoft.com');

-- 3. Students
INSERT INTO students (name, email, phone, department, cgpa, passing_year, active, resume_url) SELECT 'Rahul Sharma', 'rahul.sharma@example.com', '9123456780', 'Computer Science', 8.75, 2024, true, 'https://drive.google.com/resume/rahul' WHERE NOT EXISTS (SELECT 1 FROM students WHERE email = 'rahul.sharma@example.com');
INSERT INTO students (name, email, phone, department, cgpa, passing_year, active, resume_url) SELECT 'Priya Patel', 'priya.patel@example.com', '9123456781', 'Information Technology', 9.10, 2024, true, 'https://drive.google.com/resume/priya' WHERE NOT EXISTS (SELECT 1 FROM students WHERE email = 'priya.patel@example.com');
INSERT INTO students (name, email, phone, department, cgpa, passing_year, active, resume_url) SELECT 'Aman Gupta', 'aman.gupta@example.com', '9123456782', 'Computer Science', 7.20, 2024, true, 'https://drive.google.com/resume/aman' WHERE NOT EXISTS (SELECT 1 FROM students WHERE email = 'aman.gupta@example.com');
INSERT INTO students (name, email, phone, department, cgpa, passing_year, active, resume_url) SELECT 'Neha Singh', 'neha.singh@example.com', '9123456783', 'Electronics & Telecom', 8.40, 2024, true, 'https://drive.google.com/resume/neha' WHERE NOT EXISTS (SELECT 1 FROM students WHERE email = 'neha.singh@example.com');
INSERT INTO students (name, email, phone, department, cgpa, passing_year, active, resume_url) SELECT 'Vikram Verma', 'vikram.verma@example.com', '9123456784', 'Mechanical Engineering', 6.50, 2024, true, 'https://drive.google.com/resume/vikram' WHERE NOT EXISTS (SELECT 1 FROM students WHERE email = 'vikram.verma@example.com');

-- 4. Student Skills Mapping
INSERT INTO student_skills (student_id, skill_id) SELECT 1, 1 WHERE NOT EXISTS (SELECT 1 FROM student_skills WHERE student_id = 1 AND skill_id = 1);
INSERT INTO student_skills (student_id, skill_id) SELECT 1, 2 WHERE NOT EXISTS (SELECT 1 FROM student_skills WHERE student_id = 1 AND skill_id = 2);
INSERT INTO student_skills (student_id, skill_id) SELECT 1, 3 WHERE NOT EXISTS (SELECT 1 FROM student_skills WHERE student_id = 1 AND skill_id = 3);
INSERT INTO student_skills (student_id, skill_id) SELECT 2, 1 WHERE NOT EXISTS (SELECT 1 FROM student_skills WHERE student_id = 2 AND skill_id = 1);
INSERT INTO student_skills (student_id, skill_id) SELECT 2, 4 WHERE NOT EXISTS (SELECT 1 FROM student_skills WHERE student_id = 2 AND skill_id = 4);
INSERT INTO student_skills (student_id, skill_id) SELECT 2, 5 WHERE NOT EXISTS (SELECT 1 FROM student_skills WHERE student_id = 2 AND skill_id = 5);
INSERT INTO student_skills (student_id, skill_id) SELECT 3, 1 WHERE NOT EXISTS (SELECT 1 FROM student_skills WHERE student_id = 3 AND skill_id = 1);
INSERT INTO student_skills (student_id, skill_id) SELECT 3, 3 WHERE NOT EXISTS (SELECT 1 FROM student_skills WHERE student_id = 3 AND skill_id = 3);
INSERT INTO student_skills (student_id, skill_id) SELECT 4, 5 WHERE NOT EXISTS (SELECT 1 FROM student_skills WHERE student_id = 4 AND skill_id = 5);
INSERT INTO student_skills (student_id, skill_id) SELECT 4, 6 WHERE NOT EXISTS (SELECT 1 FROM student_skills WHERE student_id = 4 AND skill_id = 6);

-- 5. Jobs
INSERT INTO jobs (title, description, company_id, min_cgpa, package_amount, location, deadline, active) SELECT 'Software Engineer Trainee', 'Full stack Java & React development for cloud platforms', 1, 8.00, 18.50, 'Bangalore', '2026-12-31', true WHERE NOT EXISTS (SELECT 1 FROM jobs WHERE title = 'Software Engineer Trainee' AND company_id = 1);
INSERT INTO jobs (title, description, company_id, min_cgpa, package_amount, location, deadline, active) SELECT 'System Engineer', 'Enterprise Java backend developer role', 2, 7.00, 7.00, 'Pune', '2026-11-30', true WHERE NOT EXISTS (SELECT 1 FROM jobs WHERE title = 'System Engineer' AND company_id = 2);
INSERT INTO jobs (title, description, company_id, min_cgpa, package_amount, location, deadline, active) SELECT 'Associate Software Developer', 'Backend Java microservices engineering', 3, 7.50, 8.50, 'Hyderabad', '2026-10-15', true WHERE NOT EXISTS (SELECT 1 FROM jobs WHERE title = 'Associate Software Developer' AND company_id = 3);
INSERT INTO jobs (title, description, company_id, min_cgpa, package_amount, location, deadline, active) SELECT 'Cloud Support Engineer', 'AWS Cloud Infrastructure management & Python automation', 4, 8.20, 16.00, 'Hyderabad', '2026-12-15', true WHERE NOT EXISTS (SELECT 1 FROM jobs WHERE title = 'Cloud Support Engineer' AND company_id = 4);

-- 6. Job Required Skills Mapping
INSERT INTO job_required_skills (job_id, skill_id) SELECT 1, 1 WHERE NOT EXISTS (SELECT 1 FROM job_required_skills WHERE job_id = 1 AND skill_id = 1);
INSERT INTO job_required_skills (job_id, skill_id) SELECT 1, 2 WHERE NOT EXISTS (SELECT 1 FROM job_required_skills WHERE job_id = 1 AND skill_id = 2);
INSERT INTO job_required_skills (job_id, skill_id) SELECT 1, 4 WHERE NOT EXISTS (SELECT 1 FROM job_required_skills WHERE job_id = 1 AND skill_id = 4);
INSERT INTO job_required_skills (job_id, skill_id) SELECT 2, 1 WHERE NOT EXISTS (SELECT 1 FROM job_required_skills WHERE job_id = 2 AND skill_id = 1);
INSERT INTO job_required_skills (job_id, skill_id) SELECT 2, 3 WHERE NOT EXISTS (SELECT 1 FROM job_required_skills WHERE job_id = 2 AND skill_id = 3);
INSERT INTO job_required_skills (job_id, skill_id) SELECT 3, 1 WHERE NOT EXISTS (SELECT 1 FROM job_required_skills WHERE job_id = 3 AND skill_id = 1);
INSERT INTO job_required_skills (job_id, skill_id) SELECT 3, 2 WHERE NOT EXISTS (SELECT 1 FROM job_required_skills WHERE job_id = 3 AND skill_id = 2);
INSERT INTO job_required_skills (job_id, skill_id) SELECT 4, 5 WHERE NOT EXISTS (SELECT 1 FROM job_required_skills WHERE job_id = 4 AND skill_id = 5);
INSERT INTO job_required_skills (job_id, skill_id) SELECT 4, 6 WHERE NOT EXISTS (SELECT 1 FROM job_required_skills WHERE job_id = 4 AND skill_id = 6);

-- 7. Applications
INSERT INTO applications (student_id, job_id, apply_date, status) SELECT 1, 1, '2026-08-01', 'SHORTLISTED' WHERE NOT EXISTS (SELECT 1 FROM applications WHERE student_id = 1 AND job_id = 1);
INSERT INTO applications (student_id, job_id, apply_date, status) SELECT 2, 1, '2026-08-02', 'SELECTED' WHERE NOT EXISTS (SELECT 1 FROM applications WHERE student_id = 2 AND job_id = 1);
INSERT INTO applications (student_id, job_id, apply_date, status) SELECT 3, 2, '2026-08-03', 'APPLIED' WHERE NOT EXISTS (SELECT 1 FROM applications WHERE student_id = 3 AND job_id = 2);
INSERT INTO applications (student_id, job_id, apply_date, status) SELECT 4, 4, '2026-08-04', 'INTERVIEW_SCHEDULED' WHERE NOT EXISTS (SELECT 1 FROM applications WHERE student_id = 4 AND job_id = 4);

-- 8. Interviews
INSERT INTO interviews (application_id, round_name, interview_date, location_link, result, feedback) SELECT 1, 'Technical Round 1', '2026-08-10 10:00:00', 'https://meet.google.com/abc-defg-hij', 'PASSED', 'Excellent command over Data Structures & Java Streams.' WHERE NOT EXISTS (SELECT 1 FROM interviews WHERE application_id = 1 AND round_name = 'Technical Round 1');
INSERT INTO interviews (application_id, round_name, interview_date, location_link, result, feedback) SELECT 2, 'HR & Management Round', '2026-08-12 14:30:00', 'https://meet.google.com/xyz-pqrs-tuv', 'PASSED', 'Selected for final offer roll-out.' WHERE NOT EXISTS (SELECT 1 FROM interviews WHERE application_id = 2 AND round_name = 'HR & Management Round');
INSERT INTO interviews (application_id, round_name, interview_date, location_link, result, feedback) SELECT 4, 'Technical Round 1 (Python/AWS)', '2026-08-15 11:00:00', 'https://teams.microsoft.com/l/meetup-join/123', 'PENDING', 'Interview scheduled.' WHERE NOT EXISTS (SELECT 1 FROM interviews WHERE application_id = 4 AND round_name = 'Technical Round 1 (Python/AWS)');

-- 9. Placements
INSERT INTO placements (student_id, company_id, job_id, package_amount, joining_date, placement_status) SELECT 2, 1, 1, 18.50, '2026-09-01', 'CONFIRMED' WHERE NOT EXISTS (SELECT 1 FROM placements WHERE student_id = 2);
