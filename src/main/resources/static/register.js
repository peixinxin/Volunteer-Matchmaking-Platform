// Register form submit
$('#registerForm').on('submit', function (e) {
    // Prevent default form submission
    e.preventDefault();

    // Get selected user type
    var userType = $('input[name="userType"]:checked').val();

    // Create user object
    var user = {
        email: $('#email').val(),
        password: $('#password').val(),
        name: $('#name').val(),
        userType: userType // Add user type to user object
    };

    // Send POST request to /api/user
    $.ajax({
        type: 'POST',
        url: '/api/register',
        data: JSON.stringify(user),
        contentType: 'application/json',
        // If success, alert user and redirect to login page
        success: function (response) {
            alert(response.name + ' registered successfully');
            window.location.href = '/login';
        },
        // If error, alert user
        error: function (error) {
            alert(error.responseText);
        }
    });
});
