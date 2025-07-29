// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************

// Custom command for login
Cypress.Commands.add('login', (email = 'test@test.com', password = '123456') => {
  cy.request({
    method: 'POST',
    url: 'http://localhost:9000/auth/register',
    body: {
      username: 'testuser',
      email: email,
      password: password,
      phone: '+94716573585'
    },
    failOnStatusCode: false
  }).then((response) => {
    if (response.body.success) {
      const token = response.body.data.object.accessToken;
      cy.window().then((win) => {
        win.localStorage.setItem('token', token);
        win.localStorage.setItem('user', JSON.stringify(response.body.data.object));
      });
    } else {
      // Try login if register fails
      cy.request({
        method: 'POST',
        url: 'http://localhost:9000/auth/login',
        body: {
          email: email,
          password: password
        },
        failOnStatusCode: false
      }).then((loginResponse) => {
        if (loginResponse.body.success) {
          const token = loginResponse.body.data.object.accessToken;
          cy.window().then((win) => {
            win.localStorage.setItem('token', token);
            win.localStorage.setItem('user', JSON.stringify(loginResponse.body.data.object));
          });
        }
      });
    }
  });
});

// Custom command for logout
Cypress.Commands.add('logout', () => {
  cy.window().then((win) => {
    win.localStorage.removeItem('token');
    win.localStorage.removeItem('user');
  });
});

// Custom command to add a todo
Cypress.Commands.add('addTodo', (task, category = 'home', datetime = null) => {
  const todoData = {
    task: task,
    category: category
  };
  
  if (datetime) {
    todoData.datetime = datetime;
  } else {
    // Default to today
    const today = new Date();
    today.setHours(12, 0, 0, 0);
    todoData.datetime = today.toISOString();
  }

  cy.request({
    method: 'POST',
    url: 'http://localhost:9000/todo/new',
    body: todoData,
    headers: {
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    },
    failOnStatusCode: false
  });
});

// Custom command to clear todos
Cypress.Commands.add('clearTodos', () => {
  cy.request({
    method: 'GET',
    url: 'http://localhost:9000/todo/summary?type=today',
    headers: {
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    },
    failOnStatusCode: false
  }).then((response) => {
    if (response.body.tasks && response.body.tasks.length > 0) {
      response.body.tasks.forEach((todo) => {
        cy.request({
          method: 'DELETE',
          url: `http://localhost:9000/todo/${todo._id}`,
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          },
          failOnStatusCode: false
        });
      });
    }
  });
}); 