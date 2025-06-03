function togglePassword(id) {
  const input = document.getElementById(id);
  const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
  input.setAttribute('type', type);
}

$(document).ready(function () {
  // Register form handler
  const registerForm = $('#register-form');
  if (registerForm.length) {
    registerForm.submit(function (event) {
      event.preventDefault();

      const username = $('#username').val().trim();
      const password = $('#password').val().trim();
      const confirmPassword = $('#confirm-password').val().trim();

      if (!username || !password || !confirmPassword) {
        alert('All fields are required');
        return;
      }

      if (password.length < 6) {
        alert('Password must be at least 6 characters long');
        return;
      }

      if (password !== confirmPassword) {
        $('#confirm-password').addClass('is-invalid');
        $('#confirm-feedback').show();
        return;
      } else {
        $('#confirm-password').removeClass('is-invalid');
        $('#confirm-feedback').hide();
      }

      $.ajax({
        url: '/api/users/register',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ username, password }),
        success: function () {
          alert('Registration successful! Redirecting to login.');
          window.location.href = 'login.html';
        },
        error: function (xhr) {
          alert(xhr.responseJSON?.message || 'Registration failed');
        }
      });
    });
  }

  // Login form handler
  const loginForm = $('#login-form');
  if (loginForm.length) {
    loginForm.submit(function (event) {
      event.preventDefault();

      const username = $('#username').val().trim();
      const password = $('#password').val().trim();

      if (!username || !password) {
        alert('Please enter both username and password.');
        return;
      }

      $.ajax({
        url: '/api/users/login',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ username, password }),
        success: function (data) {
          if (data.valid) {
            alert('Login successful! Redirecting...');
            window.location.href = 'index.html';
          } else {
            alert('Invalid credentials. Please try again.');
          }
        },
        error: function (xhr) {
          alert(xhr.responseJSON?.message || 'Login failed');
        }
      });
    });
  }
});
