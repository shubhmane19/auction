function togglePassword(id) {
  const input = document.getElementById(id);
  const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
  input.setAttribute('type', type);
}

// Register form handler
$(document).ready(function () {
  const registerForm = $('#register-form');
  if (registerForm.length) {
    registerForm.submit(function (event) {
      event.preventDefault();

      const username = $('#username').val();
      const password = $('#password').val();
      const confirmPassword = $('#confirm-password').val();

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
          alert('Registration successful! Please login.');
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

      const username = $('#username').val();
      const password = $('#password').val();

      $.ajax({
        url: '/api/users/login',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ username, password }),
        success: function () {
          alert('Login successful!');
          // Redirect to dashboard or homepage
          window.location.href = 'index.html';
        },
        error: function (xhr) {
          alert(xhr.responseJSON?.message || 'Login failed');
        }
      });
    });
  }
});
