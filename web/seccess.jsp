<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Booking Confirmed</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(135deg, #e0ffe0, #f0fff0);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .confirmation-box {
            background: white;
            padding: 40px;
            border-radius: 15px;
            text-align: center;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
            animation: fadeInUp 1s ease-out;
            max-width: 600px;
            width: 90%;
        }

        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(40px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .checkmark {
            font-size: 60px;
            color: #4BB543;
            animation: pop 0.5s ease-out;
        }

        @keyframes pop {
            0% {
                transform: scale(0);
                opacity: 0;
            }
            100% {
                transform: scale(1);
                opacity: 1;
            }
        }

        h2 {
            color: #333;
            margin-top: 20px;
        }

        p {
            font-size: 18px;
            color: #555;
            text-align: left;
            margin: 10px 0;
        }

        .details {
            margin-top: 20px;
            text-align: left;
        }

        .back-btn {
            margin-top: 30px;
            display: inline-block;
            padding: 10px 20px;
            background: #4BB543;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background 0.3s;
        }

        .back-btn:hover {
            background: #3ca235;
        }
    </style>
</head>
<body>

<div class="confirmation-box">
    <div class="checkmark">✔</div>
    <h2>Booking Confirmed!</h2>
    <p>Thank you for booking. We will contact you shortly.</p>

    <div class="details">
        <p><strong>Name:</strong> ${name}</p>
        <p><strong>Email:</strong> ${email}</p>
        <p><strong>Phone:</strong> ${phone}</p>
        <p><strong>Travel Date:</strong> ${travel_date}</p>
        <p><strong>No. of Travelers:</strong> ${travellersno}</p>
        <p><strong>Special Request:</strong> ${requestText}</p>
    </div>

    <a href="holiday.html" class="back-btn">Book Another Trip</a>
</div>

</body>
</html>
