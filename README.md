# Attendance-management-system-api

This is attendance management system RestFul API and implement JWT Role-based authentication mechanism that build with Spring boot 3, Spring Security and MongoDB.

## API Reference

### Authentication
- **Register** "/api/v1/auth/register"
- **Login** "/api/v1/auth/login"

### Roles
- **GET** "/api/v1/roles"
- **GET** "/api/v1/roles/{id}"
- **POST** "/api/v1/roles"
- **PUT** "/api/v1/roles/{id}"
- **DELETE** "/api/v1/roles/{id}"

### Departments
- **GET** "/api/v1/departments"
- **GET** "/api/v1/departments/{id}"
- **POST** "/api/v1/departments"
- **PUT** "/api/v1/departments/{id}"
- **DELETE** "/api/v1/departments/{id}"

### Attendance
- **POST** "/api/v1/attendance/check-in"
- **POST** "/api/v1/attendance/check-out"
- **GET** "/api/v1/attendance"
- **GET** "/api/v1/attendance/{userId}"
- **DELETE** "/api/v1/attendance/{id}"

### Users
- **GET** "/api/v1/users"
- **GET** "/api/v1/users/me"
- **GET** "/api/v1/users/{id}"
- **PUT** "/api/v1/users/{id}"
- **DELETE** "/api/v1/users/{id}"
