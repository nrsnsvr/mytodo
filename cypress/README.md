# Cypress E2E Testing

This directory contains end-to-end (E2E) tests for the MyToDo application using Cypress.

## 🚀 Quick Start

### Prerequisites
- Node.js (v18+)
- Backend services running (Docker Compose)
- Frontend development server running

### Running Tests

```bash
# Run all tests in headless mode
npm run test:e2e

# Run tests with browser UI
npm run cypress:open

# Run tests in headed mode (visible browser)
npm run test:e2e:headed

# Run specific test file
npx cypress run --spec "cypress/e2e/login.cy.js"
```

## 📁 Project Structure

```
cypress/
├── e2e/                    # Test specifications
│   ├── login.cy.js        # Authentication tests
│   └── todo.cy.js         # Todo CRUD operations
├── support/                # Support files
│   ├── e2e.js            # Global configuration
│   ├── commands.js       # Custom commands
│   └── selectors.js      # Element selectors (reference)
├── screenshots/           # Test screenshots
├── downloads/             # Downloaded files
└── README.md             # This file
```

## 🧪 Test Coverage

### Authentication Tests (`login.cy.js`)
- **Positive Tests:**
  - User registration with valid data
  - User login with valid credentials
  - Navigation between login and register pages

- **Negative Tests:**
  - Invalid email format validation
  - Wrong email/password combinations
  - Empty field validations
  - Non-existent user login attempts
  - Client-side validation (email format on blur, password length)

### Todo Operations Tests (`todo.cy.js`)
- **CRUD Operations:**
  - ✅ Create new todos with categories
  - ✅ Mark todos as completed
  - ✅ Edit todo text and categories
  - ✅ Delete todos
  - ✅ Validate empty input handling

## 📸 Screenshots

Cypress automatically captures screenshots during test execution:

- **On Failure**: Screenshots are saved when tests fail
- **Manual Screenshots**: Strategic screenshots at key points
- **Location**: `cypress/screenshots/`

### Screenshot Examples:
- `register-page.png` - Registration form
- `login-form-filled.png` - Login form with data
- `home-page-after-login.png` - Dashboard after login
- `todo-form-filled.png` - Todo creation form

## ⚙️ Configuration

### Key Settings (`cypress.config.js`)
```javascript
{
  baseUrl: 'http://localhost:5173',
  viewportWidth: 1280,
  viewportHeight: 720,
  defaultCommandTimeout: 15000,
  browser: 'chrome',
  screenshotOnRunFailure: true
}
```

### Custom Commands
- `cy.login()` - Attempts registration first, then login
- Element selectors centralized for maintainability

### Test Data
- **Dynamic Users**: Tests create users with timestamps
- **Isolated Tests**: Each test runs independently
- **Clean State**: No persistent data between tests

## 🐛 Troubleshooting

### Common Issues
1. **Backend Services**: Ensure Docker Compose is running
2. **Frontend Server**: Start with `npm run dev` in client/
3. **Port Conflicts**: Check if port 5173 is available
4. **Browser Issues**: Try running in headed mode for debugging

### Debug Commands
```bash
# Run with visible browser
npm run test:e2e:headed

# Open Cypress UI
npm run cypress:open

# Check backend services
docker-compose ps
```

## 📈 Test Reports

Detailed test reports are available in:
- `cypress/TEST_REPORT.md` - Comprehensive test documentation
- Console output with timing and screenshot information
- Screenshot collection for visual verification

## 📝 Adding New Tests

1. Create new `.cy.js` file in `cypress/e2e/`
2. Use existing selectors from `cypress/support/selectors.js`
3. Follow the established patterns for element waiting
4. Add strategic screenshots for visual verification
5. Update this README with new test information

---

**Cypress Version**: 14.5.3  
**Test Status**: ✅ All tests passing 